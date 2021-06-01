package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.cards.ColorDevCard;
import it.polimi.ingsw.model.match.markettray.MarkerMarble.MarbleColor;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.*;

public class Colors {
    public static String color(String Colors, String text){
        return Colors + text + RESET ;
    }

    public static String colorBackGround(String Colors, String background, String text){
        return Colors  + background + text + RESET ;
    }


    public static String casualColorYellow(String text){
        return getString(text, YELLOW_BRIGHT, YELLOW, YELLOW_BOLD);

    }

    private static String getString(String text, String bright, String normal, String bold) {
        Random random = new Random();
        List<String> casual = new ArrayList<>();
        casual.add(bright);
        casual.add(normal);
        casual.add(bold);

        return  casual.get(random.nextInt(casual.size())) + text + RESET;
    }

    public static String casualColorRed(String text){
        return getString(text, RED_BRIGHT, RED, RED_BOLD);

    }

    public static String colorResourceType(ResourceType resourceType){
        Map<ResourceType, String> colors = new EnumMap<>(ResourceType.class);
        colors.put(ResourceType.COIN, YELLOW_BRIGHT);
        colors.put(ResourceType.FAITHPOINT, RED_BRIGHT);
        colors.put(ResourceType.SERVANT, PURPLE_BRIGHT);
        colors.put(ResourceType.STONE, WHITE_BRIGHT);
        colors.put(ResourceType.SHIELD, BLUE_BRIGHT);
        colors.put(ResourceType.EMPTY, WHITE);
        colors.put(ResourceType.UNKNOWN, WHITE);

        return colors.get(resourceType) + resourceType.name() + RESET;
    }

    public static String colorDevCard(ColorDevCard devCard){
        Map<ColorDevCard, String> colors = new EnumMap<>(ColorDevCard.class);
        colors.put(ColorDevCard.YELLOW, YELLOW);
        colors.put(ColorDevCard.BLUE, BLUE);
        colors.put(ColorDevCard.GREEN, GREEN);
        colors.put(ColorDevCard.PURPLE, PURPLE);
        return colors.get(devCard) + devCard.name() + RESET;
    }


    public static String colorMarbleType(MarbleColor marbleColor) {
        Map<MarbleColor, String> colors = new EnumMap<>(MarbleColor.class);
        colors.put(MarbleColor.BLUE, BLUE_BRIGHT);
        colors.put(MarbleColor.PURPLE, PURPLE_BRIGHT);
        colors.put(MarbleColor.RED, RED_BRIGHT);
        colors.put(MarbleColor.WHITE, WHITE_BRIGHT);
        colors.put(MarbleColor.YELLOW, YELLOW_BRIGHT);
        colors.put(MarbleColor.GRAY, WHITE_BOLD);

        return colors.get(marbleColor) + marbleColor.name() + RESET;
    }

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
}