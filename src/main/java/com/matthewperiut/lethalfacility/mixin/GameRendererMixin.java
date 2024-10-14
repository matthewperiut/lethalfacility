package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.gen.dimension.LethalDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.matthewperiut.lethalfacility.LethalFacility.see_override;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow private float viewDistance;

    @Shadow private Minecraft client;

    @Inject(method = "applyFog", at = @At("HEAD"))
    void startApplyFog(int tickDelta, float par2, CallbackInfo ci) {
        if (client.player.dimensionId == DimensionRegistry.INSTANCE.getLegacyId(LethalDimensions.LETHAL).getAsInt() && !see_override) {
            viewDistance = 16.f;
        }
    }
}
