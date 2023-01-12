package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.Redirector;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.component.*;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@ParsableClass(name = "component")
public class ComponentParsable implements Redirector<LowLevelComponent> {
    
    ComponentType component;

    @Override
    public Class<? extends LowLevelComponent> redirect() {
        if(component.isSelectMenuType()) return SelectMenu.class;

        return switch (component) {
            case BUTTON -> Button.class;
            case TEXT_INPUT -> TextInput.class;
            default -> throw new InternalExpressionException("Unsupported value for 'component': " + component.name());
        };
    }
}
