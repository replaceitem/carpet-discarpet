package net.replaceitem.discarpet.script.values;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.commands.SlashCommandValue;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.server.Server;

public class ServerValue extends DiscordValue<Server> implements Renamable {
    public ServerValue(Server server) {
        super(server);
    }

    @Override
    protected String getDiscordTypeString() {
        return "server";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "users" -> ListValue.wrap(delegate.getMembers().stream().map(UserValue::new));
            case "channels" -> ListValue.wrap(delegate.getChannels().stream().map(ChannelValue::new));
            case "roles" -> ListValue.wrap(delegate.getRoles().stream().map(RoleValue::new));
            case "webhooks" -> ListValue.wrap(ValueUtil.awaitFuture(delegate.getWebhooks(), "Error getting webhooks from server").stream().map(WebhookValue::of));
            case "slash_commands" -> ListValue.wrap(ValueUtil.awaitFuture(delegate.getSlashCommands(), "Error getting slash commands from server").stream().map(SlashCommandValue::of));
            case "emojis" -> ListValue.wrap(delegate.getCustomEmojis().stream().map(EmojiValue::of));
            case "stickers" -> ListValue.wrap(delegate.getStickers().stream().map(StickerValue::of));
            default -> super.getProperty(property);
        };
    }

    @Override
    public void rename(String name) {
        ValueUtil.awaitFuture(delegate.updateName(name), "Could not rename server");
    }
}
