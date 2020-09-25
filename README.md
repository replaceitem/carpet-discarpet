# Discarpet
## A Carpet extension for scarpet discord functionality

This mod is still under development.



Note: I am not planning to actively maintain this, but i'll release some versions sometimes...
Main reason is i just made this mod for myself and just thought i'd release it if someone needs it.





Message me on discord: `replaceitem#9118`





## Discord functions



### `dc_send_message(channel,message)`

Sends a standard discord message to a certain channel.

`channel` -> String: Discord channel ID, see how you can get it for a certain channel [here](https://support.discord.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-)

`message` -> String: The message you want to send

Example: `dc_send_message('759422744826335891','Hello discord!');` (This obvoiosly won't work, you have to use your own channel ID)



### `dc_set_channel_topic(channel,topic)`

Sets the topic of a channel.

Warning: Discord has high rate limits on this, if you do it too quickly multiple times it will not update it, so i'd recommend calling this max every 10 min

NOTE: Your bot needs the "Manage Channels" permission to do that!

`channel` -> String: Discord channel ID, see how you can get it for a certain channel [here](https://support.discord.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-).

`topic` -> String: The topic to set for the channel

Example: `dc_set_channel_topic('759422744826335891','Test topic');` (This obvoiosly won't work, you have to use your own channel ID)



### `dc_set_activity(type,text)`

Sets the activity of the bot.

Warning: Discord has high rate limits on this, if you do it too quickly multiple times it will not update it, so i'd recommend calling this max every 10 min


`type` -> Number: The type of the activity:

  0 -> Playing
  
  1 -> Streaming
  
  2 -> Listening
  
  3 -> Watching
  
  
`text` -> String: The text for the activity

Example: `dc_set_activity(0,'on a Minecraft server');` Will result in the bot's activity saying "Playing on a Minecraft server".



### `dc_set_status(status)`

Sets the activity of the bot.


`status` -> Number: Status

  0 -> Offline (Invisible)
  
  1 -> Online
  
  2 -> Idle
  
  3 -> Do not disturb


Example: `dc_set_status(2);` Bot will appear as Idle



### `dc_send_embed(channel,title,description,authorName,authorLink,authorAvatar,fields,color,footerName,footerAvatar,image,thumbnail)`

Send an embed.

This is very experimental right now (and a mess in scarpet code), but it should kinda work, i may update it to make it better (probably using hash maps).


  `channel` -> String: Channel ID

  `title` -> String: Title of embed

  `description` -> String: Description of embed

  `authorName` -> String: Author name

  `authorLink` -> String: URL to open when clicked on author name

  `authorAvatar` -> String: URL or File path to image to use as an author avatar

  `fields` -> Array of Arrays: List of embed fields, like this:
      
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`[ [inline,title,description] , [inline,title,description] , ....]`

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`inline` -> boolean: If this field is inline (See example below)
      
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`title` -> String: Title of the field
      
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`description` -> String: Description of the field
      
  `color` -> Array: 3-long array of RGB values

  `footerName` -> String: Footer name
  
  `footerAvatar` -> String: Footer avatar URL or file path
  
  `image` -> String: URL or file path to image displayed in embed
  
  `thumbnail` -> String: URL or file path to thumbail in the embed


Example:

```
sd_send_embed('759102744761335891','This is an embed','It has a description','Steve','https://www.minecraft.net','https://minotar.net/avatar/steve',[[false,'Normal field','Field description'],[true,'Inline field','Description'],[true,'Inline','Yay!']],[128,255,0],'Footer name!','https://minotar.net/avatar/notch','https://cdn.pixabay.com/photo/2015/11/03/15/58/minecraft-1021046__340.png','https://minotar.net/armor/bust/gnembon/100.png');
```

will send this embed:

![Example embed](/embed.png)
