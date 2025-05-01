package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

import java.util.EnumSet;
import java.util.List;

@ParsableClass(name = "select_menu")
public class SelectMenuParsable implements ParsableConstructor<SelectMenu> {
    String id;
    Component.Type component;
    @OptionalField
    Boolean disabled = false;
    @OptionalField
    List<SelectOption> options = List.of();
    @OptionalField
    Integer min = 1;
    @OptionalField
    Integer max = 1;
    @OptionalField
    String placeholder;

    // TODO some things may be added here, like channelType for entity selects
    
    @Override
    public SelectMenu construct() {
        SelectMenu.Builder<?,?> builder = switch (component) {
            case STRING_SELECT -> StringSelectMenu.create(id).addOptions(options);
            case USER_SELECT -> EntitySelectMenu.create(id, EntitySelectMenu.SelectTarget.USER);
            case CHANNEL_SELECT -> EntitySelectMenu.create(id, EntitySelectMenu.SelectTarget.CHANNEL).setDefaultValues();
            case ROLE_SELECT -> EntitySelectMenu.create(id, EntitySelectMenu.SelectTarget.ROLE);
            case MENTIONABLE_SELECT -> EntitySelectMenu.create(id, EnumSet.of(EntitySelectMenu.SelectTarget.USER, EntitySelectMenu.SelectTarget.ROLE));
            default -> throw new IllegalArgumentException("ComponentParsable redirected to SelectMenu, but type is invalid for select menu. Report this to discarpet.");
        };
        
        builder.setDisabled(disabled).setRequiredRange(min, max);
        if(placeholder != null) builder.setPlaceholder(placeholder);
        return builder.build();
    }
}
