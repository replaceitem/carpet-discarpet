---
icon: material/comment-quote
---


Changes the bot's activity and status to show how many players are online.


![Demo activity](/assets/examples/activity.png)


```sc title="activity.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

update_activity() -> (
    players = length(player('all'));

    if (players != 0, (
        // use "player" if only 1 is online, else use plural "players"
        text = if (players == 1, 'player', 'players');

        dc_set_activity('playing', str('with %s %s', players, text));
        dc_set_status('online');
    ), (
        dc_set_activity('playing','with nobody');
        dc_set_status('idle'); 
    ));
);

// display & update occasionally every 30 seconds
task(_() -> (
    while (true, (
        update_activity();
        sleep(30000);
    ));
));
```