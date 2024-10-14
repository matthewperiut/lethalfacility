package com.matthewperiut.lethalfacility.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.sound.SoundManager;

public class FanBlockEntity extends BlockEntity {
    int count = 70;
    @Override
    public void tick() {
        count++;
        if (count > 69) {
            world.worldEvent(69004, x, y, z, 0);
            count = 0;
        }
    }
}
