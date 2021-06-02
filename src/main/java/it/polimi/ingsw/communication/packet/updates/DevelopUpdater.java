package it.polimi.ingsw.communication.packet.updates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.litemodel.LiteModel;
import it.polimi.ingsw.litemodel.litecards.LiteDevCard;
import it.polimi.ingsw.model.player.personalBoard.DevCardSlot;
import it.polimi.ingsw.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevelopUpdater extends Updater {

    private final String nickname;

    //private final List<List<LiteDevCard>> deck;
    private final HashMap<DevCardSlot, List<LiteDevCard>> deck;

    @JsonCreator
    public DevelopUpdater(@JsonProperty("nickname") String nickname, @JsonProperty("deck") HashMap<DevCardSlot, List<LiteDevCard>> deck) {
        this.deck = deck;
        this.nickname = nickname;
    }

    /**
     * Take a lite model as input and apply to the implementing function
     *
     * @param liteModel the lite model on the client
     */
    @Override
    public void update(LiteModel liteModel, View view) {
        liteModel.setDevelop(nickname, deck);
    }
}
