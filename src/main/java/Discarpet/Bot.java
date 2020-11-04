package Discarpet;

import carpet.script.exception.InternalExpressionException;
import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

public class Bot {
	private DiscordApi api = null;

	public Bot(String token) {
		if(token != "empty") {
			try {
				api = new DiscordApiBuilder().setToken(token).login().join();
				api.addMessageCreateListener(event -> {
					String content = event.getMessage().getContent();
					String author = event.getMessageAuthor().getDisplayName();
					String channel = event.getMessage().getChannel().getIdAsString();
					//fix emojis
					content = EmojiParser.parseToAliases(content);
					//fix pings
					for (User user : this.api.getCachedUsers()) {
						ServerChannel serverChannel = (ServerChannel) this.api.getServerChannels().toArray()[0];
						Server discordServer = serverChannel.getServer();
						String string_discriminator = "";
						if(true){
							string_discriminator = "#" + user.getDiscriminator();
						}
						content = content.replace("<@!" + user.getIdAsString() + ">", "@" + user.getDisplayName(discordServer) + string_discriminator);
						//if (user.getNickname(discordServer).isPresent()) {
							//string_message = string_message.replace("@" + user.getName(), "@" + user.getDisplayName(discordServer) + "(" + user.getName() + string_discriminator + ")");
						//}
					}
					/*
					Style style = new Style();
					if (event.getMessageAttachments().isEmpty()) {
						content = content + " (Click to open attachment URL)";
						style.setColor(Formatting.BLUE).setUnderline(true).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.messageCreateEvent.getMessageAttachments().get(0).getUrl().toString()));
					}*/
					ScarpetDiscordEvents.DISCORD_MESSAGE.onDiscordMessage(content, author, channel);
				});
			} catch (CompletionException ce) {
				Discarpet.error("[Discarpet] Invalid bot token! Discord functionality disabled");
				api = null;
			}
		} else {
			Discarpet.warn("[Discarpet] No bot token specified, use /scarpetdiscord setDefault botToken [token]");
			api = null;
		}
	}
	public String getInvite() {
		return api.createBotInvite();
	}

	//discord
	public void sendMessage(String channel,String message) {
		/*
		String converted = "";
		for (User user : this.api.getCachedUsers()) {
			if(this.api.getServerChannels().toArray().length == 0) return;
			ServerChannel serverChannel = (ServerChannel) this.api.getServerChannels().toArray()[0];
			Server server = serverChannel.getServer();
			converted = message
					.replace("@" + user.getName(), user.getMentionTag())
					.replace("@" + user.getDisplayName(server), user.getMentionTag())
					.replace("@" + user.getName().toLowerCase(), user.getMentionTag())
					.replace("@" + user.getDisplayName(server).toLowerCase(), user.getMentionTag());
			if (user.getNickname(server).isPresent()) {
				converted = converted
						.replace("@" + user.getNickname(server).get(), user.getMentionTag())
						.replace("@" + user.getNickname(server).get().toLowerCase(), user.getMentionTag());
			}
		}*/
		Optional<TextChannel> textChannel = this.api.getTextChannelById(channel);
		if(textChannel.isPresent()) {
			textChannel.get().sendMessage(message);
		} else {
			throw new InternalExpressionException("Could not find Discord channel with id " + channel);
		}
	}



	public void setChannelTopic(String channel, String description) {
		Optional<ServerTextChannel> textChannel = this.api.getServerTextChannelById(channel);
		if(textChannel.isPresent()) {
			textChannel.get().updateTopic(description);
		} else {
			throw new InternalExpressionException("Could not find Discord channel with id " + channel);
		}
	}
	public void setActivity(int id, String text) {
		this.api.updateActivity(ActivityType.getActivityTypeById(id),text);
	}

	public void setStatus(int id) {
		this.api.updateStatus(getStatusFromId(id));
	}
	private UserStatus getStatusFromId(int id) {
		switch (id) {
			case 0:
				return UserStatus.INVISIBLE;
			case 1:
				return UserStatus.ONLINE;
			case 2:
				return UserStatus.IDLE;
			case 3:
				return UserStatus.DO_NOT_DISTURB;
			default:
				return UserStatus.ONLINE;
		}
	}



	public void sendEmbed(String channel, String title, String description, String authorName, String authorLink, String authorAvatar, List<EmbedField> fields, Color color, String footerName, String footerAvatar, String image, String thumbnail) {
		EmbedBuilder embed = new EmbedBuilder();
		if(!title.equals("null")) {
			embed.setTitle(title);
		}
		if(!description.equals("null")) {
			embed.setDescription(description);
		}
		if(!authorName.equals("null")) {
			if(!authorAvatar.equals("null") && !authorLink.equals("null")) {
				if(authorAvatar.startsWith("https")) {
					embed.setAuthor(authorName,authorLink,authorAvatar);
				} else {
					embed.setAuthor(authorName,authorLink,new File(authorAvatar));
				}
			} else {
				embed.setAuthor(authorName);
			}
		}
			//fields
		for(int i = 0; i < fields.size(); i++) {
			EmbedField field = fields.get(i);
			if(field.inline) {
				embed.addInlineField(field.name,field.value);
			} else {
				embed.addField(field.name,field.value);
			}
		}

		embed.setColor(color);

		if(!footerName.equals("null")) {
			if(!footerAvatar.equals("null")) {
				if(footerAvatar.startsWith("http")) {
					embed.setFooter(footerName,footerAvatar);
				} else {
					embed.setFooter(footerName,new File(footerAvatar));
				}
			} else {
				embed.setFooter(footerName);
			}
		}
		if(!image.equals("null")) {
			if(image.startsWith("https")) {
				embed.setImage(image);
			} else {
				embed.setImage(new File(image));
			}
		}
		if(!thumbnail.equals("null")) {
			if(image.startsWith("https")) {
				embed.setThumbnail(thumbnail);
			} else {
				embed.setThumbnail(new File(thumbnail));
			}
		}

		Optional<TextChannel> textChannel = this.api.getTextChannelById(channel);
		if(textChannel.isPresent()) {
			textChannel.get().sendMessage(embed);
		} else {
			throw new InternalExpressionException("Could not find Discord channel with id " + channel);
		}
	}
}
