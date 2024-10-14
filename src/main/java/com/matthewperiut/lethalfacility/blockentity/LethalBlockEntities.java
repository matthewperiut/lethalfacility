package com.matthewperiut.lethalfacility.blockentity;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;

public class LethalBlockEntities {
    @EventListener
    public static void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(FanBlockEntity.class, "lethal_fan_block_entity");
    }
}
