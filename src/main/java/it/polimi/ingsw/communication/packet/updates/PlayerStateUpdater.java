package it.polimi.ingsw.communication.packet.updates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.litemodel.LiteModel;
import it.polimi.ingsw.litemodel.liteplayer.LiteState;

public class PlayerStateUpdater extends Updater {
    private final LiteState state;

    @JsonCreator
    public PlayerStateUpdater(@JsonProperty("state") LiteState state) {
        this.state = state;
    }

    /**
     * Take a lite model as input and apply to the implementing function
     *
     * @param liteModel the lite model on the client
     */
    @Override
    public void update(LiteModel liteModel) {
        liteModel.setPlayerState(this.state);
    }
}