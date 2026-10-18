package fixdol.mekanismelements.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

/**
 * Text helpers shared by the JEI, EMI and REI categories.
 */
public final class MSRecipeViewerText {
    /** Pixels available for text inside the 122 wide inner screen of the machines that have no real recipe. */
    private static final int INNER_SCREEN_TEXT_WIDTH = 112;

    private MSRecipeViewerText() {
    }

    /**
     * The inner screen draws every component on its own line and never wraps, so a long description would be cut at
     * both sides. Wrap it into one component per line first.
     */
    public static List<Component> wrapForInnerScreen(Component text) {
        return Minecraft.getInstance().font.getSplitter()
                .splitLines(text, INNER_SCREEN_TEXT_WIDTH, Style.EMPTY).stream()
                .<Component>map(line -> Component.literal(line.getString()))
                .toList();
    }
}
