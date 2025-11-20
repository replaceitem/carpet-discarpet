`action_row`

!!! example-scripts inline end "Example scripts"
    * [Buttons](/examples/buttons.md)

Action rows are used in messages for having multiple buttons in one row, or containing one select menu.

For convenience and backward compatibility, this can also be directly parsed from simply a list of components.

### {map:}

|             Key | Type                               | Description                                                                                    |
|----------------:|:-----------------------------------|:-----------------------------------------------------------------------------------------------|
|     `component` | String                             | For action rows always `action_row`                                                            |
|    `components` | List of [Components](component.md) | The child components. Either up to 5 [buttons](button.md) or one [select menu](select-menu.md) |
