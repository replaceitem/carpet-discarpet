`container`

Containers wrap other components in a box while also showing an accent bar on the left side, just like embeds are shown.

### {map:}

|                 Key | Type                               | Description                                                           |
|--------------------:|:-----------------------------------|:----------------------------------------------------------------------|
|         `component` | String                             | For containers always `container`                                     |
|        `components` | List of [Components](component.md) | The child components inside the container.                            |
| `accent_color` {:?} | [Color](../color.md)               | The accent color of the left border.                                  |
|      `spoiler` {:?} | Boolean                            | Whether the content of the container is hidden with a spoiler button. |
