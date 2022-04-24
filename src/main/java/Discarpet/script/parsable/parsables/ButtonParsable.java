package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import Discarpet.script.util.ValueUtil;
import carpet.script.value.Value;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonBuilder;

@ParsableClass(name = "button")
public class ButtonParsable implements ParsableConstructor<Button> {
    String id;
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
