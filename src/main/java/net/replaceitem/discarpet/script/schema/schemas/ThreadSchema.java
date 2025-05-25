package net.replaceitem.discarpet.script.schema.schemas;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import net.dv8tion.jda.internal.entities.channel.mixin.attribute.IThreadContainerMixin;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "thread")
public class ThreadSchema {
    
    String name;
    @OptionalField
    Boolean is_private = false;
    @OptionalField @Nullable
    Boolean invitable;
    @OptionalField @Nullable
    Integer auto_archive_duration;
    @OptionalField @Nullable
    Integer slowmode;
    @OptionalField @Nullable
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
        if(slowmode != null) action.setSlowmode(slowmode);
        action.reason(reason);
        return action;
    }
}
