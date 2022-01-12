package Discarpet.script.util;

import Discarpet.script.values.EmojiValue;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.component.ActionRowBuilder;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.component.SelectMenuOptionBuilder;
import org.javacord.core.entity.emoji.UnicodeEmojiImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static Discarpet.script.util.MapValueUtil.*;

public class MessageComponentParser {
    public static HighLevelComponent parseMessageComponent(Value value) {
        if(!(value instanceof ListValue componentList)) throw new InternalExpressionException("Expected a list for the elements in 'components' list");

        List<Value> components = componentList.getItems();

        ActionRowBuilder a = new ActionRowBuilder();
        for (int i = 0; i < components.size(); i++) {
            Value component = components.get(i);
            try {
                addComponent(a, component);
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse component with index " + i + " inside list: " + ex.getMessage());
            }
        }

        return a.build();
    }

    private static void addComponent(ActionRowBuilder a, Value component) {
        if(!(component instanceof MapValue componentMap)) throw new InternalExpressionException("Expected a map as entry in 'components' list");
        Map<Value, Value> map = componentMap.getMap();
        String componentType = getStringInMap(map,"component");

        LowLevelComponent lowLevelComponent = switch (componentType.toLowerCase(Locale.ROOT)) {
            case "button" -> getButton(map);
            case "select_menu" -> getSelectMenu(map);
            default -> null;
        };
        a.addComponents(lowLevelComponent);
    }

    private static Button getButton(Map<Value,Value> map) {
        ButtonBuilder buttonBuilder = new ButtonBuilder();


        String styleName = getStringInMap(map,"style");
        ButtonStyle style = ButtonStyle.fromName(styleName.toLowerCase());
        if (style.equals(ButtonStyle.UNKNOWN)) throw new InternalExpressionException("Unknown button style: " + styleName);
        buttonBuilder.setStyle(style);

        if(!style.equals(ButtonStyle.LINK)) buttonBuilder.setCustomId(getStringInMap(map,"id"));

        if(mapHasKey(map,"label")) {
            buttonBuilder.setLabel(getStringInMap(map,"label"));
        }

        if(mapHasKey(map,"emoji")) {
            Value emoji = getValueInMap(map,"emoji");
            if(emoji instanceof EmojiValue emojiValue) {
                buttonBuilder.setEmoji(emojiValue.emoji);
            } else {
                buttonBuilder.setEmoji(emoji.getString());
            }
        }

        if(style.equals(ButtonStyle.LINK) && mapHasKey(map,"url")) {
            buttonBuilder.setUrl(getStringInMap(map,"url"));
        }

        buttonBuilder.setDisabled(getBooleanInMapOrDefault(map,"disabled",false));

        return buttonBuilder.build();
    }

    private static SelectMenu getSelectMenu(Map<Value,Value> map) {
        SelectMenuBuilder selectMenuBuilder = new SelectMenuBuilder();

        selectMenuBuilder.setCustomId(getStringInMap(map,"id"));

        List<Value> options = getListInMap(map, "options");

        for (int i = 0; i < options.size(); i++) {
            Value selectMenuOption = options.get(i);
            try {
                selectMenuBuilder.addOption(getSelectMenuOptionFromValue(selectMenuOption));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse select menu option with index " + i + " inside 'options' list: " + ex.getMessage());
            }
        }

        if(mapHasKey(map,"min")) {
            selectMenuBuilder.setMinimumValues(getIntInMap(map,"min"));
        }
        if(mapHasKey(map,"max")) {
            selectMenuBuilder.setMaximumValues(getIntInMap(map,"max"));
        }

        if(mapHasKey(map,"placeholder")) {
            selectMenuBuilder.setPlaceholder(getStringInMap(map,"placeholder"));
        }

        selectMenuBuilder.setDisabled(getBooleanInMapOrDefault(map,"disabled",false));

        return selectMenuBuilder.build();
    }

    private static SelectMenuOption getSelectMenuOptionFromValue(Value value) {
        SelectMenuOptionBuilder s = new SelectMenuOptionBuilder();

        if(!(value instanceof MapValue)) throw new InternalExpressionException("Options in select menu need to be a map");
        Map<Value,Value> map = ((MapValue) value).getMap();

        s.setValue(getStringInMap(map,"value"));

        s.setLabel(getStringInMap(map,"label"));

        if(mapHasKey(map,"description")) {
            s.setDescription(getStringInMap(map,"description"));
        }

        if(mapHasKey(map,"emoji")) {
            Value emojiValue = getValueInMap(map,"emoji");
            if(emojiValue instanceof EmojiValue emoji) {
                s.setEmoji(emoji.getEmoji());
            } else {
                s.setEmoji(UnicodeEmojiImpl.fromString(emojiValue.getString()));
            }

        }

        if(mapHasKey(map,"default")) {
            s.setDefault(getBooleanInMap(map,"default"));
        }

        return s.build();
    }
}
