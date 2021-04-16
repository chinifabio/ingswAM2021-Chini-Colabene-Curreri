package it.polimi.ingsw;


import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.cards.ColorDevCard;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LevelDevCard;
import it.polimi.ingsw.model.cards.effects.AddProductionEffect;
import it.polimi.ingsw.model.exceptions.EmptyDeckException;
import it.polimi.ingsw.model.exceptions.MissingCardException;
import it.polimi.ingsw.model.exceptions.productionException.IllegalTypeInProduction;
import it.polimi.ingsw.model.player.personalBoard.DevCardSlot;
import it.polimi.ingsw.model.player.personalBoard.PersonalBoard;
import it.polimi.ingsw.model.player.personalBoard.warehouse.depot.DepotBuilder;
import it.polimi.ingsw.model.player.personalBoard.warehouse.production.NormalProduction;
import it.polimi.ingsw.model.player.personalBoard.warehouse.production.Production;
import it.polimi.ingsw.model.requisite.Requisite;
import it.polimi.ingsw.model.requisite.ResourceRequisite;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * test collectors for PersonalBoard
 */
public class PersonalBoardTest {

    /**
     * This test creates a PersonalBoard and add
     */
    @Test
    void DevCards() {
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);
        List<Resource> sample = new ArrayList<>();
        Production p = null;

        DevCard c1 = new DevCard("000", new AddProductionEffect(p), 2, LevelDevCard.LEVEL1, ColorDevCard.GREEN, req);
        DevCard c2 = new DevCard("111", new AddProductionEffect(p), 4, LevelDevCard.LEVEL2, ColorDevCard.YELLOW, req);
        DevCard c3 = new DevCard("222", new AddProductionEffect(p), 6, LevelDevCard.LEVEL3, ColorDevCard.BLUE, req);
        DevCard c31 = new DevCard("333", new AddProductionEffect(p), 0, LevelDevCard.LEVEL3, ColorDevCard.PURPLE, req);

        DevCardSlot dcsLeft = DevCardSlot.LEFT;
        DevCardSlot dcsCenter = DevCardSlot.CENTER;
        DevCardSlot dcsRight = DevCardSlot.RIGHT;

        PersonalBoard personalBoard = new PersonalBoard();

        if (personalBoard.addDevCard(dcsLeft, c1)) {
            assertTrue(c1.equals(personalBoard.viewDevCards().get(dcsLeft)));
        } else {

        }

        if (personalBoard.addDevCard(dcsLeft, c2)) {
            assertTrue(c2.equals(personalBoard.viewDevCards().get(dcsLeft)));
        } else {
            fail();
        }

        if (personalBoard.addDevCard(dcsCenter, c2)) {
            fail();
        } else {
            try {
                assertTrue(personalBoard.viewDevCards().get(dcsCenter).equals(null));
                fail();
            } catch (NullPointerException e) {
                System.out.println("Sto cercando di guardare una carta che non esiste!");
            }
        }

        if (personalBoard.addDevCard(dcsLeft, c3)){
            assertTrue(c3.equals(personalBoard.viewDevCards().get(dcsLeft)));
        } else {
            fail();
        }

        if (personalBoard.addDevCard(dcsCenter, c3)){
            fail();
        } else {
            try {
                assertTrue(personalBoard.viewDevCards().get(dcsCenter).equals(null));
                fail();
            } catch (NullPointerException e) {
                System.out.println("Sto cercando di guardare una carta che non esiste!");
            }
        }

        if (personalBoard.addDevCard(dcsCenter, c1)){
            assertTrue(c1.equals(personalBoard.viewDevCards().get(dcsCenter)));
        } else {
            fail();
        }

        if (personalBoard.addDevCard(dcsCenter, c3)){
            fail();
        } else {
                assertTrue(c1.equals(personalBoard.viewDevCards().get(dcsCenter)));
            }

