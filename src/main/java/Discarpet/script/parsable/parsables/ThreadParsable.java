package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerThreadChannelBuilder;
import org.javacord.api.entity.message.Message;

@ParsableClass(name = "thread")
public class ThreadParsable implements ParsableConstructor<ServerThreadChannelBuilder> {
    
    String name;
    @Optional Message message;
    @Optional Channel channel;
    @Optional ChannelType channel_type;
    @Optional Boolean invitable;
    @Optional Integer auto_archive_duration;
    @Optional Integer slow_mode_delay;
    @Optional String reason;


    @Override
    public ServerThreadChannelBuilder construct() {
        ServerThreadChannelBuilder serverThreadChannelBuilder;
        if(message != null) {
            serverThreadChannelBuilder = new ServerThreadChannelBuilder(message, name);
        } else {
            if(channel == null) throw new InternalExpressionException("Either 'message' or 'channel' needs to be given");
            if(!(channel instanceof ServerTextChannel serverTextChannel)) throw new InternalExpressionException("'channel' needs to be a server text channel, but is a " + channel.getType().toString());
            if(channel_type == null) throw new InternalExpressionException("'channel_type' needs to be provided when creating a thread from a channel");
            serverThreadChannelBuilder = new ServerThreadChannelBuilder(serverTextChannel, channel_type, name);
        }
        
        serverThreadChannelBuilder.setInvitableFlag(invitable);
        serverThreadChannelBuilder.setAutoArchiveDuration(auto_archive_duration);
        if(slow_mode_delay != null) serverThreadChannelBuilder.setSlowmodeDelayInSeconds(slow_mode_delay);
        serverThreadChannelBuilder.setAuditLogReason(reason);
        return serverThreadChannelBuilder;
    }
}
