package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.AttachmentValue;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import org.jetbrains.annotations.Nullable;

public class ModalInteractionOptionValue extends DiscordValue<ModalMapping> {
    public ModalInteractionOptionValue(ModalMapping modalMapping) {
        super(modalMapping);
    }

    public static Value of(@Nullable ModalMapping modalMapping) {
        return ValueUtil.ofNullable(modalMapping, ModalInteractionOptionValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "modal_interaction_option";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "custom_id" -> StringValue.of(delegate.getCustomId());
            case "type" -> ValueUtil.ofEnum(delegate.getType());
            case "value" -> this.getValue();
            default -> super.getProperty(property);
        };
    }

    private Value getValue() {
        return switch(delegate.getType()) {
            case TEXT_INPUT -> StringValue.of(delegate.getAsString());
            case STRING_SELECT, USER_SELECT, ROLE_SELECT, CHANNEL_SELECT, MENTIONABLE_SELECT -> ListValue.wrap(delegate.getAsStringList().stream().map(StringValue::of));
            case FILE_UPLOAD -> ListValue.wrap(delegate.getAsAttachmentList().stream().map(AttachmentValue::of));
            default -> Value.NULL;
        };
    }
}
