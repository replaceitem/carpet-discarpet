package Discarpet.script.parsable.parsables;

import Discarpet.Discarpet;
import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.util.ScarpetGraphicsDependency;
import carpet.script.value.Value;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.image.BufferedImage;
import java.io.File;

@ParsableClass(name = "embed_footer")
public class EmbedFooterParsable implements Applicable<EmbedBuilder> {

    String text;
    @Optional Value icon;
    
    @Override
    public void apply(EmbedBuilder embedBuilder) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(icon)) {
            BufferedImage image = ScarpetGraphicsDependency.getImageFromValue(icon);
            embedBuilder.setFooter(text,image);
            return;
        }
        String iconString = icon == null? null : icon.getString();
        if(iconString != null) {
            File file = new File(iconString);
            if (file.exists()) {
                embedBuilder.setFooter(text, file);
                return;
            }
        }
        embedBuilder.setFooter(text, iconString);
    }
}
