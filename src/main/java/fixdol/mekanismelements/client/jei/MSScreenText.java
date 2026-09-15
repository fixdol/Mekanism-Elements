package fixdol.mekanismelements.client.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public final class MSScreenText {

    private MSScreenText() {
    }

    public static List<Component> wrap(Component text, int maxWidth) {
        List<Component> lines = new ArrayList<>();
        for (FormattedText line : Minecraft.getInstance().font.getSplitter().splitLines(text, maxWidth, Style.EMPTY)) {
            lines.add(Component.literal(line.getString()));
        }
        return lines;
    }
}
