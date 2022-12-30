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

import java.util.Set;

import static net.minecraft.server.command.CommandManager.literal;

public class DiscarpetCommand {
    private static final SuggestionProvider<ServerCommandSource> BOTS = (commandContext, suggestionsBuilder) ->
            CommandSource.suggestMatching(Discarpet.discordBots.keySet().stream(), suggestionsBuilder);

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(literal("discarpet").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).executes(commandContext->{
            String version = FabricLoader.getInstance().getModContainer("discarpet").get().getMetadata().getVersion().getFriendlyString();
            MutableText text = Text.literal("Discarpet version " + version).formatted(Formatting.BLUE);
            text.append("\nFor help, see the ");
            text.append(Text.literal("documentation").setStyle(Style.EMPTY
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Full.md"))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,Text.literal("Click to get to the Discarpet documentation")))
                    .withFormatting(Formatting.UNDERLINE)
                    .withColor(Formatting.DARK_BLUE)));
            commandContext.getSource().sendFeedback(text,true);
            return 1;
        }).then(literal("list").
            executes(commandContext->{
                Set<String> botIDs = Discarpet.discordBots.keySet();
                final MutableText t;
                if(botIDs.size()==0) {
                    t = Text.literal("There are no bots active:\n").formatted(Formatting.RED);
                } else {
                    t = Text.literal("There are " + botIDs.size() + " bots active\n").formatted(Formatting.GREEN);
                }
                botIDs.forEach(id-> t.append(Text.literal(id + "\n").formatted(Formatting.BLUE)));
                commandContext.getSource().sendFeedback(t,true);
                return botIDs.size();
            })
        ).then(literal("getInvite").then(CommandManager.argument("id", StringArgumentType.string()).suggests(BOTS).executes(commandContext->{
            String id = StringArgumentType.getString(commandContext,"id");
            String invite = Discarpet.discordBots.get(id).getInvite();
            Text text = Text.literal("Click here to get the invite link for the bot")
                    .styled((style) -> style.withColor(Formatting.BLUE)
                            .withFormatting(Formatting.UNDERLINE)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, invite))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to open the invite link")))
                            .withInsertion(invite));
            commandContext.getSource().sendFeedback(text,false);
            return 1;
        }))).then(literal("reload").executes(commandContext->{
                Discarpet.loadConfig(commandContext.getSource());
                return 1;
            }))
        );
    }
}
