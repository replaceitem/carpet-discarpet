`button`

Used for creating a button component.

| Value                                     | Type                            | Description                                                  |
|------------------------------------------:|---------------------------------|--------------------------------------------------------------|
| `id` <br> (not required for `url` style)  | String                          | Custom ID of the button. Used to identify them when pressed. |
| `label`                                   | String                          | The text shown on the button.                                |
| `disabled` {:?}                           | Boolean<br>(`false` by default) | Whether the button is greyed out or pressable.               |
| `style` {:?}                              | String<br>(`grey` by default)   | The [style][2] of the button.                                |
| `emoji` {:?}                              | String, or [Emoji value][1]     | The emoji shown on the button.                               |
| `url` <br> (not required for `url` style) | String                          | The URL opened when clicking the button.                     |

#### Button styles

* `blurple`
* `grey`
* `green`
* `red`
* `url`

[1]: /values/emoji.md

[2]: /parsables/components/button.md#button-styles