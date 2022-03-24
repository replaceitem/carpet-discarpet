# Discarpet Events


Discarpet's scarpet events are used to detect events that happen in discord servers the bot is in. Additionally, there are also events for when a chat message gets sent in minecraft or a general system message happens.

# Minecraft events

## `__on_system_message(text,type,entity)`

Event that execute on system messages, for example to be used to redirect system messages to a discord chat.

`text` -> String: Text of the system message

`type` -> String: Translation text key, or null if `text` is a literal text. For example:
   
* `chat.type.text` -> Normal chat message
* `multiplayer.player.left` -> Someone left the game
* `chat.type.admin` -> Admin command executed

`entity` -> Entity: Entity that message came from, or null if not sent from an entity

WARNING: DO NOT print out ANYTHING inside this event that would execute this event again! The server would crash because of never ending recursion!

The same thing applies for errors, since those are printed in chat as well, any error inside this event will also result in recursion!

Here is an example how you could prevent server crashes:

```python
__config() -> {'scope' -> 'global'};

global_executions = 0;

__on_system_message(text,type,entity) -> (
    global_executions += 1;
    if(global_executions < 10,
        //put all the code you want to execute here
        scoreboard('blah'); //this would cause an error if the objective doesnt exist, which would instantly call the event again
    );
);

__on_tick() -> (
    global_executions = 0;
);
```

## `__on_chat_message(message,player,command)`

Event that execute on chat messages, can be used to redirect chat messages to a discord chat.

`message` -> String: Text of the chat message

`player` -> Entity: Player that sent the message

`command` -> Boolean: Message is a command

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