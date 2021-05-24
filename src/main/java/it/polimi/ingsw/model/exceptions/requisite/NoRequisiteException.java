package it.polimi.ingsw.model.exceptions.requisite;

public class NoRequisiteException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public NoRequisiteException() {
        super("There are no requirements!");
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
