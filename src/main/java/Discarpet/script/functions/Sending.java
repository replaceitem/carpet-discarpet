package Discarpet.script.functions;

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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static Discarpet.Discarpet.scarpetException;

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

	    if(value instanceof StringValue) {
	        return messageBuilder.setContent(value.getString());
        }

	    if(value instanceof EmbedBuilderValue) {
	        return messageBuilder.setEmbed(((EmbedBuilderValue) value).embedBuilder);
        }

	    if(value instanceof MapValue) {
            Map<Value, Value> map = ((MapValue) value).getMap();

            Value contentValue = map.get(new StringValue("content"));
            messageBuilder.setContent(contentValue.getString());


            Value attachmentsValue = map.get(new StringValue("attachments"));
            if(attachmentsValue != null) {
                if (attachmentsValue instanceof ListValue) {
                    for (Value v : ((ListValue) attachmentsValue).getItems()) {
                        addAttachmentFromValue(messageBuilder, v);
                    }
                } else throw new InternalExpressionException("Expected a list as 'attachments' value");
            }

            Value embedsValue = map.get(new StringValue("embeds"));
            if(embedsValue != null) {
                if (embedsValue instanceof ListValue) {
                    for (Value v : ((ListValue) embedsValue).getItems()) {
                        if(v instanceof EmbedBuilderValue) {
                            messageBuilder.addEmbed(((EmbedBuilderValue) v).embedBuilder);
                        } else throw new InternalExpressionException("'embeds' list expected only embed builder values");
                    }
                } else throw new InternalExpressionException("Expected a list as 'embeds' value");
            }


            Value componentsValue = map.get(new StringValue("components"));
            if(componentsValue != null) {
                if (componentsValue instanceof ListValue) {
                    for (Value v : ((ListValue) componentsValue).getItems()) {
                        messageBuilder.addComponents(getActionRowFromValue(v));
                    }
                } else throw new InternalExpressionException("Expected a list as 'components' value");
            }
        }

	    return messageBuilder;
    }

    public static void addAttachmentFromValue(MessageBuilder builder, Value value) {
        if(!(value instanceof MapValue)) throw new InternalExpressionException("Expected a map as entry in 'attachments'");
        Map<Value, Value> map = ((MapValue) value).getMap();

        boolean spoiler = map.getOrDefault(new StringValue("spoiler"),Value.FALSE).getBoolean();

        Value fileValue = map.get(new StringValue("file"));
        if(fileValue != null) {
            File file = new File(fileValue.getString());
            if(!file.exists()) throw new InternalExpressionException("Invalid path for attachment file '" + fileValue.getString() + "'");
            if(spoiler) builder.addAttachmentAsSpoiler(file);
            else builder.addAttachment(file);
            return;
        }

        Value urlValue = map.get(new StringValue("url"));
        if(urlValue != null) {
            URL url;
            try {
                url = new URL(urlValue.getString());
            } catch (MalformedURLException e) {
                throw new InternalExpressionException("Invalid URL for attachment file '" + urlValue.getString() + "': " + e.toString());
            }
            if(spoiler) builder.addAttachmentAsSpoiler(url);
            else builder.addAttachment(url);
            return;
        }


        Value bytesValue = map.get(new StringValue("bytes"));
        if(bytesValue != null) {
            Value nameValue = map.get(new StringValue("name"));
            if(nameValue == null) throw new InternalExpressionException("Missing 'name' for 'bytes' attachment type");
            String name = nameValue.getString();
            byte[] data = bytesValue.getString().getBytes();
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

        Value componentValue = map.get(new StringValue("component"));
        if(componentValue == null) throw new InternalExpressionException("Missing 'component' value for message component");
        String componentType = componentValue.getString();

        if(componentType.equalsIgnoreCase("BUTTON")) {
            return getButton(map);
        } else if(componentType.equalsIgnoreCase("SELECT_MENU")) {
            return null; //TODO
        } else throw new InternalExpressionException("Invalid message component type " + componentType);
    }

    public static Button getButton(Map<Value, Value> map) {

        ButtonBuilder buttonBuilder = new ButtonBuilder();

        Value idValue = map.get(new StringValue("id"));
        if(idValue != null) buttonBuilder.setCustomId(idValue.getString());

        Value styleValue = map.get(new StringValue("style"));
        if(styleValue != null) {
            ButtonStyle style = ButtonStyle.fromName(styleValue.getString().toLowerCase());
            if (style.equals(ButtonStyle.UNKNOWN)) throw new InternalExpressionException("Unknown button style: " + styleValue.getString());
            buttonBuilder.setStyle(style);
        }

        Value labelValue = map.get(new StringValue("label"));
        if(labelValue != null) buttonBuilder.setLabel(labelValue.getString());


        Value emojiValue = map.get(new StringValue("emoji"));
        if(emojiValue != null) {
            if(emojiValue instanceof EmojiValue) {
                buttonBuilder.setEmoji(((EmojiValue) emojiValue).emoji);
            } else {
                buttonBuilder.setEmoji(emojiValue.getString());
            }
        }

        Value urlValue = map.get(new StringValue("url"));
        if(urlValue != null) {
            buttonBuilder.setUrl(urlValue.getString());
        }

        Value disabledValue = map.get(new StringValue("disabled"));
        if(disabledValue != null) {
            buttonBuilder.setDisabled(disabledValue.getBoolean());
        }

        return buttonBuilder.build();
    }
}
