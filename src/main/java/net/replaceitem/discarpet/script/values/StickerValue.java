package net.replaceitem.discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import org.javacord.api.entity.sticker.Sticker;

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
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "name" -> StringValue.of(delegate.getName());
            case "description" -> StringValue.of(delegate.getDescription());
            case "tags" -> StringValue.of(delegate.getTags());
            case "sort_value" -> ValueUtil.ofOptionalNumber(delegate.getSortValue());
            case "format_type" -> StringValue.of(delegate.getFormatType().name());
            case "pack_id" -> ValueUtil.ofOptionalNumber(delegate.getPackId());
            case "server" -> ServerValue.of(delegate.getServer());
            case "type" -> StringValue.of(delegate.getType().name());
            case "user" -> UserValue.of(delegate.getUser());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        ValueUtil.awaitFuture(delegate.delete(reason), "Failed to delete " + this.getTypeString());
    }

    @Override
    public void rename(String name) {
        ValueUtil.awaitFuture(delegate.updateName(name), "Could not rename sticker");
    }
}
