package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.EmojiValue;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.entity.message.component.SelectMenuOption;

import java.util.List;

@ParsableClass(name = "component")
public class ComponentParsable implements ParsableConstructor<LowLevelComponent> {
    
    String component;
    String id;
    @Optional Boolean disabled = false;

    // button only
    @Optional String style = "grey";
    @Optional String label;
    @Optional Value emoji;
    @Optional String url;
    
    // select menu only
    @Optional List<SelectMenuOption> options;
    @Optional Integer min = 1;
    @Optional Integer max = 1;
    @Optional String placeholder;
    
    
    @Override
    public LowLevelComponent construct() {
        if(component.equalsIgnoreCase("button")) {
            return constructButton();
        } else if(component.equalsIgnoreCase("select_menu")) {
            return constructSelectMenu();
        } else throw new InternalExpressionException("'component' needs to be either 'button' or 'select_menu'");
    }
    
    private Button constructButton() {
        ButtonBuilder buttonBuilder = new ButtonBuilder();
        
        buttonBuilder.setCustomId(id);
        buttonBuilder.setDisabled(disabled);
        
        buttonBuilder.setStyle(style);
        buttonBuilder.setLabel(label);
        buttonBuilder.setEmoji(ValueUtil.emojiFromValue(emoji));
        buttonBuilder.setUrl(url);
        return buttonBuilder.build();
    }
    
    private SelectMenu constructSelectMenu() {
        SelectMenuBuilder selectMenuBuilder = new SelectMenuBuilder();
        
        selectMenuBuilder.setCustomId(id);
        selectMenuBuilder.setDisabled(disabled);
        
        selectMenuBuilder.addOptions(options);
        selectMenuBuilder.setMinimumValues(min);
        selectMenuBuilder.setMaximumValues(max);
        selectMenuBuilder.setPlaceholder(placeholder);
        return selectMenuBuilder.build();
    }
}
