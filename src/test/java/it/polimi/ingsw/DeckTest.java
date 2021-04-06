package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.effects.AddDiscount;
import it.polimi.ingsw.model.cards.effects.AddProduction;
import it.polimi.ingsw.model.cards.effects.DestroyCards;
import it.polimi.ingsw.model.cards.effects.ShuffleMoveOne;
import it.polimi.ingsw.model.exceptions.AlreadyInDeckException;
import it.polimi.ingsw.model.exceptions.EmptyDeckException;
import it.polimi.ingsw.model.exceptions.MissingCardException;
import it.polimi.ingsw.model.player.personalBoard.Production;
import it.polimi.ingsw.model.requisite.Requisite;
import it.polimi.ingsw.model.requisite.ResourceRequisite;
import it.polimi.ingsw.model.resource.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test collector for cards and decks.
 */
public class DeckTest {

    /**
     * This test creates a series of different cards and checks their CardID.
     */
    @Test
    void cardIDCheck(){
        String ID = "105", ID1 = "120",ID2 = "145";
        Production p = new Production();
        List<Requisite> requisite = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        requisite.add(rr);

        DevCard c1 = new DevCard(ID, new AddProduction(p), 3, LevelDevCard.LEVEL3, ColorDevCard.BLUE,requisite);
        LeaderCard c2 = new LeaderCard(ID1, new AddProduction(p),6,requisite);
        SoloActionToken token = new SoloActionToken(ID2, new AddProduction(p));

        assertEquals(ID,c1.getCardID());
        assertEquals(ID1,c2.getCardID());
        assertEquals(ID2,token.getCardID());
    }

    /**
     * This test creates a DevCard and checks the victory point of the DevCard.
     */
    @Test
    void victoryPointDevCard(){
        int n = 7;
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        DevCard c1 = new DevCard("000", new AddProduction(p), n, LevelDevCard.LEVEL3, ColorDevCard.BLUE,req);

        assertEquals(n,c1.getVictoryPoint());
    }

    /**
     * This test creates a DevCard and checks its color and level.
     */
    @Test
    void colorLevelDevCard(){
        LevelDevCard lev = LevelDevCard.LEVEL2;
        ColorDevCard col = ColorDevCard.BLUE;
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        Production p = new Production();

        DevCard c1 = new DevCard("000", new AddProduction(p), 2, lev, col,req);

        assertEquals(lev,c1.getLevel());
        assertEquals(col,c1.getColor());
    }

    /**
     * This test creates a LeaderCard and checks its victory points.
     */
    @Test
    void victoryPointLeaderCard(){
        int n = 12;
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        LeaderCard c = new LeaderCard("010", new AddProduction(p), n, req);

        assertEquals(n,c.getVictoryPoint());
    }

    /**
     * This test creates a LeaderCard and checks its activated parameter before and after its activation.
     */
    @Test
    void activatedLeaderCard(){
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        LeaderCard c = new LeaderCard("010", new AddProduction(p), 6, req);

        assertFalse(c.isActivated());
        c.activate();
        assertTrue(c.isActivated());
        c.activate();
        assertTrue(c.isActivated());
    }

    /**
     * This test creates an empty deck and adds one card to it. It checks if the number of card is correct before and after adding the card.
     */
    @Test
    void insertCard() {
        Deck<DevCard> d = new Deck<>();
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        assertEquals(0,d.getNumberOfCards());

        DevCard c1 = new DevCard("000", new AddProduction(p),10, LevelDevCard.LEVEL1, ColorDevCard.GREEN, req);
        DevCard c2 = new DevCard("000", new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.GREEN, req);
        DevCard c3 = new DevCard("111", new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.GREEN, req);

        try { d.insertCard(c1); } catch (AlreadyInDeckException e) {
            System.out.println(e.getMsg());
        }
        try { d.insertCard(c2); } catch (AlreadyInDeckException e) {
            System.out.println(e.getMsg());
        }
        try { d.insertCard(c3); } catch (AlreadyInDeckException e) {
            System.out.println(e.getMsg());
        }

        assertEquals(2,d.getNumberOfCards());
    }

