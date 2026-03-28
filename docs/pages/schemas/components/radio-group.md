`radio_group`

!!! example-scripts inline end "Example scripts"
    * [Modal select](/examples/modal-select.md)

Used for creating a radio group component.


### {map:}

|               Key | Type                                          | Description                                                 |
|------------------:|:----------------------------------------------|:------------------------------------------------------------|
|       `component` | String                                        | For a radio group always `radio_group`                      |
|              `id` | String                                        | The ID of the radio group. Used to identify when submitted. |
|         `options` | List of [Radio group option objects](#option) | All options selectable in this radio group                  |
|   `required` {:?} | Boolean                                       | Whether a selection is required.                            |



## Option

`radio_group_option`

Used for creating a radio group option.


### {map:}

|                Key | Type                              | Description                                 |
|-------------------:|:----------------------------------|:--------------------------------------------|
|            `value` | String                            | The internal value of the option.           |
|            `label` | String                            | The label of the option.                    |
| `description` {:?} | String                            | The description of the option.              |
|  `is_default` {:?} | Boolean                           | Whether option this is selected by default. |



