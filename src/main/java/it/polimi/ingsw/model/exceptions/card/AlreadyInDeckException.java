package it.polimi.ingsw.model.exceptions.card;

import it.polimi.ingsw.TextColors;
import it.polimi.ingsw.model.cards.Card;

import static it.polimi.ingsw.TextColors.*;

public class AlreadyInDeckException extends Exception{
    /**
     * the required resource list of wrong production
     */
    private final Card already;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public AlreadyInDeckException(Card already) {
        super(TextColors.colorText(RED_BRIGHT, "This card is already in the Deck: " + already.getCardID()));
        this.already = already;
    }

    /**
     * Prints this throwable and its backtrace to the
     * standard error stream.
     */
    @Override
    public void printStackTrace() {
        System.out.println(TextColors.colorText(RED_BRIGHT, "This card is already in the Deck: " + this.already.getCardID()));
    }
}
