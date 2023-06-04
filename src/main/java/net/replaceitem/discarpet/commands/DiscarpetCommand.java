package net.replaceitem.discarpet.commands;

import net.replaceitem.discarpet.Discarpet;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.replaceitem.discarpet.config.Bot;

import java.util.Set;

import static net.minecraft.server.command.CommandManager.literal;

public class DiscarpetCommand {
    private static final SuggestionProvider<ServerCommandSource> BOTS = (commandContext, suggestionsBuilder) ->
            CommandSource.suggestMatching(Discarpet.discordBots.keySet().stream(), suggestionsBuilder);

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(literal("discarpet").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(commandContext->{
            commandContext.getSource().sendFeedback(() -> {
                String version = FabricLoader.getInstance().getModContainer("discarpet").get().getMetadata().getVersion().getFriendlyString();
                MutableText text = Text.literal("Discarpet version " + version).formatted(Formatting.BLUE);
                text.append("\nFor help, see the ");
                text.append(Text.literal("documentation").setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://replaceitem.github.io/carpet-discarpet/"))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,Text.literal("Click to get to the Discarpet documentation")))
                        .withFormatting(Formatting.UNDERLINE)
                        .withColor(Formatting.DARK_BLUE)));
                return text;
            },true);
            return 1;
        }).then(literal("list").
            executes(commandContext->{
                commandContext.getSource().sendFeedback(() -> {
                    Set<String> botIDs = Discarpet.discordBots.keySet();
                    if(botIDs.size() == 0) return Text.literal("There are no bots active").formatted(Formatting.RED);
                    final MutableText t = Text.literal("There are " + botIDs.size() + " bots active").formatted(Formatting.GREEN);
                    botIDs.forEach(id -> t.append(Text.literal("\n" + id).formatted(Formatting.BLUE)));
                    return t;
                },true);
                return Discarpet.discordBots.size();
            })
        ).then(literal("getInvite").then(CommandManager.argument("id", StringArgumentType.string()).suggests(BOTS).executes(commandContext->{
            commandContext.getSource().sendFeedback(() -> {
                String id = StringArgumentType.getString(commandContext,"id");
                Bot bot = Discarpet.discordBots.get(id);
                if(bot == null) {
                    return Text.literal("Invalid bot: " + id);
                }
                String invite = bot.getInvite();
                return Text.literal("Click here to get the invite link for the bot")
                        .styled((style) -> style.withColor(Formatting.BLUE)
                                .withFormatting(Formatting.UNDERLINE)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, invite))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to open the invite link")))
                                .withInsertion(invite));
            },false);
            return 1;
        }))).then(literal("reload").executes(commandContext->{
                commandContext.getSource().sendFeedback(() -> Text.literal("Reloading..."), false);
                Discarpet.loadConfig(commandContext.getSource());
                return 1;
            }))
        );
    }
}
