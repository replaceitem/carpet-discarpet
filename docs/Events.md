# Discarpet Events


Discarpet's scarpet events are used to detect events that happen in discord servers the bot is in. Additionally, there are also events for when a chat message gets sent in minecraft or a general system message happens.

# Minecraft events

## `__on_system_message(text,type)`

Event that execute on system messages, for example to be used to redirect system messages to a discord chat.

`text` -> String: Text of the system message

`type` -> String: Translation text key, or null if `text` is a literal text. For example:
   
* `chat.type.text` -> Normal chat message
* `multiplayer.player.left` -> Someone left the game
* `chat.type.admin` -> Admin command executed

## `__on_command_executed(player, command)`

Triggers when a player executes a command

`player` -> Entity: Player that executed the command

`command` -> String: Command that was executed

# Discord events

## `__on_discord_message(message)`

Executes when a message is sent in a channel the bot has access to.

`message` -> [Message](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message): The message that was sent

## `__on_discord_reaction(reaction,user,added)`

Executes when a user reacts to a message with some emoji

`reaction` -> [Reaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#reaction): The reaction that was made containing the emoji

`user` -> [User](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user): The user who reacted

`added` -> boolean, `true` if the reaction was added, `false` if the reaction was removed

## `__on_discord_slash_command(interaction)`

Executes when a user runs a slash command

`interaction` -> [Slash command interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Slash-command-interaction): The slash command interaction containing everything about the command that was executed

## `__on_discord_button(interaction)`

Executes when a user presses a button component on a message

`interaction` -> [Button interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Button-and-Select-menu-interaction): The button interaction containing everything about the button that was pressed

## `__on_discord_select_menu(interaction)`

Executes when a user uses a select menu component on a message

`interaction` -> [Select menu interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Button-and-Select-menu-interaction): The select menu interaction containing everything about the select menu that was used