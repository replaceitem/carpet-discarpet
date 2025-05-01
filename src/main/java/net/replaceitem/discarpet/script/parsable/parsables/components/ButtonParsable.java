package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

@ParsableClass(name = "button")
public class ButtonParsable implements ParsableConstructor<Button> {
    @OptionalField
    String id;
    String label;
    @OptionalField
    Boolean disabled = false;
    @OptionalField
    ButtonStyle style = ButtonStyle.SECONDARY;
    @OptionalField
    Emoji emoji;
    @OptionalField
    String url;
    
    @Override
    public Button construct() {
        return Button.of(
                style,
                style == ButtonStyle.LINK ? url : id,
                label,
                emoji
        ).withDisabled(disabled);
    }
}
