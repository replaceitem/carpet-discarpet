`separator`

A separator line for separating components.
This component requires `use_components_v2` enabled on message content.


### {map:}

|             Key | Type                 | Description                       |
|----------------:|:---------------------|:----------------------------------|
|     `component` | String               | For separators always `separator` |
|    `is_divider` | String               | Whether the divider line is shown |
|       `spacing` | [Spacing](#spacings) | The spacing around the separator  |

#### Spacings

|  String | Description                      |
|--------:|:---------------------------------|
| `small` | Smaller padding around separator |
| `large` | Larger padding around separator  |