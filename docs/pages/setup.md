---
icon: octicons/rocket-16
---

## Setting up a bot

### Discord setup

Firstly, you'll need to have a Discord bot account to use. If you already know how to do this, you can go to the [next section](#minecraft-setup).
<br>If you don't, here's some quick steps:

1. Go to the [Discord Developer Portal](https://discord.com/developers/applications), and click on the button "Add Application" at the top right. A prompt should pop up.
2. Put anything in the name prompt, click on the checkbox, then click on "Create".
   <br>(Whatever you put on as the name doesn't matter, as that is not used for the bot's username. It'll come up later, don't worry.)
3. After creating an application, you should be on its settings page. Navigate to the "Bot" tab from the sidebar.
   From there, you may change your bot's username & profile picture.
4. Generate your bot's token by clicking "Reset Token". It may ask you for authorization.
   Then, paste your token somewhere safe temporarily (like in a Notepad window).
   Do not share it with other people (unless if you absolutely trust them), as they'll have access to the bot.
5. Then, navigate to the "OAuth2" tab. Get the client ID, and append it to this URL at the end:
   <br>`https://discord.com/oauth2/authorize?scope=bot&client_id=`
   <br>Then, open your URL in your browser. It should prompt you what server to add the bot.
6. Select the server where you want to add the bot to, then click on "Authorize".

If things didn't go wrong, you've got a Discord bot into your server! Very nice!

Now, let's set things up in Minecraft.

### Minecraft setup

!!! note
    Singleplayer usage is not officially supported. It may, or may not work.

We'll assume you have a brand new server setup. You'll need to install [Carpet](https://modrinth.com/mod/carpet) and [Discarpet](https://modrinth.com/mod/discarpet) in your mods folder.

When you start your server, it should create a `discarpet.json` file in the config folder. The file should look like this by default:

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

To add your bot to the config, paste your bot's token into the `"bot_token"` field.

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
      "bot_id": "mybot",
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
__config() -> {
    'bot' -> 'mybot'
};
```

The `'bot_id'` is the id you specified in the config.
Without a valid bot specified, most Discarpet functions will not work and will throw an error.

## Multiple bots

To have multiple bots running on your server, you can add more into the `"bots"` list in the config file like this:

```json title="discarpet.json"
{
  "bots": [
    {
      "bot_id": "mybot",
      "bot_token": "token",
      "intents": []
    },
    {
      "bot_id": "mybot2",
      "bot_token": "token 2",
      "intents": [
        "GUILD_PRESENCES",
        "GUILD_MEMBERS"
      ]
    },
    {
      "bot_id": "mybot3",
      "bot_token": "token 3",
      "intents": []
    }
  ],
  "disable_reconnect_logs": false
}
```

Keep in mind that each script can only use one bot.
Each script will only receive events from that one specified bot,
and when getting [values from IDs](/functions/value-from-id.md),
the value will have the context of the bot of the script.

That would mean that if you pass a message value from an event to another script,
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
you will also need to enable them in Discord as well. You can enable them in the [Discord Developer Portal](https://discord.com/developers/applications)
under your application's "Bots" tab.


## Disabling log messages

If you run Discarpet for a while, you might notice messages that look like this:

```
[ReadingThread/INFO]: Websocket closed with reason 'Discord commanded a reconnect (Received opcode 7)' and code COMMANDED_RECONNECT (4999) by client!
[ReadingThread/INFO]: Trying to reconnect/resume in 1 seconds!
```

To disable those messages, you can set the `"disable_reconnect_logs"` config option to `true`