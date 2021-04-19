package it.polimi.ingsw.model.cards.effects;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.model.exceptions.faithtrack.EndGameException;
import it.polimi.ingsw.model.exceptions.game.GameException;
import it.polimi.ingsw.model.exceptions.warehouse.WrongPointsException;
import it.polimi.ingsw.model.player.PlayerReactEffect;

/**
 * This class is a part of the strategy pattern, it implements the interface Effect.
 */
public class MoveTwoEffect extends Effect{

    @JsonCreator
    public MoveTwoEffect() {}

    /**
     * This method is activated by a SoloActionToken, it gives two FaithPoint to Lorenzo.
     * @param p the only player in the game.
     */
    @Override
    public void use(PlayerReactEffect p) throws EndGameException {
        System.out.println("Moving two spaces");
        p.moveFaithMarker(2); // aggiungere movimento 2
    }
}
