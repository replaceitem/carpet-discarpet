package Discarpet.script.functions;

import Discarpet.script.util.ValueUtil;
import carpet.script.annotation.ScarpetFunction;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerTextChannel;

public class Channels {
	
	@ScarpetFunction
	public boolean dc_set_channel_topic(Channel channel, String str) {
		if(!(channel instanceof ServerTextChannel textChannel)) return false;
        return ValueUtil.awaitFutureBoolean(textChannel.updateTopic(str),"Error updating channel topic");
	}
}
