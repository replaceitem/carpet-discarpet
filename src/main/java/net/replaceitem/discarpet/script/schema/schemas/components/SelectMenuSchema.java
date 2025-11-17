package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.Component.Type;
import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.components.selections.EntitySelectMenu.Builder;
import net.dv8tion.jda.api.components.selections.EntitySelectMenu.SelectTarget;
import net.dv8tion.jda.api.components.selections.SelectMenu;
import net.dv8tion.jda.api.components.selections.SelectOption;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

@SchemaClass(name = "select_menu")
public class SelectMenuSchema implements SchemaConstructor<SelectMenu> {
    String id;
    Type component;
    @OptionalField
    Boolean disabled = false;
    @OptionalField
    List<SelectOption> options = List.of();
    @OptionalField @Nullable
    List<ChannelType> channel_types;
    @OptionalField
    Integer min = 1;
    @OptionalField
    Integer max = 1;
    @OptionalField @Nullable
    String placeholder;
    
    @Override
    public SelectMenu construct(Context context) {
        SelectMenu.Builder<?,?> builder = switch (component) {
            case STRING_SELECT -> StringSelectMenu.create(id).addOptions(options);
            case USER_SELECT -> EntitySelectMenu.create(id, SelectTarget.USER);
            case CHANNEL_SELECT -> {
                Builder channelBuilder = EntitySelectMenu.create(id, SelectTarget.CHANNEL);
                if(channel_types != null) channelBuilder.setChannelTypes(channel_types);
                yield channelBuilder;
            }
            case ROLE_SELECT -> EntitySelectMenu.create(id, SelectTarget.ROLE);
            case MENTIONABLE_SELECT -> EntitySelectMenu.create(id, EnumSet.of(SelectTarget.USER, SelectTarget.ROLE));
            default -> throw new IllegalArgumentException("ComponentSchema redirected to SelectMenuSchema, but type is invalid for select menu. Report this to discarpet.");
        };
        builder.setDisabled(disabled).setRequiredRange(min, max);
        builder.setPlaceholder(placeholder);
        return builder.build();
    }
}
