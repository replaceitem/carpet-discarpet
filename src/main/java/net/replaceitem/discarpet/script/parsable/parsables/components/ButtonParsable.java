package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.util.ValueUtil;
import carpet.script.value.Value;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonBuilder;

@ParsableClass(name = "button")
public class ButtonParsable implements ParsableConstructor<Button> {
    @Optional String id;
    String label;
    @Optional Boolean disabled = false;
    @Optional String style = "grey";
    @Optional Value emoji;
    @Optional String url;
    
    @Override
    public Button construct() {
        ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setCustomId(id);
        buttonBuilder.setDisabled(disabled);
        buttonBuilder.setLabel(label);
        buttonBuilder.setStyle(style);
        buttonBuilder.setEmoji(ValueUtil.emojiFromValue(emoji));
        buttonBuilder.setUrl(url);
        return buttonBuilder.build();
    }
}
