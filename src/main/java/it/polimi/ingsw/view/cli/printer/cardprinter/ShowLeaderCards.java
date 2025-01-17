package it.polimi.ingsw.view.cli.printer.cardprinter;

import it.polimi.ingsw.litemodel.litecards.LiteLeaderCard;

import java.util.List;

/**
 * This class is the Printer of all the LeaderCards of the Player
 */
public class ShowLeaderCards {

    /**
     * This attribute is the height of the Leader Cards
     */
    private static final int HEIGHT = 11; //rows.

    /**
     * This attribute is the width of the Leader Cards
     */
    private static final int WIDTH = 60; //cols.

    /**
     * This methods prints all the LeaderCards of the Player
     * @param toShow is the List of LeaderCards to show
     */
    public static void printLeaderCardsPlayer(List<LiteLeaderCard> toShow) {
        String[][] leaderCards = new String[HEIGHT][WIDTH];
        for (int i = 0; i< HEIGHT-1; i++){
            for (int j = 0; j<WIDTH-1; j++){
                leaderCards[i][j] = " ";
            }
        }
        int index = 0;
        for (LiteLeaderCard card : toShow) {
            LeaderCardPrinter.createLeaderCard(leaderCards, card, 0, index, card.isActivated());
            index = index + 14;
        }

        for (int i = 0; i< HEIGHT-1; i++){
            System.out.println();
            for (int j = 0; j<WIDTH-1; j++){
                System.out.print(leaderCards[i][j]);
            }
        }
        System.out.println();
    }

    /**
     * This method creates all the LeaderCards of the Player
     * @param display is where the LeaderCards will be created
     * @param toShow is the List of LeaderCards of the Player
     * @param x is the horizontal coordinate
     * @param y is the vertical coordinate
     */
    public static void createLeaderCardsSlot(String[][] display, List<LiteLeaderCard> toShow, int x, int y){
        int index = y;
        for (LiteLeaderCard card : toShow) {
            LeaderCardPrinter.createLeaderCard(display, card, x, index, card.isActivated());
            index = index + 14;
        }
    }



}
