package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.Component.Type;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.label.Label;
import net.dv8tion.jda.api.components.section.Section;
import net.dv8tion.jda.api.components.selections.SelectMenu;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.components.textinput.TextInput;
import net.dv8tion.jda.api.components.thumbnail.Thumbnail;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.Redirector;
import net.replaceitem.discarpet.script.schema.SchemaClass;


@SchemaClass(name = "component")
public class ComponentSchema implements Redirector<Component>, DirectParsable {
    Type component;

    @Override
    public Class<? extends Component> redirect() {
        return switch (component) {
            case ACTION_ROW -> ActionRow.class;
            case BUTTON -> Button.class;
            case TEXT_INPUT -> TextInput.class;
            case STRING_SELECT, USER_SELECT, ROLE_SELECT, CHANNEL_SELECT, MENTIONABLE_SELECT -> SelectMenu.class;
            case LABEL -> Label.class;
            case SEPARATOR -> Separator.class;
            case CONTAINER -> Container.class;
            case TEXT_DISPLAY -> TextDisplay.class;
            case SECTION -> Section.class;
            case THUMBNAIL -> Thumbnail.class;
            default -> throw new InternalExpressionException("Unsupported value for 'component': " + component.name());
        };
    }


    @Override
    public boolean tryParseDirectly(Value value, Context context) {
        if(value instanceof ListValue) {
            this.component = Type.ACTION_ROW;
            return true;
        }
        return false;
    }
}
