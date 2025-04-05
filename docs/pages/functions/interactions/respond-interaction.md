### `dc_respond_interaction(interaction, type, content?)`

Sends a response to an interaction.

{% include 'blocking-function.md' %}


### {input:}

|      Parameter | Type                                                   | Description                                     |
|---------------:|:-------------------------------------------------------|:------------------------------------------------|
|  `interaction` | Any [interaction](/values/interactions/interaction.md) | The interaction to respond to.                  |
|         `type` | String                                                 | The [type](#response-types) of response to use. |
| `content` {:?} | [(See response types)](#response-types)                | The content of the response.                    |

#### Response types

|                String | Description                                                                                                                                                                                               |
|----------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|       `RESPOND_LATER` | Tells that the interaction was received, and a response will come later.<br>You will then need to send a `RESPOND_FOLLOWUP` response within 15 minutes.<br>This does not require the `content` parameter. |
| `RESPOND_IMMEDIATELY` | Sends an immediate response which has to come within 3 seconds.<br>The `content` parameter requires a [Message content](/parsables/message-content.md) for this.                                          |
|    `RESPOND_FOLLOWUP` | Sends a followup response for `RESPOND_LATER`.<br>The `content` parameter requires a [Message content](/parsables/message-content.md) for this.                                                           |
|       `RESPOND_MODAL` | Opens a modal for the user.<br>The `content` parameter requires a [Modal parsable](/parsables/modal.md) for this.                                                                                         |


### {output:}

#### {output values:}

* Null, if successful.
* The [message](/values/message.md) that was sent, if using the `RESPOND_FOLLOWUP` type.

#### {output exceptions:}

* Throws an exception on failure.