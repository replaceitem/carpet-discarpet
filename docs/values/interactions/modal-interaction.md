`dc_modal_interaction`

Value from `__on_discord_modal(interaction)` event, used for getting the modal interaction details, and then responding to it with `dc_respond_interaction()`

Queryable:

| Property             | Type                                 | Description                                                                                                                                             |
|----------------------|--------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                               | Custom id of the modal, as specified when creating it                                                                                                   |
| `channel`            | Channel                              | The channel this interaction was made in.                                                                                                               |
| `user`               | User                                 | The user that used the interaction.                                                                                                                     |
| `locale`             | String                               | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US)                                  |
| `input_values_by_id` | Map of string keys and string values | A map where the keys are the ids of the component, and the value the values entered into them. Useful for querying the values of components by their id |