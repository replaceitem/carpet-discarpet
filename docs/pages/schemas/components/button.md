`button`

!!! example-scripts inline end "Example scripts"
    * [Buttons](/examples/buttons.md)

Used for creating a button component.


### {map:}

|             Key | Type                               | Description                                                             |
|----------------:|:-----------------------------------|:------------------------------------------------------------------------|
|     `component` | String                             | For buttons always `button`                                             |
|            `id` | String                             | The ID of the button. Used to identify when interacted with.[^1]        |
|         `label` | String                             | The text shown on the button.                                           |
| `disabled` {:?} | Boolean<br>(`false` by default)    | Whether this button is disabled.                                        |
|    `style` {:?} | String<br>(`secondary` by default) | The [style](/schemas/components/button.md#button-styles) of the button. |
|    `emoji` {:?} | [Emoji object](/schemas/emoji.md)  | The emoji shown on the button.                                          |
|      `url` {:?} | String                             | The URL opened when clicking the button.[^2]                            |

#### Button styles

|      String | Preview                                     |
|------------:|:--------------------------------------------|
|   `primary` | ![Primary](/assets/buttons/primary.svg)     |
| `secondary` | ![Secondary](/assets/buttons/secondary.svg) |
|   `success` | ![Success](/assets/buttons/success.svg)     |
|    `danger` | ![Danger](/assets/buttons/danger.svg)       |
|      `link` | ![Link](/assets/buttons/link.svg)           |


[^1]: Not required for `URL` style.
[^2]: Only required for `URL` style.