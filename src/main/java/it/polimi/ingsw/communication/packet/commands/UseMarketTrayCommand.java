package it.polimi.ingsw.communication.packet.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.match.markettray.RowCol;
import it.polimi.ingsw.model.player.PlayerAction;

/**
 * This class represent a command instance for the player that use the market tray
 */
public class UseMarketTrayCommand extends Command{

    /**
     * the row or col
     */
    private final RowCol rc;

    /**
     * the index of the row/col
     */
    private final int index;

    /**
     * build a command that allow the player to use the market player
     * @param rc enum indicating if the player want to push a row or column
     * @param index the index of the row/col to push
     */
    @JsonCreator
    public UseMarketTrayCommand(@JsonProperty("rc") RowCol rc, @JsonProperty("index") int index) {
        this.rc = rc;
        this.index = index;
    }

    /**
     * The command to execute on a player action interface
     *
     * @param player the player on which execute the command
     */
    @Override
    public void execute(PlayerAction player) {
        player.useMarketTray(this.rc, this.index);
    }

    /**
     * @return the row or the column selected
     */
    @JsonGetter("rc")
    public RowCol getRc() {
        return rc;
    }

    /**
     * @return the index of the tray
     */
    @JsonGetter("index")
    public int getIndex() {
        return index;
    }
}
