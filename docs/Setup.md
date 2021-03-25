# Setting up Bots


## Logging in

First, you need a Discord bot account.
You can create one at the [Discord developer portal](https://discord.com/developers/applications).
If youre not sure how to do that, look up a tutorial for that.

When you installed Discarpet and start your server, it should create a `discarpet.json` file in the config folder of the server
(Yes, this mod is made for servers, i never tried using it in singleplayer, it may work, it may not).

The file should look like this by default:

```json
{
  "bots": [
    {
      "bot_id": "Your Bot ID",
      "bot_token": "Your Bot Token",
      "member_intent": false,
      "presence_intent": false
    }
  ]
}
```

To add your bot to the game, copy and paste your Bot token from the Developer portal into the `"Your Bot Token"` field.
The `"bot_id"` is used to identify your bot in scarpet later. You should just give the bot a name so you can identify it.
This doesnt need to be what you called it in the developer portal,
it's just an arbitrary name.
The `member_intent` and `presence_intent` can be set to `true` if these intents are needed by your bots.
For more info, see the section about [indents](#Indents).

Now your config should look something like this:

```json
{
  "bots": [
    {
      "bot_id": "coolbot",
      "bot_token": "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
      "member_intent": false,
      "presence_intent": false
    }
  ]
}
```

Now, you can use the in-game command `/discarpet reload` to reload all the bots, and start new ones if you added one to the config.
This command will tell you whether the bots could be logged in or not.

When the login was successful, you can use `/discarpet getInvite [bot id]` to get a link in chat, which upon click,
will take you directly to the webpage where you can add the bot to any discord server you have admin permission in.

## Making a script

Now its time time to create your bot scarpet script!

Since Discarpet supports multiple bots at the same time, you need to specify which bot you want to use in your script.
This is done by specifying a `'bot'` to the config line, like this:

`__config()->{'bot'->'bot_id'}`

The `'bot_id'` is the id you specified in the config.
Without a valid bot specified, no discarpet functions will work and will all throw an error.

## Multiple bots

To have multiple bots running on your server, just add them to the config file like this:

```json
{
  "bots": [
    {
      "bot_id": "bot1",
      "bot_token": "token1"
    },
    {
      "bot_id": "bot2",
      "bot_token": "token2"
    },
    {
      "bot_id": "third_bot",
      "bot_token": "token for third bot"
    }
  ]
}
```

Keep in mind that each script can only have one bot.
Each script will only receive events from that one specified bot,
and when getting [values from ids](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#Values-from-ids),
the value will have the context of the bot of the script. That would mean that if you pass a message value from an event to another script,
and add a reaction there, ther user of the reaction will still be from the script where the event happened.
Only if you query [values from ids](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#Values-from-ids),
the bot from the config will be applied.

# Intents

In the config file, you can enable two kinds of intents for your bot.
You should leave them as `false`, unless you use functions that require your bot to have this permission.
In that case, you also need to enable the permissions in the Discord developer portal (Bot/Privileged Gateway Intents).
There is a hint at all functions that need an additional Intent so you know when you need them.

