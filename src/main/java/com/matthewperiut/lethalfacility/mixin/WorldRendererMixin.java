package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.gen.dimension.LethalDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.lethalfacility.LethalFacility.see_override;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow private Minecraft client;

    @Shadow private World world;

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void renderSky(float par1, CallbackInfo ci) {
        if (client.player.dimensionId == DimensionRegistry.INSTANCE.getLegacyId(LethalDimensions.LETHAL).getAsInt() && !see_override) {
            ci.cancel();
        }
    }

    @Inject(method = "worldEvent", at = @At("HEAD"), cancellable = true)
    void playDoor(PlayerEntity player, int event, int x, int y, int z, int data, CallbackInfo ci) {
        if(event == 69000) {
            this.world.playSound(x,y,z, "lethalfacility:airhorn", 1.0F, 1.0F);
            ci.cancel();
        }
        else if(event == 69003) {
            this.world.playSound(x, y, z, "lethalfacility:doorshut", 1.0F, 1.0F);
            ci.cancel();
        } else if (event == 69004) {
            this.world.playSound(x, y, z, "lethalfacility:air", 1.0F, 1.0F);
            ci.cancel();
        }
    }
}
