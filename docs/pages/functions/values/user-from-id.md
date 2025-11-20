### `dc_user_from_id(id)`

Gets the user from the specified ID.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type           | Description         |
|----------:|:---------------|:--------------------|
|      `id` | String, Number | The ID of the user. |


### {output:}

#### {output values:}

* [User](/values/user.md)

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`