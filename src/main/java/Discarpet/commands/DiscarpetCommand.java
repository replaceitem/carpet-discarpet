package Discarpet.commands;

import Discarpet.Discarpet;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Set;

import static net.minecraft.server.command.CommandManager.literal;

public class DiscarpetCommand {
    private static final SuggestionProvider<ServerCommandSource> BOTS = (commandContext, suggestionsBuilder) ->
            CommandSource.suggestMatching(Discarpet.discordBots.keySet().stream(), suggestionsBuilder);

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(literal("discarpet").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).
                then(literal("list").
                        executes(commandContext->{
                            Set<String> botIDs = Discarpet.discordBots.keySet();
                            final LiteralText t;
                            if(botIDs.size()==0) {
                                t = (LiteralText) new LiteralText("There are no bots active:\n").formatted(Formatting.RED);
                            } else {
                                t = (LiteralText) new LiteralText("There are " + botIDs.size() + " bots active\n").formatted(Formatting.GREEN);
                            }
                            botIDs.forEach(id-> t.append(new LiteralText(id + "\n").formatted(Formatting.BLUE)));
                            commandContext.getSource().sendFeedback(t,true);
                            return botIDs.size();
                        })
                )
                .then(literal("getInvite").then(CommandManager.argument("id", StringArgumentType.string()).suggests(BOTS).executes(commandContext->{
                    String id = StringArgumentType.getString(commandContext,"id");
                    String invite = Discarpet.discordBots.get(id).getInvite();
                    Text text = ((new LiteralText("Click here to get the invite link for the bot")).styled((style) -> {
                        return style.withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, invite)).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new LiteralText("Click to open the invite link"))).withInsertion(invite);
                    }));
                    commandContext.getSource().sendFeedback(text,false);
                    return 1;
                })))
                .then(literal("reload").executes(commandContext->{
                    Discarpet.loadBots(commandContext.getSource());
                    return 1;
                }))

        );
    }
}