        if (personalBoard.addDevCard(dcsLeft, c31)){
            fail();
        } else {
                assertTrue(c3.equals(personalBoard.viewDevCards().get(dcsLeft)));
        }

    }

    /**
     * This test creates two LeaderCards, adds them to the deck and activate them.
     */
    @Test
    void ActivateLeaderCard(){
        String ID1="000", ID2="111";
        List<Resource> sample = new ArrayList<>();

        Production p = null;
        try {
            p = new NormalProduction( sample, sample);
        } catch (IllegalTypeInProduction illegalTypeInProduction) {
            fail();
        }

        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);



        LeaderCard c1 = new LeaderCard(ID1, new AddProductionEffect(p), 1, req);
        LeaderCard c2 = new LeaderCard(ID2, new AddProductionEffect(p), 2, req);

        PersonalBoard personalBoard = new PersonalBoard();

        personalBoard.addLeaderCard(c1);
        try {
            assertTrue(personalBoard.viewLeaderCard().peekFirstCard().equals(c1));
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
            fail();
        }

        personalBoard.addLeaderCard(c1);

        assertEquals(1,personalBoard.viewLeaderCard().getNumberOfCards());

        personalBoard.addLeaderCard(c2);

        assertEquals(2,personalBoard.viewLeaderCard().getNumberOfCards());

        try {
            assertFalse(personalBoard.viewLeaderCard().peekCard(ID1).isActivated());
        } catch (MissingCardException e) {
            System.out.println(e.getMsg());
        }

        personalBoard.activateLeaderCard(ID1);
        System.out.println("Ho attivato la carta con id: "+ID1);

        try {
            assertTrue(personalBoard.viewLeaderCard().peekCard(ID1).isActivated());
        } catch (MissingCardException e) {
            System.out.println(e.getMsg());
        }
    }

    /**
     * This test creates two LeaderCards and discard them one by one
     */
    @Test
    void DiscardLeaderCard(){
        String ID1="000", ID2="111";
        List<Resource> sample = new ArrayList<>();

        Production p = null;

        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);



        LeaderCard c1 = new LeaderCard(ID1, new AddProductionEffect(p), 1, req);
        LeaderCard c2 = new LeaderCard(ID2, new AddProductionEffect(p), 2, req);

        PersonalBoard personalBoard = new PersonalBoard();

        personalBoard.addLeaderCard(c1);
        personalBoard.addLeaderCard(c2);

        assertEquals(2,personalBoard.viewLeaderCard().getNumberOfCards());

        personalBoard.discardLeaderCard(ID1);

        assertEquals(1,personalBoard.viewLeaderCard().getNumberOfCards());

        try {
            personalBoard.viewLeaderCard().peekCard(ID1);
            fail();
        } catch (MissingCardException e) {
            System.out.println(e.getMsg());
        }

        personalBoard.discardLeaderCard(ID1);

        personalBoard.discardLeaderCard(ID2);

        try {
            personalBoard.viewLeaderCard().peekCard(ID1);
            fail();
        } catch (MissingCardException e) {
            System.out.println(e.getMsg());
        }

        assertEquals(0,personalBoard.viewLeaderCard().getNumberOfCards());

    }

    /**
     * This test
     */
    @Test
    void Resources(){
        PersonalBoard personalBoard = new PersonalBoard();

        System.out.println(personalBoard.viewResources());

    }

    /**
     * This test create a personalBoard and moves the player and Lorenzo on its faith track.
     */
    @Test
    void FaithTrackMoves(){
        Resource first = ResourceBuilder.buildFaithPoint(1);
        Resource ten = ResourceBuilder.buildFaithPoint(10);

        PersonalBoard personalBoard = new PersonalBoard();

        assertEquals(0,personalBoard.FaithMarkerPosition());
        assertTrue(personalBoard.moveFaithMarker(first.amount()));
        assertEquals(1,personalBoard.FaithMarkerPosition());
        assertTrue(personalBoard.moveFaithMarker(first.amount()));
        assertEquals(2,personalBoard.FaithMarkerPosition());
        assertTrue(personalBoard.moveFaithMarker(ten.amount()));
        assertEquals(12,personalBoard.FaithMarkerPosition());
        assertTrue(personalBoard.moveFaithMarker(ten.amount()));
        assertEquals(22,personalBoard.FaithMarkerPosition());
        assertTrue(personalBoard.moveFaithMarker(ten.amount()));
        assertEquals(24,personalBoard.FaithMarkerPosition());
        assertFalse(personalBoard.moveFaithMarker(first.amount()));

    }


    /**
     * This test checks the correct functioning of the popeTile
     */
    @Test
    void PopeTile(){
    //    PersonalBoard personalBoard1 = new PersonalBoard();
    //    PersonalBoard personalBoard2 = new PersonalBoard();
//
    //    Resource first = ResourceBuilder.buildFaithPoint(1);
    //    Resource second = ResourceBuilder.buildFaithPoint(2);
//
    //    assertTrue(personalBoard1.moveFaithMarker(second.amount()));
    //    if(personalBoard1.){}
    //    assertTrue(personalBoard1.moveFaithMarker(second.amount()));
    //    assertTrue(personalBoard1.moveFaithMarker(second.amount()));
    }
}