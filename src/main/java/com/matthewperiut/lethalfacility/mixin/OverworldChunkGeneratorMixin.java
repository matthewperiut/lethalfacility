package com.matthewperiut.lethalfacility.mixin;

import com.matthewperiut.lethalfacility.LethalFacility;
import com.matthewperiut.lethalfacility.util.StructureStorage;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.modificationstation.stationapi.api.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileWriter;
import java.io.IOException;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {

    @Shadow private World world;

    @Inject(method = "<init>", at = @At("TAIL"))
    void shouldMakeEntrance(World world, long seed, CallbackInfo ci) {

    }

    // Injecting into the 'decorate' method of OverworldChunkGenerator
    @Inject(method = "decorate", at = @At("TAIL"))
    public void onDecorate(ChunkSource source, int chunkX, int chunkZ, CallbackInfo ci) {
        if (!LethalFacility.worldLethalEntranceExists) {
            int startX = chunkX * 16;
            int startZ = chunkZ * 16;

            // Loop through the chunk to find the 6x9 area
            for (int x = startX; x <= startX + 16 - 9; x++) {
                for (int z = startZ; z <= startZ + 16 - 6; z++) {
                    int baseY = world.getTopY(x, z);

                    // Check for flat 6x9 area of grass blocks
                    if (isFlatGrassArea(world, x, baseY, z)) {
                        if (isSolidGround(world, x, baseY, z)) {
                            LethalFacility.worldLethalEntranceExists = true;
                            (new StructureStorage()).pasteWithoutNotify(world, x-4, baseY-4, z-4, "lethalfacility:entrance");
                            LethalFacility.worldLethalEntrancePos = new Vec3d((x-1), (baseY-2), (z-1.5));
                            try (FileWriter writer = new FileWriter(LethalFacility.worldLethalEntranceFilePath)) {
                                writer.write("true\n");
                                writer.write((x-1) + " " + (baseY-2) + " " + (z-1.5));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    // Helper method to check if there is a 6x9 area of grass blocks
    private boolean isFlatGrassArea(World world, int startX, int baseY, int startZ) {
        for (int x = startX; x < startX + 9; x++) {
            for (int z = startZ; z < startZ + 6; z++) {
                int currentY = world.getTopY(x, z);
                BlockPos pos = new BlockPos(x, currentY - 1, z);

                // Check if the area is flat and made of grass blocks
                if (currentY != baseY || world.getBlockState(pos).getBlock() != Block.GRASS_BLOCK) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if total area is solid
    private boolean isSolidGround(World world, int startX, int baseY, int startZ) {
        for (int x = 0; x < 12; x++) {
            for (int y = 1; y < 6; y++) {
                for (int z = 0; z < 10; z++) {
                    if (world.getBlockId(startX+x, baseY-y, startZ+z) == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
