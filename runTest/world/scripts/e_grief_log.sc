import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_log = dc_channel_from_id(env('channelId'));

__on_player_interacts_with_block(player, hand, block, face, hitvec) -> (
    // warn when player opens chest/barrel/shulkerbox
    if (['chest', 'barrel', 'shulker_box']~block != null, 
        task(_(outer(player), outer(block)) -> (
            dc_send_message(global_log,
                str(':warning: %s opened %s at %s in %s', player, block, pos(block), current_dimension() ));
        ));
    );
);
__on_player_places_block(player, item_tuple, hand, block) -> (
    // warn when player places tnt
    if (block == 'tnt', 
        task(_(outer(player), outer(block)) -> (
            dc_send_message(global_log,
                str(':warning: %s placed %s at %s in %s', player, block, pos(block), current_dimension() ));
        ));
    );
);