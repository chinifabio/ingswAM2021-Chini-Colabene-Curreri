package it.polimi.ingsw.model.exceptions.card;

import static it.polimi.ingsw.TextColors.*;

public class EmptyDeckException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public EmptyDeckException() {
        super(colorText(RED_BRIGHT, "This Deck is empty!"));
    }

    /**
     * Prints this throwable and its backtrace to the
     * standard error stream.
     */
    @Override
    public void printStackTrace() {
        System.out.println(colorText(RED_BRIGHT, "This Deck is empty!"));
    }
}