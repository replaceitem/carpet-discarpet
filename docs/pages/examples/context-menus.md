---
icon: material/menu-open
---


Adds multiple context menu commands:

* One [message command](/schemas/commands/message-context-menu-builder.md), which waits 10 seconds and then deletes the message.
* Two [user commands](/schemas/commands/user-context-menu-builder.md), one for adding a [prefix] to a user's nickname, and one for removing it.

!!! note "Notes"
    1. Your bot will need "Manage Messages" and "Manage Nicknames" permissions.
    2. The bot cannot set the nickname of someone that is higher in role hierarchy than itself.


![Demo context menu](/assets/examples/context-menu.png)

{% include 'generated/examples/context-menus.md' %}
