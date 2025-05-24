package net.replaceitem.discarpet.script.parsable.parsables.components;

import carpet.script.Context;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@ParsableClass(name = "button")
public class ButtonParsable implements ParsableConstructor<Button> {
    @OptionalField @Nullable
    String id;
    String label;
    @OptionalField
    Boolean disabled = false;
    @OptionalField
    ButtonStyle style = ButtonStyle.SECONDARY;
    @OptionalField @Nullable
    Emoji emoji;
    @OptionalField @Nullable
    String url;
    
    @Override
    public Button construct(Context context) {
        return Button.of(
                style,
                style == ButtonStyle.LINK ?
                        Objects.requireNonNull(url, "Link style requires an url") :
                        Objects.requireNonNull(id, "Non-link style requires an id"),
                label,
                emoji
        ).withDisabled(disabled);
    }
}
