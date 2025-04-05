`button`

Used for creating a button component.


### {map:}

|             Key | Type                                 | Description                                                               |
|----------------:|:-------------------------------------|:--------------------------------------------------------------------------|
|            `id` | String                               | The ID of the button. Used to identify when interacted with.[^1]          |
|         `label` | String                               | The text shown on the button.                                             |
| `disabled` {:?} | Boolean<br>(`false` by default)      | Whether if this button is disabled.                                       |
|    `style` {:?} | String<br>(`GREY` by default)        | The [style](/parsables/components/button.md#button-styles) of the button. |
|    `emoji` {:?} | String, or [Emoji](/values/emoji.md) | The emoji shown on the button.                                            |
|           `url` | String                               | The URL opened when clicking the button.[^2]                              |

#### Button styles

|    String | Preview                                 |
|----------:|:----------------------------------------|
| `BLURPLE` | ![Primary](/assets/buttons/blurple.svg) |
|    `GREY` | ![Secondary](/assets/buttons/grey.svg)  |
|   `GREEN` | ![Success](/assets/buttons/green.svg)   |
|     `RED` | ![Danger](/assets/buttons/red.svg)      |
|     `URL` | ![Link](/assets/buttons/url.svg)        |


[^1]: Not required for `URL` style.
[^2]: Only required for `URL` style.