package com.matthewperiut.lethalfacility.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MaskedEntity extends LivingEntity {
    public MaskedEntity(World world) {
        super(world);
        if (world.players.size() < 1) {

        } else {
            PlayerEntity p = (PlayerEntity) world.players.get(random.nextInt(0, world.players.size()));
            skinUrl = p.skinUrl;
            capeUrl = p.capeUrl;
            this.modelName = "humanoid";
            this.texture = "/mob/char.png";
        }
    }
}
