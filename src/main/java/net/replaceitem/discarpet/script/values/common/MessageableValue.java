package net.replaceitem.discarpet.script.values.common;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.Optional;

public abstract class MessageableValue<T> extends DiscordValue<T> {
    public MessageableValue(T value) {
        super(value);
    }
    
    public Optional<MessageConsumer> getMessageConsumer() {
        return Optional.empty();
    }
    
    public interface MessageConsumer {
        RestAction<Message> send(MessageCreateData data);
    }
}
