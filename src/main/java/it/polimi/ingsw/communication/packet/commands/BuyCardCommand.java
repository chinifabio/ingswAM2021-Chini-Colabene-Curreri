package it.polimi.ingsw.communication.packet.commands;

import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.model.player.PlayerAction;

/**
 * This command introduce the Player into the state where he can buy the DevCards
 */
public class BuyCardCommand extends Command{

    /**
     * The command to execute on a player action interface
     * @param player the player on which execute the command
     */
    @Override
    public Packet execute(PlayerAction player) {
        return player.buyCard();
    }
}
