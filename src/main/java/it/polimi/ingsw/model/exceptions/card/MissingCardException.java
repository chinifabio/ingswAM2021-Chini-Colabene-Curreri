package it.polimi.ingsw.model.exceptions.card;

public class MissingCardException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public MissingCardException(String missed) {
        super("This card was not found: " + missed);
    }

    /**
     * Prints this throwable and its backtrace to the
     * standard error stream.
     */
    @Override
    public void printStackTrace() {
        System.out.println(super.getMessage());
    }
}
