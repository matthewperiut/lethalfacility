package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.LethalFacility;
import com.matthewperiut.lethalfacility.gen.control.EntranceFileManager;
import net.minecraft.world.storage.AlphaWorldStorageSource;
import net.minecraft.world.storage.WorldStorage;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import net.modificationstation.stationapi.impl.world.storage.FlattenedWorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.*;

@Mixin(FlattenedWorldStorage.class)
public class FlattenedWorldStorageMixin extends AlphaWorldStorageSource {

    public FlattenedWorldStorageMixin(File file) {
        super(file);
    }

    @Inject(
            method = "method_1009",
            at = @At("HEAD")
    )
    public void method_1009(String saveName, boolean createPlayerDataDir, CallbackInfoReturnable<WorldStorage> cir) {
        EntranceFileManager.handle(String.valueOf(dir), saveName);
        LethalFacility.roomMap.createLayer(dir + "/" + saveName);
    }
}
