`button`

| Value       | Type                                                | Description                                                                     |
|-------------|-----------------------------------------------------|---------------------------------------------------------------------------------|
| `component` | String                                              | Must be `button`                                                                |
| `id`        | String<br>(optional, not required for `url` style)  | Custom id of the button. Used to identify them when pressed                     |
| `label`     | String                                              | The text shown on the button                                                    |
| `disabled`  | Boolean<br>(optional, defaults to false)            | Whether the button is greyed out or pressable                                   |
| `style`     | String<br>(optional, defaults to grey)              | The [style](/parsables/components/button.md#button-styles) of the button        |
| `emoji`     | String, or Emoji value<br>(optional)                | The emoji shown on the button                                                   |
| `url`       | String<br>(optional, required only for `url` style) | The URL opened when clicking the button                                         |

#### Button styles
* `blurple`
* `grey`
* `green`
* `red`
* `url`