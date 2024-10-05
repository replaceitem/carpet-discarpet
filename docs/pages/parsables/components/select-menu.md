`select_menu_string`, `select_menu_user`, `select_menu_role`, `select_menu_mentionable`, `select_menu_channel`

| Value         | Type                                                                                     | Description                                                                     |
|---------------|------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `id`          | String                                                                                   | The ID of the button. Used to identify them when pressed.                       |
| `options`     | List of [Select menu options](/parsables/components/select-menu-option.md)<br>(optional) | All options selectable in this select menu, only for `select_menu_string` type. |
| `min`         | Number<br>(optional)                                                                     | The minimum number of options that need to be selected.                         |
| `max`         | Number<br>(optional)                                                                     | The maximum number of options that can be selected.                             |
| `placeholder` | String<br>(optional)                                                                     | The text displayed when nothing is selected yet.                                |