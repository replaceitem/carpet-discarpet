package Discarpet.script.functions;

import Discarpet.ScarpetMapValueUtil;
import Discarpet.script.ScriptFuture;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmbedBuilderValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.UserValue;
import carpet.script.Expression;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.argument.FunctionArgument;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static Discarpet.Discarpet.scarpetException;
import static Discarpet.ScarpetMapValueUtil.*;

public class Sending {
	@ScarpetFunction
	public boolean dc_react(Message message, Value emojiValue) {
		if (!(emojiValue instanceof EmojiValue || emojiValue instanceof StringValue)) scarpetException("dc_react","emoji",1);
		if (!message.canYouAddNewReactions()) return false;
		
		if(emojiValue instanceof EmojiValue) {
            message.addReaction(((EmojiValue) emojiValue).emoji);
        } else {
            message.addReaction(emojiValue.getString());
        }
		
		return true;
	}
	
    public static void apply(Expression expr) {
        expr.addContextFunction("dc_send_message", -1, (c, t, lv) -> {
            if(!(lv.size()==2 || lv.size()==3)) throw new InternalExpressionException("'dc_send_message' requires two or tree arguments");

            Value channelValue = lv.get(0);
            Value messageValue = lv.get(1);

            MessageBuilder messageBuilder = messageBuilderFromValue(messageValue);

            CompletableFuture<Message> cf;

            if(channelValue instanceof ChannelValue) {
                cf = messageBuilder.send(((ChannelValue) channelValue).getChannel());
            } else if(channelValue instanceof UserValue) {
                cf = messageBuilder.send(((UserValue) channelValue).getUser());
            } else throw new InternalExpressionException("'dc_send_message' expected a channel or user as the first parameter");

            if(lv.size()==3) {
                FunctionArgument functionArgument = FunctionArgument.findIn(c, expr.module, lv, 2, false, false);
                ScriptFuture future = new ScriptFuture(c, functionArgument.function);
                cf.thenAccept(message -> {
                    future.execute(new MessageValue(message));
                });
            }

            return Value.TRUE;
        });
    }


    public static MessageBuilder messageBuilderFromValue(Value value) {
	    MessageBuilder messageBuilder = new MessageBuilder();

	    if(value instanceof EmbedBuilderValue) {
	        return messageBuilder.setEmbed(((EmbedBuilderValue) value).embedBuilder);
        } else if(value instanceof MapValue) {
            Map<Value, Value> map = ((MapValue) value).getMap();

            if(mapHasKey(map,"content")) {
                messageBuilder.setContent(getStringInMap(map,"content"));
            }

            if(mapHasKey(map,"attachments")) {
                List<Value> attachments = getListInMap(map,"attachments");
                for (Value v : attachments) {
                    addAttachmentFromValue(messageBuilder, v);
                }
            }

            if(mapHasKey(map,"embeds")) {
                List<Value> embeds = getListInMap(map,"embeds");
                for (Value v : embeds) {
                    if(v instanceof EmbedBuilderValue) {
                        messageBuilder.addEmbed(((EmbedBuilderValue) v).embedBuilder);
                    } else throw new InternalExpressionException("'embeds' list expected only embed builder values");
                }
            }

            if(mapHasKey(map,"components")) {
                List<Value> components = getListInMap(map,"components");
                for (Value v : components) {
                    messageBuilder.addComponents(getActionRowFromValue(v));
                }
            }

            return messageBuilder;
        }
	    return messageBuilder.setContent(value.getString());

    }

    public static void addAttachmentFromValue(MessageBuilder builder, Value value) {
        if(!(value instanceof MapValue)) throw new InternalExpressionException("Expected a map as entry in 'attachments'");
        Map<Value, Value> map = ((MapValue) value).getMap();

        boolean spoiler = mapHasKey(map, "spoiler") && getBooleanInMap(map, "spoiler");

        if(mapHasKey(map,"file")) {
            String path = getStringInMap(map,"file");
            File file = new File(path);
            if(!file.exists()) throw new InternalExpressionException("Invalid path for attachment file '" + path + "'");
            if(spoiler) builder.addAttachmentAsSpoiler(file);
            else builder.addAttachment(file);
            return;
        }


        if(mapHasKey(map,"url")) {
            String path = getStringInMap(map,"url");
            URL url;
            try {
                url = new URL(path);
            } catch (MalformedURLException e) {
                throw new InternalExpressionException("Invalid URL for attachment file '" + path + "': " + e.toString());
            }
            if(spoiler) builder.addAttachmentAsSpoiler(url);
            else builder.addAttachment(url);
            return;
        }

        if(mapHasKey(map,"bytes")) {
            String name = getStringInMap(map,"name");
            if(name == null) throw new InternalExpressionException("Missing 'name' for 'bytes' attachment type");
            byte[] data = getStringInMap(map,"bytes").getBytes();
            if(spoiler) builder.addAttachmentAsSpoiler(data,name);
            else builder.addAttachment(data,name);
        }
    }

