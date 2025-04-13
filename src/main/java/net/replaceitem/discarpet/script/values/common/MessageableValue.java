package net.replaceitem.discarpet.script.values.common;

import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.Optional;

public abstract class MessageableValue<T> extends DiscordValue<T> {
    public MessageableValue(T value) {
        super(value);
    }
    
    public Optional<MessageConsumer> getMessageable() {
        return Optional.empty();
    }
    
    public interface MessageConsumer {
        RestAction<?> send(MessageCreateData data);
    }
}
