## Changes

* Updated to JDA 6.1.1. With that comes some breaking changes. See below
* Added new Label component type
  * This replaces the use of Action rows in modals and the `label` property in text inputs
* Action rows are now its own component type
  * Instead of the `components` in the message content schema being a list of lists of components,
    action rows can be declared explicitly with a component of type `action_row`.
    The old syntax of defining an action row from an array is still supported for backward compatibility and convenience.
* The `label` field of text inputs has been removed. Use a label component instead.

## Migrating

To migrate your scripts, the following is necessary:

* Converting action rows in modals to labels
  * Check the updated modals example in the documentation