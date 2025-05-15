package net.replaceitem.discarpet.script.parsable.parsables.components;

import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.Redirector;


@ParsableClass(name = "component")
public class ComponentParsable implements Redirector<ItemComponent> {
    Component.Type component;

    @Override
    public Class<? extends ItemComponent> redirect() {
        return switch (component) {
            case BUTTON -> Button.class;
            case TEXT_INPUT -> TextInput.class;
            case STRING_SELECT, USER_SELECT, ROLE_SELECT, CHANNEL_SELECT, MENTIONABLE_SELECT -> SelectMenu.class;
            default -> throw new InternalExpressionException("Unsupported value for 'component': " + component.name());
        };
    }
}
