package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.parsable.Applicable;
import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.util.ScarpetGraphicsDependency;
import net.replaceitem.discarpet.script.values.UserValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.image.BufferedImage;
import java.io.File;

@ParsableClass(name = "embed_author")
public class EmbedAuthorParsable implements Applicable<EmbedBuilder>, DirectParsable {
    
    String name;
    @Optional String url;
    @Optional Value icon;
    
    @Override
    public void apply(EmbedBuilder embedBuilder) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(icon)) {
            BufferedImage image = ScarpetGraphicsDependency.getImageFromValue(icon);
            embedBuilder.setAuthor(name,url,image);
            return;
        }
        String iconString = icon == null? null : icon.getString();
        if(iconString != null) {
            File file = new File(iconString);
            if(file.exists()) {
                embedBuilder.setAuthor(name, url, file);
                return;
            }
        }
        embedBuilder.setAuthor(name, url, iconString);
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof UserValue userValue) {
            User user = userValue.getDelegate();
            this.name = user.getName();
            this.icon = StringValue.of(user.getAvatar().getUrl().toString());
            return true;
        }
        if(!(value instanceof MapValue)) {
            this.name = value.getString();
            return true;
        }
        return false;
    }
}
