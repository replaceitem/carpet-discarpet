package net.replaceitem.discarpet.script.parsable.parsables;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import net.dv8tion.jda.internal.entities.channel.mixin.attribute.IThreadContainerMixin;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;

@ParsableClass(name = "thread")
public class ThreadParsable {
    
    String name;
    @OptionalField
    Boolean is_private = false;
    @OptionalField
    Boolean invitable;
    @OptionalField
    Integer auto_archive_duration;
    @OptionalField
    String reason;

    public ThreadChannelAction apply(IThreadContainerMixin<?> threadContainer) {
        return apply(threadContainer.createThreadChannel(name, is_private));
    }

    public ThreadChannelAction apply(Message message) {
        return apply(message.createThreadChannel(name));
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private ThreadChannelAction apply(ThreadChannelAction action) {
        if(invitable != null) action.setInvitable(invitable);
        if(auto_archive_duration != null) action.setAutoArchiveDuration(ThreadChannel.AutoArchiveDuration.fromKey(auto_archive_duration));
        action.reason(reason);
        return action;
    }
}
