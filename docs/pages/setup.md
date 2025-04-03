---
icon: octicons/rocket-16
---


## Setting up a bot

### Discord setup

Firstly, you'll need to have a Discord bot account to use. If you already know how to do this, you can go [setup Minecraft next](#minecraft-setup).
<br>If you don't, here's some quick steps:

1. Go to the [Discord Developer Portal][portal], and click on the button "Add Application" at the top right. A prompt should pop up.
2. Type in the name prompt, click on the checkbox, then click on "Create".
   <br>(Whatever you put on as the name doesn't matter, as it is separate from the bot's username)
3. After creating an application, you should be on its settings page. Navigate to the "Bot" tab from the sidebar.
   <br>From there, you may change your bot's username & profile picture.
4. Generate your bot's token by clicking "Reset Token". It may ask you for authorization. Then, copy it.
   <br>Paste your token somewhere safe temporarily (like in a Notepad window).
   <br>Do not share it with untrustworthy people!
5. Navigate to the "OAuth2" tab. Copy the client ID, then append it to this URL at the end:
   <br>`https://discord.com/oauth2/authorize?scope=bot&client_id=`
   <br>Then, open your URL in your browser. It should prompt you what server to add the bot.
6. Select the server where you want to add the bot to, then click on "Authorize".

If things didn't go wrong, you've got a Discord bot into your server! Very nice!

### Minecraft setup

!!! note
    Singleplayer usage is not officially supported. It may, or may not work.

Firstly, setup a [Fabric server](https://fabricmc.net/use/server/). Then, you'll need to install [Carpet](https://modrinth.com/mod/carpet) and [Discarpet](https://modrinth.com/mod/discarpet) in your server's mods folder.

When you restart your server, it should create a `discarpet.json` file in the config folder. The file should look like this by default:

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
This doesn't need to be what you called it in the Developer Portal,
it's just an arbitrary name.

In the `"intents"` list, you can add additional intents for your bot.
For more info, see the section about [intents](#using-intents).

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


## Setting up a script

If you have never used scarpet before,
it's recommended to first [get familiar with the scarpet language](https://github.com/gnembon/fabric-carpet/wiki/Scarpet).

<!-- Since Discarpet supports multiple bots at the same time, you need to specify which bot you want to use in your script.
This is done by specifying a `'bot'` to the config line, like this: -->

To use your bot in your script, you'll need to set the `'bot'` value inside the `__config` function.

Additionally, it is best to set the `'scope'` to `'global'`.

```sc title="my_script.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};
```

The `'bot'` value is specified from your bot's ID in the config.
Without setting a valid bot and the scope globally, most Discarpet functions and events will not work, and will throw errors.


## Using intents

In Discarpet's config, you can enable privileged intents for your bot to have access to certain things:

- `MESSAGE_CONTENT` - Required to access some properties from a [message](/values/message.md).
  <br>(`content`, `readable_content`, `attachments`)
- `GUILD_MEMBERS` - Required to access a list of users from a [server](/values/server.md).
- `GUILD_PRESENCES` - You can ignore this one. This intent is currently not utilized by Discarpet.

You can add them to your bot like so:

```json title="discarpet.json"
{
  "bots": [
    {
      "bot_id": "mybot",
      "bot_token": "token",
      "intents": [
        "MESSAGE_CONTENT",
        "GUILD_MEMBERS"
      ]
    }
  ],
  "disable_reconnect_logs": false
}
```

If you add these intents to the `"intents"` list in your bot config,
you will also need to enable them in Discord as well.
You can enable them in the [Developer Portal][portal] under your application's "Bots" tab.


## Using multiple bots

To have multiple bots running on your server,
you can add more into the `"bots"` list in the config file like this:

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
        "MESSAGE_CONTENT"
      ]
    },
    {
      "bot_id": "mybot3",
      "bot_token": "token 3",
      "intents": [
        "GUILD_MEMBERS"
      ]
    }
  ],
  "disable_reconnect_logs": false
}
```

!!! note "Notes"
    1. A script can only use one bot, and will only receive events from that bot.
    2. When getting Discord values like a
    [channel](/functions/values/channel-from-id.md)
    or a
    [message](/functions/values/message-from-id.md),
    they will have the context of the bot from that script.
    <br>
    (e.g. if you pass a message to another script and make it add a reaction,
    it will be added by the bot from the original script.)


## Enable developer mode

Most [value functions][1] require you to input an ID to get the respective value.

To get IDs, you'll need to open your Discord settings and navigate to "Advanced". Then, enable "Developer Mode".

Now, when you right click certain things (like users, channels, and messages),
the context menu will have an option to copy the ID.


## Disabling log messages

If you run Discarpet for a while, you might notice messages that look like this:

```
[ReadingThread/INFO]: Websocket closed with reason 'Discord commanded a reconnect (Received opcode 7)' and code COMMANDED_RECONNECT (4999) by client!
[ReadingThread/INFO]: Trying to reconnect/resume in 1 seconds!
```

To disable those messages, you can set the `"disable_reconnect_logs"` in the config to `true`.


[1]: /functions/values/channel-from-id.md

[portal]: https://discord.com/developers/applications