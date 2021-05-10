package it.polimi.ingsw.communication.packet.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.model.player.PlayerAction;

/**
 * This command activate a leader card from the passed id.
 */
public class ActivateLeaderCommand extends Command{
    /**
     * The id of the card to activate
     */
    private final String card;

    @JsonCreator
    public ActivateLeaderCommand(@JsonProperty("card") String card) {
        this.card = card;
    }

    /**
     * The command to execute on a player action interface
     *
     * @param player the player on which execute the command
     */
    @Override
    public Packet execute(PlayerAction player) {
        return player.activateLeaderCard(this.card);
    }
}
