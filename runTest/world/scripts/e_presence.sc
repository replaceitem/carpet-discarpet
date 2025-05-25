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

task(_() -> (
    // check whether player count changed every 10 seconds
    previous_count = null;
    while (true, (
        player_count = length(player('all'));
        if (previous_count != player_count,
            previous_count = player_count;
            update_activity();
        );
        sleep(10000);
    ));
));