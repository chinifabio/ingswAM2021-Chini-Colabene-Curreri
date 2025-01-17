package it.polimi.ingsw.communication.packet.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.player.PlayerAction;
import it.polimi.ingsw.model.player.personalBoard.warehouse.production.NormalProduction;
import it.polimi.ingsw.model.player.personalBoard.warehouse.production.ProductionID;

/**
 * This command is used for set a normal production of a passed player
 */
public class SetNormalProductionCommand extends Command{

    /**
     * The id of the production to normalize
     */
    private final ProductionID id;

    /**
     * The normalized production
     */
    private final NormalProduction prod;

    /**
     * This is the constructor of the class
     * @param id is the identifier of the production
     * @param prod is the production to normalize
     */
    @JsonCreator
    public SetNormalProductionCommand(@JsonProperty("id") ProductionID id, @JsonProperty("prod") NormalProduction prod) {
        this.id = id;
        this.prod = prod;
    }

    /**
     * The command to execute on a player action interface
     *
     * @param player the player on which execute the command
     */
    @Override
    public void execute(PlayerAction player) {
        player.setNormalProduction(id, prod);
    }
}
