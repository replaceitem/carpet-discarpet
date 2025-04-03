Changes the bots activity and status to show how many players are online.


![Demo activity](/assets/examples/activity.png)


```sc title="activity.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

update_activity() -> (
    players = length(player('all'));

    // are there players online?
    if (players, (
        // if so, let's display amount of players
        // then set bot's status to online

        // use "player" if only 1 is online, else use plural "players"
        text = if (players == 1, 'player', 'players');

        dc_set_activity('playing', str('with %s %s', players, text));
        dc_set_status('online');
    ), (
        // if not, display that nobody is online
        // then set bot's status to idle

        dc_set_activity('playing','with nobody');
        dc_set_status('idle'); 
    ))
);

// display activity on startup
update_activity();

// update activity on occasion
// consider Discord's rate limit (only execute every 30 seconds, or 600 ticks)
__on_tick() -> (
    if (tick_time() % (30 * 20) == 0,
        update_activity();
    );
);
```