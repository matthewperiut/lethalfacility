package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.api.ICooldown;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements ICooldown {
    @Unique
    int cooldown = 0;

    @Override
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public void setCooldown(int c) {
        cooldown = c;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    void cooldown(CallbackInfo ci) {
        if (cooldown > 0) {
            cooldown--;
        }
    }
}
