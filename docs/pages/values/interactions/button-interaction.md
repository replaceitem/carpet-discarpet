`dc_button_interaction`

Represents an interaction from a user clicking a button from [`__on_discord_button`](/events/discord-button.md).

#### Queryable:

| Property             | Type         | Description                                                                   |
|----------------------|--------------|-------------------------------------------------------------------------------|
| `custom_id`          | String       | The custom ID of the button, as specified when creating the button component. |
| `message`            | [Message][1] | The message this interaction is attached to.                                  |

[1]: /values/message.md