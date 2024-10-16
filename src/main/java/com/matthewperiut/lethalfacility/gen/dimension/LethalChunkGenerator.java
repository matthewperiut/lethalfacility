package com.matthewperiut.lethalfacility.gen.dimension;

import com.matthewperiut.lethalfacility.block.LethalBlocks;
import com.matthewperiut.lethalfacility.gen.feature.RailingRoom;
import net.minecraft.client.gui.screen.LoadingDisplay;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;

import java.util.Random;

public class LethalChunkGenerator implements ChunkSource {
    private final World world;
    Random random;

    public LethalChunkGenerator(World world) {
        this.world = world;
        random = new Random();
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        return true;  // Mark the chunk as always loaded
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        return loadChunk(chunkX, chunkZ);
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        // Create a new chunk at the given coordinates
        FlattenedChunk chunk = new FlattenedChunk(world, chunkX, chunkZ);

        // Fill the chunk with stone for all y < 5
        /*
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(x, 25, z, LethalBlocks.platform.getDefaultState());
            }
        }*/

        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 16; x++) {
                chunk.setBlockState(x, y, 0, LethalBlocks.wall.getDefaultState());
                chunk.setBlockState(x, y, 8, LethalBlocks.wall.getDefaultState());
            }
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(0, y, z, LethalBlocks.wall.getDefaultState());
                chunk.setBlockState(8, y, z, LethalBlocks.wall.getDefaultState());
            }
        }



        chunk.populateHeightMap();
        chunk.populateBlockLight();
        return chunk;
    }

    @Override
    public void decorate(ChunkSource source, int x, int z) {
        int realx = x*16;
        int realz = z*16;

        // Generate 4 mini chunks within the major chunk
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                minichunk(realx + (i*8), realz + (j*8));
            }
        }
    }

    public void minichunk(int x, int z) {
        world.setBlockState(x + 4, 28, z + 4, LethalBlocks.invisible_light.getDefaultState());
        if (random.nextInt() % 20 == 0) {
            world.setBlockState(x + 4, 23, z + 4, LethalBlocks.fan_block.getDefaultState());
        }
        world.setBlockState(x + 4, 26, z, LethalBlocks.unlocked_door.getDefaultState());
        world.setBlockState(x + 4, 27, z, LethalBlocks.unlocked_door.getDefaultState());
        world.setBlockMetaWithoutNotifyingNeighbors(x+4, 26, z, 1);
        world.setBlockMetaWithoutNotifyingNeighbors(x+4, 27, z, 9);
        world.setBlockState(x, 26, z + 4, LethalBlocks.unlocked_door.getDefaultState());
        world.setBlockState(x, 27, z + 4, LethalBlocks.unlocked_door.getDefaultState());
        world.setBlockMetaWithoutNotifyingNeighbors(x, 26, z + 4, 0);
        world.setBlockMetaWithoutNotifyingNeighbors(x, 27, z + 4, 8);

        (new RailingRoom()).construct(world, x, 25, z, true, true, true, true);
    }

    @Override
    public boolean save(boolean saveEntities, LoadingDisplay display) {
        return true;
    }

    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String getDebugInfo() {
        return "LethalChunkSource";
    }
}
