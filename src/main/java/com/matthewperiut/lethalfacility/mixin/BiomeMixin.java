package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.gen.biome.LethalBiomes;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.matthewperiut.lethalfacility.LethalFacility.see_override;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(float par1, CallbackInfoReturnable<Integer> cir) {
        if (this.equals(LethalBiomes.LETHAL) && !see_override)
            cir.setReturnValue(0x000000);
    }
}