    public static HighLevelComponent getActionRowFromValue(Value value) {
	    if(!(value instanceof ListValue)) throw new InternalExpressionException("Expected a list for the elements in 'components' list");

        List<LowLevelComponent> lowLevelComponents = new ArrayList<>();

        for(Value v : ((ListValue) value).getItems()) {
            lowLevelComponents.add(getLowLevelComponentFromValue(v));
        }


        return ActionRow.of(lowLevelComponents);
    }

    public static LowLevelComponent getLowLevelComponentFromValue(Value value) {
        if(!(value instanceof MapValue)) throw new InternalExpressionException("Expected a map as entry in 'components' list");
        Map<Value, Value> map = ((MapValue) value).getMap();


        String componentType = getStringInMap(map,"component");

        if(componentType.equalsIgnoreCase("BUTTON")) {
            return getButton(map);
        } else if(componentType.equalsIgnoreCase("SELECT_MENU")) {
            return getSelectMenu(map);
        } else throw new InternalExpressionException("Invalid message component type " + componentType);
    }

    public static Button getButton(Map<Value, Value> map) {

        ButtonBuilder buttonBuilder = new ButtonBuilder();

        if(mapHasKey(map,"id")) {
            buttonBuilder.setCustomId(getStringInMap(map,"id"));
        }

        if(mapHasKey(map,"style")) {
            String styleName = getStringInMap(map,"style");
            ButtonStyle style = ButtonStyle.fromName(styleName.toLowerCase());
            if (style.equals(ButtonStyle.UNKNOWN)) throw new InternalExpressionException("Unknown button style: " + styleName);
            buttonBuilder.setStyle(style);
        }

        if(mapHasKey(map,"label")) {
            buttonBuilder.setLabel(getStringInMap(map,"label"));
        }

        if(mapHasKey(map,"emoji")) {
            Value emojiValue = getValueInMap(map,"emoji");
            if(emojiValue instanceof EmojiValue) {
                buttonBuilder.setEmoji(((EmojiValue) emojiValue).emoji);
            } else {
                buttonBuilder.setEmoji(emojiValue.getString());
            }
        }

        if(mapHasKey(map,"url")) {
            buttonBuilder.setUrl(getStringInMap(map,"url"));
        }

        if(mapHasKey(map,"disabled")) {
            buttonBuilder.setDisabled(getBooleanInMap(map,"disabled"));
        }

        return buttonBuilder.build();
    }

    public static SelectMenu getSelectMenu(Map<Value, Value> map) {

        SelectMenuBuilder selectMenuBuilder = new SelectMenuBuilder();


        selectMenuBuilder.setCustomId(getStringInMap(map,"id"));

        for(Value v : getListInMap(map,"options")) {
            selectMenuBuilder.addOption(getSelectMenuOptionFromValue(v));
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

        if(mapHasKey(map,"disabled")) {
            selectMenuBuilder.setDisabled(getBooleanInMap(map,"disabled"));
        }

        return selectMenuBuilder.build();
    }

    public static SelectMenuOption getSelectMenuOptionFromValue(Value value) {
	    if(!(value instanceof MapValue)) throw new InternalExpressionException("Options in select menu need to be a map");
	    Map<Value,Value> map = ((MapValue) value).getMap();

        SelectMenuOptionBuilder selectMenuOptionBuilder = new SelectMenuOptionBuilder();

        selectMenuOptionBuilder.setValue(getStringInMap(map,"value"));

        selectMenuOptionBuilder.setLabel(getStringInMap(map,"label"));

        if(mapHasKey(map,"description")) {
            selectMenuOptionBuilder.setDescription(getStringInMap(map,"description"));
        }

        if(mapHasKey(map,"emoji")) {
            Value emojiValue = getValueInMap(map,"emoji");
            Emoji emoji;
            if(emojiValue instanceof EmojiValue) {
                emoji = ((EmojiValue) emojiValue).emoji;
            } else {
                emoji = UnicodeEmojiImpl.fromString(emojiValue.getString());
            }
            selectMenuOptionBuilder.setEmoji(emoji);
        }

        if(mapHasKey(map,"default")) {
            selectMenuOptionBuilder.setDefault(getBooleanInMap(map,"default"));
        }

        return  selectMenuOptionBuilder.build();
    }
}
