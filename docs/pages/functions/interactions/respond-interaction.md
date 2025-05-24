### `dc_respond_interaction(interaction, type, content?)`

Sends a response to an interaction.

{% include 'blocking-function.md' %}


### {input:}

|      Parameter | Type                                                   | Description                                        |
|---------------:|:-------------------------------------------------------|:---------------------------------------------------|
|  `interaction` | Any [interaction](/values/interactions/interaction.md) | The interaction to respond to.                     |
|         `type` | String                                                 | The [type](#response-types) of response to use.    |
| `content` {:?} | [(See response types)](#response-types)                | The data of the response, depending on the `type`. |

#### Response types

|                String | Description                                                                                                                                                                                                                                                   |
|----------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|       `respond_later` | Tells that the interaction was received, and a response will come later.<br>You will then need to send a `RESPOND_FOLLOWUP` response within 15 minutes.<br>The `content` parameter optionally accepts [Respond Later Data](/parsables/respond-later-data.md). |
| `respond_immediately` | Sends an immediate response which has to come within 3 seconds.<br>The `content` parameter requires a [Message content](/parsables/message-content.md) for this.                                                                                              |
|    `respond_followup` | Sends a followup response for `RESPOND_LATER`.<br>The `content` parameter requires a [Message content](/parsables/message-content.md) for this.                                                                                                               |
|       `respond_modal` | Opens a modal for the user.<br>The `content` parameter requires a [Modal parsable](/parsables/modal.md) for this.                                                                                                                                             |


### {output:}

#### {output values:}

* Null, if successful.
* The [message](/values/message.md) that was sent, if using the `respond_followup` or `respond_immediately` type.

#### {output exceptions:}

* Throws an exception on failure.