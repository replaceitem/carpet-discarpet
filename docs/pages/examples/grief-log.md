Logs the opening of containers and tnt placement to a log channel

```sc title="grief_log.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

global_log = dc_channel_from_id('789877625497190440');

__on_player_interacts_with_block(player, hand, block, face, hitvec) -> (
    if((['chest','barrel']~block != null) || (block~'shulker_box' != null), //warning when player opens chest/barrel/shulkerbox
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str('%s opened %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);
__on_player_places_block(player, item_tuple, hand, block) -> (
    if(block == 'tnt', //warning when player places tnt
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str(':warning: %s placed %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);
```