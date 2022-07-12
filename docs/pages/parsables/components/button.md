`button`

| Value       | Type                                                    | Description                                                                    |
|-------------|---------------------------------------------------------|--------------------------------------------------------------------------------|
| `component` | String                                                  | Must be `button`                                                               |
| `id`        | String (optional, not required for `url` style buttons) | Custom id of the button. Used to identify them when pressed                    |
| `label`     | String                                                  | The text shown on the button                                                   |
| `disabled`  | boolean (optional, defaults to false)                   | Whether the button is greyed out or pressable                                  |
| `style`     | String (optional, defaults to grey)                     | Button style. Can be `blurple`, `grey`, `green`, `red` and `url`               |
| `emoji`     | String or Emoji value (optional)                        | The emoji shown on the button                                                  |
| `url`       | String (optional, only required for `url` style)        | The URL opened when clicking the button. This is only used for the `url` style |