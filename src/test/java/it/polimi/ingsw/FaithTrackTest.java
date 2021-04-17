package it.polimi.ingsw;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.exceptions.card.EmptyDeckException;
import it.polimi.ingsw.model.exceptions.game.LorenzoMovesException;
import it.polimi.ingsw.model.exceptions.game.movesexception.NotHisTurnException;
import it.polimi.ingsw.model.exceptions.game.movesexception.TurnStartedException;
import it.polimi.ingsw.model.exceptions.warehouse.WrongPointsException;
import it.polimi.ingsw.model.exceptions.faithtrack.IllegalMovesException;
import it.polimi.ingsw.model.exceptions.warehouse.production.IllegalTypeInProduction;
import it.polimi.ingsw.model.match.PlayerToMatch;
import it.polimi.ingsw.model.match.match.Match;
import it.polimi.ingsw.model.match.match.MultiplayerMatch;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.personalBoard.faithTrack.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceBuilder;
import org.junit.jupiter.api.Test;

public class FaithTrackTest {

    Match pm = new MultiplayerMatch();

    /**
     * Testing if the FaithTrack is correctly created using the player position to take cells info
     * @throws IllegalMovesException if the Player or Lorenzo moves when they are in the last cell
     * @throws WrongPointsException if the Player or Lorenzo receives negative points
     */
    @Test
    public void infoFaithTrack() throws IllegalMovesException, WrongPointsException {

        try {
            pm.playerJoin(new Player("uno", pm));
            pm.playerJoin(new Player("due", pm));
        } catch (IllegalTypeInProduction e) {
            e.printStackTrace();
        }

        FaithTrack track = new FaithTrack();
        Resource point = ResourceBuilder.buildFaithPoint();

        assertEquals(0, track.victoryPointCell());
        assertEquals(VaticanSpace.NONE, track.vaticanSpaceCell());
        track.movePlayer(point.amount(), pm);
        track.movePlayer(point.amount(), pm);
        track.movePlayer(point.amount(), pm);
        assertEquals(VaticanSpace.NONE, track.vaticanSpaceCell());
        assertEquals(1, track.victoryPointCell());
        track.movePlayer(point.amount(), pm);
        track.movePlayer(point.amount(), pm);
        track.movePlayer(point.amount(), pm);
        assertEquals(VaticanSpace.FIRST, track.vaticanSpaceCell());
        assertEquals(2, track.victoryPointCell());
        track.movePlayer(point.amount(), pm);
        track.movePlayer(point.amount(), pm);
        assertEquals(VaticanSpace.FIRST, track.vaticanSpaceCell());
        assertEquals(0, track.victoryPointCell());

        Resource mid = ResourceBuilder.buildFaithPoint(7);
        track.movePlayer(mid.amount(), pm);
        assertEquals(VaticanSpace.SECOND, track.vaticanSpaceCell());
        assertEquals(9, track.victoryPointCell());

        Resource last = ResourceBuilder.buildFaithPoint(9);
        track.movePlayer(last.amount(), pm);
        assertEquals(VaticanSpace.THIRD, track.vaticanSpaceCell());
        assertEquals(20, track.victoryPointCell());

    }

    /**
     * Testing if the PlayerPosition is correctly updated
     * @throws IllegalMovesException if the Player or Lorenzo moves when they are in the last cell
     * @throws WrongPointsException if the Player or Lorenzo receives negative points
     */
    @Test
    public void checkPlayerPosition() throws IllegalMovesException, WrongPointsException {
        try {
            pm.playerJoin(new Player("uno", pm));
            pm.playerJoin(new Player("due", pm));
        } catch (IllegalTypeInProduction e) {
            e.printStackTrace();
        }

        Resource first = ResourceBuilder.buildFaithPoint(1);
        Resource second = ResourceBuilder.buildFaithPoint(2);
        Resource third = ResourceBuilder.buildFaithPoint(3);
        Resource last = ResourceBuilder.buildFaithPoint(20);

        FaithTrack faithTrack = new FaithTrack();

        assertEquals(0,faithTrack.getPlayerPosition());
        faithTrack.movePlayer(first.amount(), pm);
        assertEquals(1,faithTrack.getPlayerPosition());
        faithTrack.movePlayer(first.amount(), pm);
        assertEquals(2,faithTrack.getPlayerPosition());
        faithTrack.movePlayer(second.amount(), pm);
        assertEquals(4,faithTrack.getPlayerPosition());
        faithTrack.movePlayer(first.amount(), pm);
        assertEquals(5,faithTrack.getPlayerPosition());
        faithTrack.movePlayer(third.amount(), pm);
        assertEquals(8,faithTrack.getPlayerPosition());
        faithTrack.movePlayer(last.amount(), pm); //Player should be in the cell 28 but the Track is composed with 24 positions
        assertEquals(24,faithTrack.getPlayerPosition());
    }

