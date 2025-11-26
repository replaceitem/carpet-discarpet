`dc_modal_interaction`

Represents an interaction from a user's response to a modal from
[`__on_discord_modal`](/events/discord-modal.md).


### {query:}

Also has all properties from the [base interaction](/values/interactions/interaction.md)

|             Property | Type                                                 | Description                                                                                                                                   |
|---------------------:|:-----------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------|
|          `custom_id` | String                                               | The custom ID of the modal.                                                                                                                   |
|       `values_by_id` | Map of String -> [Modal interaction option](#option) | All component ids and their values in a key-value format.                                                                                     |
| `input_values_by_id` | Map of String -> String                              | All text inputs and their values in a key-value format. Prefer to use the above for retrieving values other than from text input components.. |


## Option

`dc_modal_interaction_option`

Represents the submitted value of one component inside the modal.


### {query:}

|    Property | Type                                                                           | Description                                                                                                                                                                 |
|------------:|:-------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `custom_id` | String                                                                         | The custom id of the component                                                                                                                                              |
|      `type` | String                                                                         | The [type](/schemas/components/component.md#component-types) of the component                                                                                               |
|     `value` | String, List of Strings, Boolean, List of [Attachments](/values/attachment.md) | The value submitted by the user. The type depends on the component type:<br/>* Text input: String<br/>* Select menu: List of Strings<br/>* File upload: List of Attachments |