package it.polimi.ingsw.communication.packet.clientexecutable;

import it.polimi.ingsw.view.View;

/**
 * Render the game creator in the client
 */
public class FireGameCreator extends ClientExecutable {
    /**
     * Execute a command on the view
     * @param view the view on with execute the command
     */
    @Override
    public void execute(View view) {
        view.fireGameCreator();
    }
}
