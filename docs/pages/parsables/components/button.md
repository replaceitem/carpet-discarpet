`button`

| Value       | Type                                                     | Description                                                               |
|-------------|----------------------------------------------------------|---------------------------------------------------------------------------|
| `id`        | String<br>(Not required for `url` style)                 | Custom ID of the button. Used to identify them when pressed.              |
| `label`     | String                                                   | The text shown on the button.                                             |
| `disabled`  | Boolean<br>(optional, `false` by default)                | Whether the button is greyed out or pressable.                            |
| `style`     | String<br>(optional, `grey` by default)                  | The [style](/parsables/components/button.md#button-styles) of the button. |
| `emoji`     | String, or [Emoji value](/values/emoji.md)<br>(optional) | The emoji shown on the button.                                            |
| `url`       | String<br>(Required only for `url` style)                | The URL opened when clicking the button.                                  |

#### Button styles
* `blurple`
* `grey`
* `green`
* `red`
* `url`