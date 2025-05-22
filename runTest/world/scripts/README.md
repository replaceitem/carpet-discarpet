This scripts folder contains all examples prefixed with `e_`.
In order to run them for testing, you need to create an environment file that specifies a channel ID and server ID.
The example scripts will then use that through the `env.scl` library.

In the shared resources folder, create a `shared/env.json` file with the following format:

```json
{
    "channelId": "your channel id here",
    "serverId": "your server id here"
}
```