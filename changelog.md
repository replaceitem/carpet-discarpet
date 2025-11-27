## Changes

* Updated to JDA 6.1.1. With that comes some breaking changes. See "Migrating" section below for necessary migrations

### Components

* Added new Label component type
  * This replaces the use of Action rows in modals and the `label` property in text inputs
* Action rows are now its own component type
  * Instead of the `components` in the message content schema being a list of lists of components,
    action rows can be declared explicitly with a component of type `action_row`.
    The old syntax of defining an action row from an array is still supported for backward compatibility and convenience.
* The `label` field of text inputs has been removed. Use a label component instead.
* Added support for components V2 via `use_components_v2` in message content
* Added new components from components V2 (See docs for details)
  * Separator
  * Container
  * Text display
  * Section
  * Thumbnail
  * Media gallery
  * File display
  * File upload

### Documentation

* Message components example was split into seperate buttons and select menus example
* Added misc components example for component V2 components
* Added links to relevant examples to docs for functions and schemas
* Updated modal example with file upload and select menu

## Migrating

To migrate your scripts, the following is necessary:

* Converting action rows in modals to labels
  * Check the updated modal example in the documentation