Discarpet adds exception types that can be caught using scarpets `try()` function.
The exception hierarchy is as follows:

* `exception` (Base scarpet exception)
  * `discord_exception` (Base discarpet exception)
    * `api_exception`
      * `missing_permission`
      * `rate_limit`
      * `bad_request`
    * `missing_intent`

All exceptions discarpet uses have `discord_exception` as the base exception type.
All exceptions that were returned from the discord API will be `api_exception`s.
All `api_exception`s have additional information in them that is returned from the discord api.

The exception value can be accessed like this in scarpet:
```sc title="Getting exception details"
try(
    dc_send_message(channel,message);
,
    print(_);
);
```
The format is as follows:
```sc title="Example exception value"
{
    'code' -> 10003,
    'body' -> { 'code' -> 10003, 'message' -> 'Unknown Channel'}
}
```

`code` is a discord status code according to [this list](https://discord.com/developers/docs/topics/opcodes-and-status-codes#json)
`message` is an error string also provided by the discord api response


`rate_limit` exceptions are rare, since javacord will queue requests to avoid rate limits,
but if far too many requests are sent, you might hit the limit anyway.