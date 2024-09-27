This script changes the bots
activity and status to show the currently online player list.
When no players are online, the bot will show as idle,
and show `Playing with nobody` in the activity.

```sc title="activity.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

__on_tick() -> (
    // consider Discord's rate limit (only execute every 30 seconds)
    if (tick_time() % (20 * 30) == 0,
        // are players online?
        if (length(player('all')) != 0, ( 
            // if so, display list of players
            dc_set_activity('playing','with ' + join(', ',player('all')));
            // then set bot's status to online
            dc_set_status('online'); 
        ), (
            // if not, display that nobody is online
            dc_set_activity('playing','with nobody');
            // then set bot's status to idle
            dc_set_status('idle'); 
        ));
    );
);
```