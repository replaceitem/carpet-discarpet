package discarpet;

import carpet.settings.Rule;

public class Settings
{

    @Rule(
            desc = "Discord bot token",
            options = {},
            extra = {
                    "The token of your discord",
                    "bot, get it from the disord",
                    "developer dashboard"
            },
            category = "misc",
            strict = false
    )
    public static String botToken = "empty";

    @Rule(
            desc = "Discord bot invitation link",
            options = {},
            extra = {
                    "Use this to add your bot",
                    "to your discord server.",
                    "Set it to true, to get the link"
            },
            category = "misc",
            strict = false
    )
    public static boolean inviteLink = false;

    @Rule(
            desc = "Clear chat and bot buffer",
            options = {},
            extra = {
                    "If your chat or bot buffer",
                    "got too full, set this to",
                    "true to clear it"
            },
            category = "misc",
            strict = false
    )
    public static boolean clearBuffer = false;
}