    /**
     * This test creates a deck and passes to it a List of Card. It insert another list of Card and checks then the number of cards that the deck contains.
     */
    @Test
    void addListOfCards(){
        int n=2;
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        Card c1 = new DevCard("000", new AddProduction(p),10, LevelDevCard.LEVEL1, ColorDevCard.GREEN,req);
        Card c2 = new DevCard("001", new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.YELLOW,req);

        List<Card> cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        Deck<Card> d = new Deck<>();

        for(int i=0;i<n;i++){
            try {
                d.insertCard(cards.get(i));
            } catch (AlreadyInDeckException e) {
                System.out.println(e.getMsg());
            }
        }

        assertEquals(n,d.getNumberOfCards());
    }

    /**
     * This test creates a deck and passes to it a single Card. It then add another two and check the number of card.
     */
    @Test
    void addOneInsertCard() {
        int n=3;
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        DevCard c1 = new DevCard("000", new AddProduction(p), 10, LevelDevCard.LEVEL3, ColorDevCard.GREEN,req);
        DevCard c2 = new DevCard("000",  new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.YELLOW,req);
        DevCard c3 = new DevCard("002",  new AddProduction(p),2, LevelDevCard.LEVEL1, ColorDevCard.PURPLE,req);

        List<DevCard> cards = new ArrayList<>();
        cards.add(c2);
        cards.add(c3);

        Deck<DevCard> d1 = new Deck<>(c1);

        for(int i=0;i<n-1;i++){
            try {
                d1.insertCard(cards.get(i));
            } catch (AlreadyInDeckException e) {
                System.out.println(e.getMsg());
            }
        }

        assertEquals(n-1,d1.getNumberOfCards());
    }

    /**
     * This test creates two decks, one empty and one with 3 cards. It checks if the method draw throws an exception when called by an empty deck.
     * It then checks if the number of cards of the second deck are updated once one is drawn and if that card is the one on top.
     */
    @Test
    void drawFromDeck(){
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        DevCard c1 = new DevCard("000", new AddProduction(p), 10, LevelDevCard.LEVEL3, ColorDevCard.GREEN,req);
        DevCard c2 = new DevCard("001",  new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.YELLOW,req);
        DevCard c3 = new DevCard("002",  new AddProduction(p),2, LevelDevCard.LEVEL1, ColorDevCard.PURPLE,req);
        List<DevCard> cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        Deck<Card> d1 = new Deck<>();
        Deck<DevCard> d2 = new Deck<>();
        for(int i=0;i<3;i++) {
            try {
                d2.insertCard(cards.get(i));
            } catch (AlreadyInDeckException e) {
                System.out.println(e.getMsg());
            }
        }
        assertEquals(0,d1.getNumberOfCards());
        assertEquals(3,d2.getNumberOfCards());
        try {
            d1.draw();
            fail();
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
        }
        try {
            DevCard cTop = d2.draw();
            assertEquals(2,d2.getNumberOfCards());
            assertEquals("000",cTop.getCardID());
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
            fail();
        }
    }

    /**
     * This test creates two decks, one empty and one with 3 cards. It checks if the method discard throws an exception when called by an empty deck.
     * It then checks if the number of cards of the second deck are updated once one is discarded.
     */
    @Test
    void discardCards(){
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        DevCard c1 = new DevCard("100", new AddProduction(p), 10, LevelDevCard.LEVEL3, ColorDevCard.GREEN,req);
        DevCard c2 = new DevCard("001",  new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.YELLOW,req);
        DevCard c3 = new DevCard("002",  new AddProduction(p),2, LevelDevCard.LEVEL1, ColorDevCard.PURPLE,req);
        List<DevCard> cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        Deck<Card> d1 = new Deck<>();
        Deck<DevCard> d2 = new Deck<>();
        for(int i=0;i<3;i++) {
            try {
                d2.insertCard(cards.get(i));
            } catch (AlreadyInDeckException e) {
                System.out.println(e.getMsg());
            }
        }
        assertEquals(0,d1.getNumberOfCards());
        assertEquals(3,d2.getNumberOfCards());
        try {
            d1.discard();
            fail();
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
        }
        try {
            d2.discard();
            assertEquals(2,d2.getNumberOfCards());
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
            fail();
        }
    }

