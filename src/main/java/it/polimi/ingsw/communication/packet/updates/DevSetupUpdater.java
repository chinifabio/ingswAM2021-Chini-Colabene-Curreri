package it.polimi.ingsw.communication.packet.updates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.litemodel.LiteModel;
import it.polimi.ingsw.litemodel.litecards.LiteDevSetup;
import it.polimi.ingsw.view.View;

public class DevSetupUpdater extends Updater {

    private final LiteDevSetup setup;

    @JsonCreator
    public DevSetupUpdater(@JsonProperty("setup") LiteDevSetup setup) {
        this.setup = setup;
    }

    /**
     * Take a lite model as input and apply to the implementing function
     *
     * @param liteModel the lite model on the client
     */
    @Override
    public void update(LiteModel liteModel, View view) {
        view.notifyPlayer("card market modified");
        liteModel.setDevSetup(this.setup);
    }
}
