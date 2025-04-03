`dc_modal_interaction`

Represents an interaction from a user's response to a modal from [`__on_discord_modal`](/events/discord-modal.md).

#### Queryable:

| Property             | Type                                 | Description                                                                                                                                                 |
|----------------------|--------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `custom_id`          | String                               | The custom ID of the modal, as specified when creating the modal.                                                                                           |
| `input_values_by_id` | Map of string keys and string values | A map where the keys are the IDs of the component, and the value the values entered into them.<br>Useful for querying the values of components by their ID. |