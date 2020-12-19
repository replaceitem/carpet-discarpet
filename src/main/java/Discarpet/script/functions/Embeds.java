package Discarpet.script.functions;

import Discarpet.script.values.EmbedBuilderValue;
import Discarpet.script.values.UserValue;
import carpet.script.Expression;
import carpet.script.LazyValue;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static Discarpet.Discarpet.scarpetException;
import static carpet.script.LazyValue.TRUE;
import static carpet.script.LazyValue.FALSE;
import static carpet.script.LazyValue.NULL;

public class Embeds {
    public static void apply(Expression expr) {

        expr.addLazyFunction("dc_build_embed", -1, (c, t, lv) -> {
            if(lv.size()==0) return (cc, tt) -> new EmbedBuilderValue();
            if(lv.size()==1) return LazyValue.FALSE;
            Value builderValue = lv.get(0).evalValue(c);
            if(!(builderValue instanceof EmbedBuilderValue)) scarpetException("dc_build_embed","EmbedBuilder",0);
            String property = lv.get(1).evalValue(c).getString();
            EmbedBuilder builder = ((EmbedBuilderValue) builderValue).embedBuilder;

            if(lv.size()==2) throw new InternalExpressionException("'dc_build_embed' at least one argument for modifying " + property);

            List<Value> v = lv.subList(2,lv.size()).stream().map(lazyValue -> lazyValue.evalValue(c)).collect(Collectors.toList());

            switch (property) {
                case "title":
                    builder.setTitle(v.get(0).getString());
                    return TRUE;
                case "description":
                    builder.setDescription(v.get(0).getString());
                    return TRUE;
                case "author":
                    if(v.get(0) instanceof UserValue) {
                        builder.setAuthor(((UserValue) v.get(0)).user);
                        return TRUE;
                    }
                    if(v.size()==1) {
                        builder.setAuthor(v.get(0).getString());
                        return TRUE;
                    }
                    if(v.size()>2) {
                        builder.setAuthor(v.get(0).getString(),v.get(1).getString(),v.get(2).getString());
                        return TRUE;
                    }
                    return FALSE;
                case "add_field":
                    if(v.size()<2) return FALSE;
                    builder.addField(v.get(0).getString(),v.get(1).getString());
                    return TRUE;
                case "add_inline_field":
                    if(v.size()<2) return FALSE;
                    builder.addInlineField(v.get(0).getString(),v.get(1).getString());
                    return TRUE;
                case "color":
                    if(v.size()>2) {
                        Color color = new Color((int)v.get(0).readInteger(),(int)v.get(1).readInteger(),(int)v.get(2).readInteger());
                        builder.setColor(color);
                        return TRUE;
                    }
                    if(v.get(0) instanceof ListValue && v.get(0).length() == 3) {
                        List<Value> col = ((ListValue) v.get(0)).getItems();
                        Color color = new Color((int)col.get(0).readInteger(),(int)col.get(1).readInteger(),(int)col.get(2).readInteger());
                        builder.setColor(color);
                        return TRUE;
                    }
                    return FALSE;
                case "footer":
                    if(v.size()>1) {
                        builder.setFooter(v.get(0).getString(),v.get(1).getString());
                        return TRUE;
                    }
                    builder.setFooter(v.get(0).getString());
                    return TRUE;
                case "image":
                    builder.setImage(v.get(0).getString());
                    return TRUE;
                case "thumbnail":
                    builder.setThumbnail(v.get(0).getString());
                    return TRUE;
                default:
                    return NULL;
            }
        });
    }
}
