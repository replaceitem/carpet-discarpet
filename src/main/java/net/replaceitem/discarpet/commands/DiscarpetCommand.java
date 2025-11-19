package net.replaceitem.discarpet.commands;

import net.replaceitem.discarpet.Discarpet;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.replaceitem.discarpet.config.Bot;

import java.net.URI;
import java.util.Set;

import static net.minecraft.commands.Commands.literal;

public class DiscarpetCommand {
    public static final URI DOCS_URI = URI.create("https://replaceitem.github.io/carpet-discarpet/");
    private static final SuggestionProvider<CommandSourceStack> BOTS = (commandContext, suggestionsBuilder) ->
            SharedSuggestionProvider.suggest(Discarpet.discordBots.keySet().stream(), suggestionsBuilder);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(literal("discarpet").requires(serverCommandSource -> serverCommandSource.hasPermission(2)).executes(commandContext->{
            commandContext.getSource().sendSuccess(() -> {
                String version = FabricLoader.getInstance().getModContainer("discarpet").orElseThrow().getMetadata().getVersion().getFriendlyString();
                MutableComponent text = Component.literal("Discarpet version " + version).withStyle(ChatFormatting.BLUE);
                text.append("\nFor help, see the ");
                text.append(Component.literal("documentation").setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent.OpenUrl(DOCS_URI))
                        .withHoverEvent(new HoverEvent.ShowText(Component.literal("Click to get to the Discarpet documentation")))
                        .applyFormat(ChatFormatting.UNDERLINE)
                        .withColor(ChatFormatting.DARK_BLUE)));
                return text;
            },true);
            return 1;
        }).then(literal("list").
            executes(commandContext->{
                commandContext.getSource().sendSuccess(() -> {
                    Set<String> botIDs = Discarpet.discordBots.keySet();
                    if(botIDs.isEmpty()) return Component.literal("There are no bots active").withStyle(ChatFormatting.RED);
                    final MutableComponent t = Component.literal("There are " + botIDs.size() + " bots active").withStyle(ChatFormatting.GREEN);
                    botIDs.forEach(id -> t.append(Component.literal("\n" + id).withStyle(ChatFormatting.BLUE)));
                    return t;
                },true);
                return Discarpet.discordBots.size();
            })
        ).then(literal("getInvite").then(Commands.argument("id", StringArgumentType.string()).suggests(BOTS).executes(commandContext->{
            commandContext.getSource().sendSuccess(() -> {
                String id = StringArgumentType.getString(commandContext,"id");
                Bot bot = Discarpet.discordBots.get(id);
                if(bot == null) {
                    return Component.literal("Invalid bot: " + id);
                }
                String invite = bot.getInvite();
                return Component.literal("Click here to get the invite link for the bot")
                        .withStyle((style) -> style.withColor(ChatFormatting.BLUE)
                                .applyFormat(ChatFormatting.UNDERLINE)
                                .withClickEvent(new ClickEvent.OpenUrl(URI.create(invite)))
                                .withHoverEvent(new HoverEvent.ShowText(Component.literal("Click to open the invite link")))
                                .withInsertion(invite));
            },false);
            return 1;
        }))).then(literal("reload").executes(commandContext->{
                commandContext.getSource().sendSuccess(() -> Component.literal("Reloading..."), false);
                Discarpet.loadConfig(commandContext.getSource());
                return 1;
            }))
        );
    }
}
