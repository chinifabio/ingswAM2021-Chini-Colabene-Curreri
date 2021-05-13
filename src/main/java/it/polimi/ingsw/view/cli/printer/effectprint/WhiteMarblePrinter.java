package it.polimi.ingsw.view.cli.printer.effectprint;

import it.polimi.ingsw.TextColors;
import it.polimi.ingsw.litemodel.litemarkettray.LiteMarble;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public class WhiteMarblePrinter implements EffectPrinter{

    private LiteMarble marble;

    private final Map<ResourceType, String> colors;

    public WhiteMarblePrinter(LiteMarble marble) {
        this.marble = marble;
        this.colors = new EnumMap<>(ResourceType.class);
        colors.put(ResourceType.COIN, TextColors.colorText(TextColors.YELLOW_BRIGHT,"©"));
        colors.put(ResourceType.SHIELD, TextColors.colorText(TextColors.BLUE_BRIGHT,"▼"));
        colors.put(ResourceType.SERVANT, TextColors.colorText(TextColors.PURPLE,"Õ"));
        colors.put(ResourceType.STONE, TextColors.colorText(TextColors.WHITE,"■"));
        colors.put(ResourceType.UNKNOWN,TextColors.colorText(TextColors.WHITE_BRIGHT,"?"));
        colors.put(ResourceType.FAITHPOINT, TextColors.colorText(TextColors.RED,"┼"));
    }

    @Override
    public void printEffect(String[][] leaderCard) {
        for (int i = 1; i < leaderCard[3].length-1; i++){
            leaderCard[3][i] = "-";
        }
        leaderCard[4][3] = "O";
        leaderCard[4][5] = "=";
        leaderCard[4][7] = colors.get(marble.getToResource());
        for (int i = 1; i < leaderCard[5].length-1; i++){
            leaderCard[5][i] = "-";
        }
    }
}