    /**
     * Testing if the Tiles of the VaticanSpace are correctly flipped when the player moves
     */
     @Test
     public void flipPopeTiles() {
     }

    /**
     * Testing if the model calls an exception when the player try to pass the last cell of the FaithTrack
     */
    @Test
    public void callExceptionsPlayer() throws IllegalMovesException, WrongPointsException {
        try {
            pm.playerJoin(new Player("uno", pm));
            pm.playerJoin(new Player("due", pm));
        } catch (IllegalTypeInProduction e) {
            e.printStackTrace();
        }

        FaithTrack track = new FaithTrack();
        Resource negative = ResourceBuilder.buildFaithPoint(-3);
        Resource first = ResourceBuilder.buildFaithPoint(1);
        Resource last = ResourceBuilder.buildFaithPoint(18);
        Resource error = ResourceBuilder.buildServant(3);

        assertEquals(0,track.getPlayerPosition());
        track.movePlayer(first.amount(), pm);
        track.movePlayer(last.amount(), pm);
        track.movePlayer(last.amount(), pm);
        assertEquals(24, track.getPlayerPosition());

        try {
            track.movePlayer(first.amount(), pm);
            fail();
        } catch (IllegalMovesException e) {
            e.printStackTrace();
        }

        try {
            track.movePlayer(negative.amount(), pm);
            fail();
        } catch (WrongPointsException e) {
            System.out.println(e.getMsg());
        }

        try{
            track.movePlayer(error.amount(), pm);
            fail();
        } catch (IllegalMovesException e){
            e.printStackTrace();
        }
    }

    @Test
    public void flipOtherPopeTile() throws WrongPointsException, IllegalMovesException, LorenzoMovesException, NotHisTurnException, TurnStartedException, EmptyDeckException {
        Player player1 = null;
        Player player2 = null;

        try {
            player1 = new Player(TextColors.BLUE + "gino" + TextColors.RESET, pm);
            player2 = new Player(TextColors.PURPLE + "lino" + TextColors.RESET, pm);
        } catch (Exception e) {
            fail();
        }

        pm.playerJoin(player1);
        pm.playerJoin(player2);

        FaithTrack ft1 = player1.getFT_forTest();
        FaithTrack ft2 = player2.getFT_forTest();

        try {
            pm.startGame();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        pm.getcurr_test().moveFaithMarker(4);
        pm.getcurr_test().endThisTurn();

        pm.getcurr_test().moveFaithMarker(9);
        pm.getcurr_test().endThisTurn();

        assertTrue(pm.getcurr_test().getFT_forTest().isFlipped(VaticanSpace.FIRST));
        pm.getcurr_test().endThisTurn();
        assertFalse(pm.getcurr_test().getFT_forTest().isFlipped(VaticanSpace.FIRST));
        pm.getcurr_test().endThisTurn();

        pm.getcurr_test().moveFaithMarker(8);
        pm.getcurr_test().endThisTurn();

        pm.getcurr_test().moveFaithMarker(7);
        pm.getcurr_test().endThisTurn();

        assertFalse(pm.getcurr_test().getFT_forTest().isFlipped(VaticanSpace.SECOND));
        pm.getcurr_test().endThisTurn();
        assertFalse(pm.getcurr_test().getFT_forTest().isFlipped(VaticanSpace.SECOND));

        pm.getcurr_test().moveFaithMarker(20);
        pm.getcurr_test().endThisTurn();

        assertTrue(pm.getcurr_test().getFT_forTest().isFlipped(VaticanSpace.THIRD));
        pm.getcurr_test().endThisTurn();
        assertFalse(pm.getcurr_test().getFT_forTest().isFlipped(VaticanSpace.THIRD));
        pm.getcurr_test().endThisTurn();

    }

}
