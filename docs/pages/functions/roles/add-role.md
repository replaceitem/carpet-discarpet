### `dc_add_role(user, role, reason?)`

Adds a role to a user.

{% include 'blocking-function.md' %}


### {input:}

|     Parameter | Type                    | Description                  |
|--------------:|:------------------------|:-----------------------------|
|        `user` | [User](/values/user.md) | The user to add the role to. |
|        `role` | [Role](/values/role.md) | The role to add.             |
| `reason` {:?} | String                  | The audit log reason.        |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

* Throws an exception on failure.
* `missing_permission`
    * `50001` - You do not have "Manage Roles" permission
    * `50013` - You may be trying to add a role that is of higher hierachy