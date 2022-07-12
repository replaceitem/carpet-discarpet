`select_menu`

| Value         | Type                                                                       | Description                                                 |
|---------------|----------------------------------------------------------------------------|-------------------------------------------------------------|
| `component`   | String                                                                     | Must be `select_menu`                                       |
| `id`          | String                                                                     | Custom id of the button. Used to identify them when pressed |
| `options`     | List of [Select menu options](/parsables/components/select-menu-option.md) | All options selectable on this select menu                  |
| `min`         | number (optional)                                                          | The minimum number of options that need to be selected      |
| `max`         | number (optional)                                                          | The maximum number of options that need to be selected      |
| `placeholder` | String (optional)                                                          | The text displayed when nothing is selected yet             |