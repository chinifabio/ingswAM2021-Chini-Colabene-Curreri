package it.polimi.ingsw.model.exceptions;

public class ExtraDepotsException extends Exception {
    /**
     * msg to identify the error
     */
    private String msg;

    /**
     * costructor that save the message to handle
     * @param msg string
     */
    public ExtraDepotsException(String msg) {
        this.msg = msg;
    }

    /**
     * return the message handled
     * @return string of the message
     */
    public String getMsg(){
        return msg;
    }
}
