## Logging in

First, you need a Discord bot account.
You can create one at the [Discord Developer Portal](https://discord.com/developers/applications).
If you aren't sure how to do that, look up a tutorial for that.

When you installed Discarpet and start your server, it should create a `discarpet.json` file in the config folder of the server.
(Yes, this mod is made for servers, I never tried using it in singleplayer, it may work, it may not).

The file should look like this by default:

```json title="discarpet.json"
{
  "bots": [
    {
      "bot_id": "Your bot ID",
      "bot_token": "Your bot token",
      "intents": []
    }
  ],
  "disable_reconnect_logs": false
}
```

To add your bot to the config, copy and paste your bot token from the Developer Portal into the `"bot_token"` field.

The `"bot_id"` is an arbitrary name used to identify your bot in scarpet later.
This doesn't need to be what you called it in the developer portal,
it's just an arbitrary name.

In the `"intents"` list, you can add additional intents for your bot.
For more info, see the section about [intents](#intents).

Now your config should look something like this:

```json title="discarpet.json"
{
  "bots": [
    {
      "bot_id": "coolbot",
      "bot_token": "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
      "intents": []
    }
  ],
  "disable_reconnect_logs": false
}
```

Now, you can use the in-game command `/discarpet reload` to reload all the bots, and start new ones if you added one to the config.
This command will tell you whether the bots could be logged in or not.

When login was successful, you can use `/discarpet getInvite [bot id]` to get a link in chat, which upon clicking,
will take you directly to the webpage where you can add the bot to any Discord server you have the "Manage Server" permission in.

## Making a script

Now it's time to create your bot scarpet script!

If you have never used scarpet before,
it's recommended to first [get familiar with the scarpet language](https://github.com/gnembon/fabric-carpet/wiki/Scarpet).

Since Discarpet supports multiple bots at the same time, you need to specify which bot you want to use in your script.
This is done by specifying a `'bot'` to the config line, like this:

```sc title="my_script.sc"
__config()->{'bot'->'bot_id'}
```

The `'bot_id'` is the id you specified in the config.
Without a valid bot specified, most Discarpet functions will not work and will throw an error.

## Multiple bots

To have multiple bots running on your server, just add them to the config file like this:

```json title="discarpet.json"
{
  "bots": [
    {
      "bot_id": "bot1",
      "bot_token": "token1",
      "intents": []
    },
    {
      "bot_id": "bot2",
      "bot_token": "token2",
      "intents": [
        "GUILD_PRESENCES",
        "GUILD_MEMBERS"
      ]
    },
    {
      "bot_id": "third_bot",
      "bot_token": "token for third bot",
      "intents": []
    }
  ],
  "disable_reconnect_logs": false
}
```

Keep in mind that each script can only have one bot.
Each script will only receive events from that one specified bot,
and when getting [values from IDs](/functions/value-from-id.md),
the value will have the context of the bot of the script. That would mean that if you pass a message value from an event to another script,
and add a reaction there, the user of the reaction will still be from the script where the event happened.
Only if you query [values from IDs](/functions/value-from-id.md),
the bot from the config will be applied.

## Intents

In the config file, you can enable privileged intents for your bot.
By default, all non-privileged intents are enabled.
The only privileged intents that are disabled by default are:

- MESSAGE_CONTENT
- GUILD_MEMBERS
- GUILD_PRESENCES

If you add these intents to the `"intents"` list in your bot config,
you will also need to enable them in the [Discord Developer Portal](https://discord.com/developers/applications)
under Applications -> [Your bot] -> Bot -> Privileged Gateway Intents.


## Disabling log messages

If you run Discarpet for a while, you might notice messages that look like this:

```
[ReadingThread/INFO]: Websocket closed with reason 'Discord commanded a reconnect (Received opcode 7)' and code COMMANDED_RECONNECT (4999) by client!
[ReadingThread/INFO]: Trying to reconnect/resume in 1 seconds!
```

To disable those messages, you can set the `"disable_reconnect_logs"` config option to `true`