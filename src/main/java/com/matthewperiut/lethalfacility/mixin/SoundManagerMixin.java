package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.gen.dimension.LethalDimensions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin {
    @Shadow private int timeUntilNextSong;

    @Shadow private SoundEntry music;

    @Shadow public abstract void playSound(String id, float volume, float pitch);

    @Shadow private GameOptions gameOptions;

    @Shadow private static boolean started;

    @Inject(method = "<init>", at = @At("TAIL"))
    void instant(CallbackInfo ci) {
        timeUntilNextSong = 1;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEntry;getSounds()Lnet/minecraft/client/sound/Sound;"))
    Sound change(SoundEntry instance) {
        Minecraft minecraft = ((Minecraft) FabricLoader.getInstance().getGameInstance());
        boolean lethal = minecraft.player.dimensionId == DimensionRegistry.INSTANCE.getLegacyId(LethalDimensions.LETHAL).getAsInt();
        for (int i = 0; i < 100; i++) {
            Sound s = music.getSounds();
            if (lethal) {
                if (s.id.contains("lethalfacility")) {
                    return s;
                }
            } else {
                if (!s.id.contains("lethalfacility")) {
                    return s;
                }
            }
        }
        return music.getSounds();
    }

}
