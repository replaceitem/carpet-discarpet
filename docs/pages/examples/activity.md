This script changes the bots
activity and status to show the currently online player list.
When no players are online, the bot will show as idle,
and show `Playing with nobody` in the activity.

```sc title="activity.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

__on_tick() -> (
    if(tick_time()%(20*30) == 0, // Consider discords rate limit (only execute every 30 seconds)
        if(length(player('all')) != 0, // are players online?
            dc_set_activity('playing','with ' + join(', ',player('all'))); //display list of players
            dc_set_status('online'); // status should be online
        ,
            dc_set_activity('playing','with nobody'); // alternative text if nobody is online
            dc_set_status('idle'); // set status to idle
        );
    );
);
```