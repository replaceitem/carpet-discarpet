`button`

Used for creating a button component.

|           Value | Type                                 | Description                                                               |
|----------------:|:-------------------------------------|:--------------------------------------------------------------------------|
|            `id` | String                               | The ID of the button. Used to identify them when pressed.[^1]          |
|         `label` | String                               | The text shown on the button.                                             |
| `disabled` {:?} | Boolean<br>(`false` by default)      | Whether if this button is disabled.                                       |
|    `style` {:?} | String<br>(`grey` by default)        | The [style](/parsables/components/button.md#button-styles) of the button. |
|    `emoji` {:?} | String, or [Emoji](/values/emoji.md) | The emoji shown on the button.                                            |
|           `url` | String                               | The URL opened when clicking the button.[^2]                              |

#### Button styles

* `blurple` -
  <button class="discord blurple">Primary</button>
* `grey` -
  <button class="discord grey">Secondary</button>
* `green` -
  <button class="discord green">Success</button>
* `red` -
  <button class="discord red">Danger</button>
* `url` -
  [<button class="discord url" markdown>Link :octicons-link-external-16:</button>](https://replaceitem.net)

[^1]: Not required for `url` style
[^2]: Only required for `url` style