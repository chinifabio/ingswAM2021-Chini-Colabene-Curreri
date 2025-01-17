package it.polimi.ingsw.matchTests;

import it.polimi.ingsw.model.VirtualView;
import it.polimi.ingsw.model.exceptions.warehouse.production.IllegalTypeInProduction;
import it.polimi.ingsw.model.match.markettray.RowCol;
import it.polimi.ingsw.model.match.match.Match;
import it.polimi.ingsw.model.match.match.MultiplayerMatch;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.personalBoard.warehouse.depot.DepotSlot;
import it.polimi.ingsw.model.resource.ResourceBuilder;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplayerMatchTest {
    Player pino;
    Player gino;
    Player dino;
    Player tino;

    List<Player> order = new ArrayList<>();

    VirtualView view = new VirtualView();
    Match multiplayer;

    @BeforeEach
    public void initializeMatch() throws IllegalTypeInProduction, IOException {
        try {
            multiplayer = new MultiplayerMatch(4, view);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        pino = new Player("pino", multiplayer, view);
        assertTrue(multiplayer.playerJoin(pino));
        order.add(pino);

        gino = new Player("gino", multiplayer, view);
        assertTrue(multiplayer.playerJoin(gino));
        order.add(gino);

        dino = new Player("dino", multiplayer, view);
        assertTrue(multiplayer.playerJoin(dino));
        order.add(dino);

        tino = new Player("tino", multiplayer, view);
        assertTrue(multiplayer.playerJoin(tino));
        order.add(tino);

        multiplayer.initialize();
        Collections.rotate(order, -order.indexOf(multiplayer.currentPlayer()));
        assertTrue(multiplayer.isGameOnAir());

        // initializing player section
        /*
        1st - 0 resource to choose and 0 faith points
        2nd - 1 resource to choose and 0 faith points
        3rd - 1 resource to choose and 1 faith points
        4th - 2 resource to choose and 1 faith points
         */

        assertDoesNotThrow(()-> order.get(0).test_discardLeader());
        assertDoesNotThrow(()-> order.get(0).test_discardLeader());
        order.get(0).endThisTurn();

        assertDoesNotThrow(()-> order.get(1).chooseResource(DepotSlot.BOTTOM, ResourceType.COIN));
        assertDoesNotThrow(()-> order.get(1).test_discardLeader());
        assertDoesNotThrow(()-> order.get(1).test_discardLeader());
        assertDoesNotThrow(() -> assertEquals(order.get(1).test_getPB().getDepots().get(DepotSlot.BOTTOM).viewResources().get(0), ResourceBuilder.buildCoin()));
        order.get(1).endThisTurn();

        assertDoesNotThrow(()-> order.get(2).chooseResource(DepotSlot.BOTTOM, ResourceType.COIN));
        assertDoesNotThrow(()-> order.get(2).test_discardLeader());
        assertDoesNotThrow(()-> order.get(2).test_discardLeader());
        assertEquals(1, order.get(2).test_getPB().getFT_forTest().getPlayerPosition());
        assertDoesNotThrow(() -> assertEquals(order.get(2).test_getPB().getDepots().get(DepotSlot.BOTTOM).viewResources().get(0), ResourceBuilder.buildCoin()));
        order.get(2).endThisTurn();

        order.get(3).chooseResource(DepotSlot.BOTTOM, ResourceType.COIN);
        order.get(3).chooseResource(DepotSlot.BOTTOM, ResourceType.STONE);
        order.get(3).chooseResource(DepotSlot.MIDDLE, ResourceType.STONE);
        assertDoesNotThrow(()-> order.get(3).test_discardLeader());
        assertDoesNotThrow(()-> order.get(3).test_discardLeader());
        assertEquals(1, order.get(3).test_getPB().getFT_forTest().getPlayerPosition());
        assertDoesNotThrow(() -> assertEquals(order.get(3).test_getPB().getDepots().get(DepotSlot.BOTTOM).viewResources().get(0), ResourceBuilder.buildCoin()));
        assertDoesNotThrow(() -> assertEquals(order.get(3).test_getPB().getDepots().get(DepotSlot.MIDDLE).viewResources().get(0), ResourceBuilder.buildStone()));
        order.get(3).endThisTurn();

        // creating a list of the players in order to have player(0) = inkwell player
        Collections.rotate(order, -order.indexOf(multiplayer.currentPlayer()));
    }

    @RepeatedTest(5)
    public void endMatchByEndFaithTrack() {

        order.get(0).obtainResource(DepotSlot.BUFFER, ResourceBuilder.buildFaithPoint(24));

        order.get(1).test_endTurnNoMain();
        order.get(2).test_endTurnNoMain();
        order.get(3).test_endTurnNoMain();

        assertFalse(multiplayer.isGameOnAir());
    }
}
