package it.polimi.ingsw.model.match.markettray;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.litemodel.litemarkettray.LiteMarble;
import it.polimi.ingsw.litemodel.litemarkettray.LiteMarketTray;
import it.polimi.ingsw.model.MappableToLiteVersion;
import it.polimi.ingsw.model.exceptions.faithtrack.EndGameException;
import it.polimi.ingsw.model.exceptions.tray.OutOfBoundMarketTrayException;
import it.polimi.ingsw.model.exceptions.tray.UnpaintableMarbleException;
import it.polimi.ingsw.model.exceptions.warehouse.UnobtainableResourceException;
import it.polimi.ingsw.model.exceptions.warehouse.WrongDepotException;
import it.polimi.ingsw.model.match.markettray.MarkerMarble.*;
import it.polimi.ingsw.model.player.PlayableCardReaction;
import it.polimi.ingsw.model.player.personalBoard.warehouse.depot.DepotSlot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;

/**
 * the market tray class represent the market of resources in the game.
 * it allows the player to obtain resources pushing the extra marble in a column or row
 */
public class MarketTray implements MappableToLiteVersion {
    /**
     * this matrix represent the marbles in the tray
     */
    private final Marble[][] marbles;

    /**
     * used to save the extra marble that stay in the slide
     */
    private Marble slideMarble;

    /**
     * row of the tray
     */
    private final int row;

    /**
     * column of the tray
     */
    private final int col;

    /**
     * exception to check if the passed value is valid to be used in the matrix
     * first parameter is the bound
     * second parameter is the value to check
     */
    BiPredicate<Integer, Integer> boundCheck = (Integer bound, Integer passedBound) -> (bound < passedBound || passedBound < 0);

    /**
     * the constructor read from a json file the size of the tray and instantiate the right amount and type of marble, saved in the json.
     * then it shuffle the order of the marbles and insert them all in the tray
     */
    @JsonCreator
    public MarketTray(@JsonProperty("row") int row, @JsonProperty("col") int col, @JsonProperty("marbles") List<Marble> marbleBuilder) throws IOException {
        Random rand = new Random();

        marbles = new Marble[row][col];
        this.col = col;
        this.row = row;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                marbles[i][j] = marbleBuilder.remove(rand.nextInt(marbleBuilder.size()));
            }
        }

        slideMarble = marbleBuilder.get(0);
    }

    /**
     * for all the marble in the column it is given the right resource to the player
     * get an index of a column and shift the marbles in it until the last one falls into the slide
     * @param shiftCol index of the col
     * @param player player that uses the tray
     * @throws OutOfBoundMarketTrayException launched when shiftCol is out of bound
     */
    public void pushCol(int shiftCol, PlayableCardReaction player) throws OutOfBoundMarketTrayException, UnobtainableResourceException, EndGameException, WrongDepotException {
        if (boundCheck.test(this.col, shiftCol)) throw new OutOfBoundMarketTrayException();

        for (int i = 0; i < row; i++) player.obtainResource(DepotSlot.BUFFER, marbles[i][shiftCol].toResource());

        Marble temp = marbles[0][shiftCol];
        for (int i = 0; i < (row - 1); i++) {
            marbles[i][shiftCol] = marbles[i+1][shiftCol];
        }
        marbles[row-1][shiftCol] = slideMarble;

        slideMarble = temp;
    }

    /**
     * for all the marble in the row it is given the right resource to the player
     * get an index of a row and shift the marbles in it until the last one falls into the slide
     * @param shiftRow index of the row
     * @param player player that uses the tray
     * @throws OutOfBoundMarketTrayException launched when shiftRow is out of bound
     */
    public void pushRow(int shiftRow, PlayableCardReaction player) throws OutOfBoundMarketTrayException, UnobtainableResourceException, EndGameException, WrongDepotException {
        if (boundCheck.test(this.row, shiftRow)) throw new OutOfBoundMarketTrayException();

        for (int i = 0; i < col; i++) player.obtainResource(DepotSlot.BUFFER, marbles[shiftRow][i].toResource());

        Marble temp = marbles[shiftRow][0];
        if (col - 1 >= 0) System.arraycopy(marbles[shiftRow], 1, marbles[shiftRow], 0, col - 1);
        marbles[shiftRow][col-1] = slideMarble;

        slideMarble = temp;
    }

    /**
     * return a List of the marbles in the tray
     * @return a list of marbles
     */
    public List<Marble> showMarketTray() {
        List<Marble> toReturn = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                toReturn.add(marbles[i][j].copy());
            }
        }
        return toReturn;
    }

    /**
     * return a copy of the marble in the slide
     * @return Marble
     */
    public Marble showSlideMarble() {
        return slideMarble.copy();
    }

    /**
     * set a new marble color in a paintable
     * @param newColor the new marble color
     * @param marbleIndex the number in the list of marble
     * @throws UnpaintableMarbleException throw if
     */
    public void paintMarble(Marble newColor, int marbleIndex) throws UnpaintableMarbleException {
        int x = marbleIndex / col;
        int y = marbleIndex % col;
        if(this.marbles[x][y].isPaintable()) {
            this.marbles[x][y].paint(newColor);
        }
        else throw new UnpaintableMarbleException();
    }

    /**
     * restore all marble in the tray
     */
    public void unPaint(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                marbles[i][j].unPaint();
            }
        }
    }

    /**
     * Create a lite version of the class and serialize it in json
     *
     * @return the json representation of the lite version of the class
     */
    @Override
    public LiteMarketTray liteVersion() {
        LiteMarble[][] config = new LiteMarble[this.row][this.col];

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(Arrays.stream(this.marbles[i]).map(Marble::liteVersion).toArray(), 0,
                    config[i], 0, this.col);
        }

        return new LiteMarketTray(config, this.slideMarble.liteVersion(), this.row, this.col);
    }
}
