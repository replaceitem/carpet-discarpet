import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

update_activity() -> (
    players = length(player('all'));
    
    // use "player" if only 1 is online, else use plural "players"
    players_suffix = if (players == 1, 'player', 'players');
    
    presence = {
        'status' -> if(players == 0, 'idle', 'online'),
        'activity_type' -> 'playing',
        'activity_name' -> if(players == 0, 'with nobody', str('with %s %s', players, players_suffix)),
    };
    
    dc_update_presence(presence);
);

// display & update occasionally every 30 seconds
task(_() -> (
    while (true, (
        update_activity();
        sleep(10000);
    ));
));