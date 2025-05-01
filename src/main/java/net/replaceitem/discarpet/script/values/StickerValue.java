package net.replaceitem.discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.sticker.GuildSticker;
import net.dv8tion.jda.api.entities.sticker.RichSticker;
import net.dv8tion.jda.api.entities.sticker.StandardSticker;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.RestAction;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;

public class StickerValue extends DiscordValue<Sticker> implements Deletable, Renamable {
    public StickerValue(Sticker sticker) {
        super(sticker);
    }

    @Override
    protected String getDiscordTypeString() {
        return "sticker";
    }

    @Override
    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getId());
            case "name" -> StringValue.of(delegate.getName());
            case "description" -> StringValue.of(delegate instanceof RichSticker richSticker ? richSticker.getDescription() : null);
            case "tags" -> delegate instanceof RichSticker richSticker ? ListValue.wrap(richSticker.getTags().stream().map(StringValue::of)) : Value.NULL;
            case "sort_value" -> delegate instanceof StandardSticker standardSticker ? NumericValue.of(standardSticker.getSortValue()) : Value.NULL;
            case "format_type" -> ValueUtil.ofEnum(delegate.getFormatType());
            case "pack_id" -> delegate instanceof StandardSticker standardSticker ? StringValue.of(standardSticker.getPackId()) : Value.NULL;
            case "server" -> delegate instanceof GuildSticker guildSticker ? ServerValue.of(guildSticker.getGuild()) : Value.NULL;
            case "type" -> delegate instanceof RichSticker richSticker ? ValueUtil.ofEnum(richSticker.getType()) : Value.NULL;
            case "user" -> delegate instanceof GuildSticker guildSticker ? UserValue.of(guildSticker.getOwner()) : Value.NULL;
            default -> super.getProperty(property);
        };
    }

    @Override
    public RestAction<?> delete(String reason) {
        if(!(delegate instanceof GuildSticker guildSticker)) throw DiscordThrowables.genericCode(ErrorResponse.UNKNOWN_STICKER);
        return guildSticker.delete().reason(reason);
    }

    @Override
    public RestAction<?> rename(String name) {
        if(!(delegate instanceof GuildSticker guildSticker)) throw DiscordThrowables.genericCode(ErrorResponse.UNKNOWN_STICKER);
        return guildSticker.getManager().setName(name);
    }
}