    /**
     * This test creates a deck and tries to peek a card passing a cardID.
     */
    @Test
    void peekCardFromDeck(){
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);
        String ID = "100", ID1 = "010",ID2 = "001";

        DevCard c1 = new DevCard(ID, new AddProduction(p), 10, LevelDevCard.LEVEL3, ColorDevCard.GREEN,req);
        DevCard c2 = new DevCard(ID1, new AddProduction(p),5, LevelDevCard.LEVEL2, ColorDevCard.YELLOW,req);
        DevCard c3 = new DevCard(ID2, new AddProduction(p),2, LevelDevCard.LEVEL1, ColorDevCard.PURPLE,req);
        List<DevCard> cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        Deck<DevCard> d = new Deck<>();
        for(int i=0;i<3;i++) {
            try {
                d.insertCard(cards.get(i));
            } catch (AlreadyInDeckException e) {
                System.out.println(e.getMsg());
            }
        }

        try {
            d.peekCard("000");
            fail();
        } catch (MissingCardException e) {
            System.out.println(e.getMsg());
        }
        try {
            assertEquals(LevelDevCard.LEVEL3,d.peekCard(ID).getLevel());
        } catch (MissingCardException e) {
            System.out.println(e.getMsg());
            fail();
        }
    }

    /**
     * This test creates a deck and shuffles it at the start. It then discard two cards, shuffle again and check if the size of the deck is 3.
     */
    @Test
    void shuffleDeck(){
        Production p = new Production();
        List<Requisite> req = new ArrayList<>();
        Resource coin = ResourceBuilder.buildCoin(2);
        ResourceRequisite rr = new ResourceRequisite(coin);
        req.add(rr);

        LeaderCard c1 = new LeaderCard("111", new AddProduction(p), 1, req);
        LeaderCard c2 = new LeaderCard("222", new AddProduction(p), 2, req);
        LeaderCard c3 = new LeaderCard("333", new AddProduction(p), 3, req);
        LeaderCard c4 = new LeaderCard("444", new AddProduction(p), 4, req);
        LeaderCard c5 = new LeaderCard("555", new AddProduction(p), 5, req);
        LeaderCard c6 = new LeaderCard("666", new AddProduction(p), 6, req);
        LeaderCard c7 = new LeaderCard("777", new AddProduction(p), 7, req);
        Deck<LeaderCard> d = new Deck<>(c1);
        List<LeaderCard> cards = new ArrayList<>();
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        cards.add(c5);
        cards.add(c6);
        cards.add(c7);
        for(int i=0;i<6;i++) {
            try {
                d.insertCard(cards.get(i));
            } catch (AlreadyInDeckException e) {
                System.out.println(e.getMsg());
            }
        }

        assertEquals(7,d.getNumberOfCards());
        d.shuffle();
        assertEquals(7,d.getNumberOfCards());
        try {
            d.discard();
            assertEquals(6,d.getNumberOfCards());
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
        }
        try {
            d.discard();
            assertEquals(5,d.getNumberOfCards());
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
        }
        try {
            d.discard();
            assertEquals(4,d.getNumberOfCards());
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
        }
        try {
            d.discard();
            assertEquals(3,d.getNumberOfCards());
        } catch (EmptyDeckException e) {
            System.out.println(e.getMsg());
        }
        d.shuffle();
        assertEquals(7,d.getNumberOfCards());
    }
}