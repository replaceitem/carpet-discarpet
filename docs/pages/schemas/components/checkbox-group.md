`checkbox_group`

!!! example-scripts inline end "Example scripts"
    * [Modal select](/examples/modal-select.md)

Used for creating a checkbox group component.


### {map:}

|               Key | Type                                             | Description                                                    |
|------------------:|:-------------------------------------------------|:---------------------------------------------------------------|
|       `component` | String                                           | For a checkbox group always `checkbox_group`                   |
|              `id` | String                                           | The ID of the checkbox group. Used to identify when submitted. |
|         `options` | List of [Checkbox group option objects](#option) | All options selectable in this checkbox group                  |
|   `required` {:?} | Boolean                                          | Whether a selection is required.                               |
| `min_values` {:?} | Number                                           | The minimum number of options that need to be selected.        |
| `max_values` {:?} | Number                                           | The maximum number of options that can be selected.            |



## Option

`checkbox_group_option`

Used for creating a checkbox group option.


### {map:}

|                Key | Type                              | Description                                 |
|-------------------:|:----------------------------------|:--------------------------------------------|
|            `value` | String                            | The internal value of the option.           |
|            `label` | String                            | The label of the option.                    |
| `description` {:?} | String                            | The description of the option.              |
|  `is_default` {:?} | Boolean                           | Whether option this is selected by default. |



