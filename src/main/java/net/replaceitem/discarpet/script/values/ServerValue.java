package net.replaceitem.discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.RestAction;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import org.jetbrains.annotations.Nullable;

public class ServerValue extends DiscordValue<Guild> implements Renamable {
    public ServerValue(Guild server) {
        super(server);
    }
    
    public static Value of(@Nullable Guild guild) {
        return ValueUtil.ofNullable(guild, ServerValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "server";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "id" -> StringValue.of(delegate.getId());
            case "members" -> ListValue.wrap(delegate.getMembers().stream().map(MemberValue::new));
            case "users" -> ListValue.wrap(delegate.getMembers().stream().map(Member::getUser).map(UserValue::new));
            case "channels" -> ListValue.wrap(delegate.getChannels().stream().map(ChannelValue::new));
            case "roles" -> ListValue.wrap(delegate.getRoles().stream().map(RoleValue::new));
            case "webhooks" -> ListValue.wrap(ValueUtil.awaitRest(delegate.retrieveWebhooks(), "Error getting webhooks from server").stream().map(WebhookValue::of));
            case "slash_commands" -> ListValue.wrap(ValueUtil.awaitRest(delegate.retrieveCommands(), "Error getting slash commands from server").stream().map(CommandValue::of));
            case "emojis" -> ListValue.wrap(delegate.getEmojis().stream().map(EmojiValue::of));
            case "stickers" -> ListValue.wrap(delegate.getStickers().stream().map(StickerValue::of));
            default -> super.getProperty(property);
        };
    }

    @Override
    public RestAction<?> rename(String name) {
        return delegate.getManager().setName(name);
    }
}
