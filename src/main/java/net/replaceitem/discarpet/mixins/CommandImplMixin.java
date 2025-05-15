package net.replaceitem.discarpet.mixins;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.internal.interactions.command.CommandImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CommandImpl.class, remap = false)
public interface CommandImplMixin {
    @Accessor
    Guild getGuild();
}
