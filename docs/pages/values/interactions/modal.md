`dc_modal_interaction`

Represents an interaction from a user's response to a modal from
[`__on_discord_modal`](/events/discord-modal.md).


### {query:}

Also has all properties from the [base interaction](/values/interactions/interaction.md)

|             Property | Type   | Description                                                                                        |
|---------------------:|:-------|:---------------------------------------------------------------------------------------------------|
|          `custom_id` | String | The custom ID of the modal.                                                                        |
| `input_values_by_id` | Map    | All text inputs and their values in a key-value format.<br>(e.g. `'text_input_id' -> 'Text here'`) |