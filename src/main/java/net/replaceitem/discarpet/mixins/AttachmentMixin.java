package net.replaceitem.discarpet.mixins;

import net.dv8tion.jda.api.entities.Message;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Message.Attachment.class, remap = false)
public interface AttachmentMixin {
    @Accessor("this$0")
    Message getMessage();
}