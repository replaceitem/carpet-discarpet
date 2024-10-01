`dc_modal_interaction`

Represents an interaction from a user's response to a modal from [`__on_discord_modal()`](/events/discord-modal.md).

{% include 'getting-interaction-details.md' %}

#### Queryable:

| Property             | Type                                 | Description                                                                                                                                                 |
|----------------------|--------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                               | The ID of the interaction.                                                                                                                                  |
| `channel`            | [Channel](/values/channel.md)        | The channel this interaction was made in.                                                                                                                   |
| `user`               | [User](/values/user.md)              | The user who made the interaction.                                                                                                                          |
| `token`              | String                               | The continuation token internally used for responding to this interaction.                                                                                  |
| `server`             | [Server](/values/server.md)          | The server this interaction was made in.                                                                                                                    |
| `locale`             | String                               | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US).                                     |
| `creation_timestamp` | Number                               | The timestamp in epoch milliseconds when this interaction was made.                                                                                         |
| `custom_id`          | String                               | The custom ID of the modal, as specified when creating the modal.                                                                                           |
| `input_values_by_id` | Map of string keys and string values | A map where the keys are the IDs of the component, and the value the values entered into them.<br>Useful for querying the values of components by their ID. |