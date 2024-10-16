package com.matthewperiut.lethalfacility.gen.feature;

import com.matthewperiut.lethalfacility.block.LethalBlocks;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

import static com.matthewperiut.lethalfacility.block.LethalBlocks.railing;
import static com.matthewperiut.lethalfacility.block.Railing.CORNER;
import static net.modificationstation.stationapi.api.state.property.Properties.HORIZONTAL_FACING;
import static net.modificationstation.stationapi.api.util.math.Direction.*;

public class RailingRoom {
    private World world;
    private int x;
    private int y;
    private int z;

    public void construct(World world, int x, int y, int z, boolean posX, boolean negX, boolean posZ, boolean negZ) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        // Construct corners
        if (posX && posZ)
            CornerXZ();
        else if (posX) {
            world.setBlockState(x + 5, y + 1, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, EAST));
            world.setBlockState(x + 5, y, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, EAST));
        } else if (posZ) {
            world.setBlockState(x + 5, y + 1, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, NORTH));
            world.setBlockState(x + 5, y, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, NORTH));
        }

        if (posX && negZ)
            CornerXz();
        else if (posX) {
            world.setBlockState(x + 5, y + 1, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, WEST));
            world.setBlockState(x + 5, y, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, WEST));
        } else if (negZ) {
            world.setBlockState(x + 5, y + 1, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, NORTH));
            world.setBlockState(x + 5, y, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, NORTH));
        }

        if (negX && posZ)
            CornerxZ();
        else if (negX) {
            world.setBlockState(x + 3, y + 1, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, EAST));
            world.setBlockState(x + 3, y, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, EAST));
        } else if (posZ) {
            world.setBlockState(x + 3, y + 1, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH));
            world.setBlockState(x + 3, y, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH));
        }

        if (negX && negZ)
            Cornerxz();
        else if (negX) {
            world.setBlockState(x + 3, y + 1, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, WEST));
            world.setBlockState(x + 3, y, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, WEST));
        } else if (negZ) {
            world.setBlockState(x + 3, y + 1, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH));
            world.setBlockState(x + 3, y, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH));
        }

        // Adjusted conditions for side railings
        if (!posZ && (posX || negX)) {
            world.setBlockState(x + 4, y, z + 5, getPosZ());
            world.setBlockState(x + 4, y + 1, z + 5, getPosZ());
        }
        if (!posX && (posZ || negZ)) {
            world.setBlockState(x + 5, y, z + 4, getPosX());
            world.setBlockState(x + 5, y + 1, z + 4, getPosX());
        }
        if (!negZ && (posX || negX)) {
            world.setBlockState(x + 4, y, z + 3, getNegZ());
            world.setBlockState(x + 4, y + 1, z + 3, getNegZ());
        }
        if (!negX && (posZ || negZ)) {
            world.setBlockState(x + 3, y, z + 4, getNegX());
            world.setBlockState(x + 3, y + 1, z + 4, getNegX());
        }

        if (!posZ) {
            world.setBlockState(x+4, y , z+5, getPosZ());
            world.setBlockState(x+4, y+1 , z+5, getPosZ());
        } else {
            world.setBlockState(x+5, y, z+7, getPosX());
            world.setBlockState(x+5, y+1, z+7, getPosX());
            world.setBlockState(x+5, y, z+6, getPosX());
            world.setBlockState(x+5, y+1, z+6, getPosX());
            world.setBlockState(x+3, y, z+7, getNegX());
            world.setBlockState(x+3, y+1, z+7, getNegX());
            world.setBlockState(x+3, y, z+6, getNegX());
            world.setBlockState(x+3, y+1, z+6, getNegX());
        }
        if (!posX) {
            world.setBlockState(x+5, y , z+4, getPosX());
            world.setBlockState(x+5, y+1 , z+4, getPosX());
        } else {
            world.setBlockState(x+7, y, z+3,  getNegZ());
            world.setBlockState(x+7, y+1, z+3,  getNegZ());
            world.setBlockState(x+6, y, z+3,  getNegZ());
            world.setBlockState(x+6, y+1, z+3,  getNegZ());
            world.setBlockState(x+7, y, z+5,  getPosZ());
            world.setBlockState(x+7, y+1, z+5,  getPosZ());
            world.setBlockState(x+6, y, z+5,  getPosZ());
            world.setBlockState(x+6, y+1, z+5,  getPosZ());
        }
        if (!negZ) {
            world.setBlockState(x+4, y , z+3, getNegZ());
            world.setBlockState(x+4, y+1 , z+3, getNegZ());
        } else {
            world.setBlockState(x+3, y , z+1, getNegX());
            world.setBlockState(x+3, y+1 , z+1, getNegX());
            world.setBlockState(x+3, y , z+2, getNegX());
            world.setBlockState(x+3, y+1 , z+2, getNegX());
            world.setBlockState(x+5, y , z+1, getPosX());
            world.setBlockState(x+5, y+1 , z+1, getPosX());
            world.setBlockState(x+5, y , z+2, getPosX());
            world.setBlockState(x+5, y+1 , z+2, getPosX());

        }
        if (!negX) {
            world.setBlockState(x+3, y , z+4, getNegX());
            world.setBlockState(x+3, y+1 , z+4, getNegX());
        } else {
            world.setBlockState(x+1, y , z+3, getNegZ());
            world.setBlockState(x+1, y+1 , z+3, getNegZ());
            world.setBlockState(x+2, y , z+3, getNegZ());
            world.setBlockState(x+2, y+1 , z+3, getNegZ());
            world.setBlockState(x+1, y , z+5, getPosZ());
            world.setBlockState(x+1, y+1 , z+5, getPosZ());
            world.setBlockState(x+2, y , z+5, getPosZ());
            world.setBlockState(x+2, y+1 , z+5, getPosZ());
        }

        // Place the central platform block
        world.setBlockWithoutNotifyingNeighbors(x + 4, y, z + 4, LethalBlocks.platform.id);

        // Extend platforms in open directions
        for (int i = 1; i < 4; i++) {
            if (posX)
                world.setBlockWithoutNotifyingNeighbors(x + 4 + i, y, z + 4, LethalBlocks.platform.id);
            if (negX)
                world.setBlockWithoutNotifyingNeighbors(x + 4 - i, y, z + 4, LethalBlocks.platform.id);
            if (posZ)
                world.setBlockWithoutNotifyingNeighbors(x + 4, y, z + 4 + i, LethalBlocks.platform.id);
            if (negZ)
                world.setBlockWithoutNotifyingNeighbors(x + 4, y, z + 4 - i, LethalBlocks.platform.id);
        }
    }

    private void CornerXZ() {
        world.setBlockState(x + 5, y + 1, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, NORTH).with(CORNER, true));
        world.setBlockState(x + 5, y, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, NORTH).with(CORNER, true));
    }

    private void CornerXz() {
        world.setBlockState(x + 5, y + 1, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, WEST).with(CORNER, true));
        world.setBlockState(x + 5, y, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, WEST).with(CORNER, true));
    }

    private void CornerxZ() {
        world.setBlockState(x + 3, y + 1, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, EAST).with(CORNER, true));
        world.setBlockState(x + 3, y, z + 5, railing.getDefaultState().with(HORIZONTAL_FACING, EAST).with(CORNER, true));
    }

    private void Cornerxz() {
        world.setBlockState(x + 3, y + 1, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH).with(CORNER, true));
        world.setBlockState(x + 3, y, z + 3, railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH).with(CORNER, true));
    }

    private BlockState getPosX() {
        return railing.getDefaultState().with(HORIZONTAL_FACING, NORTH);
    }

    private BlockState getNegX() {
        return railing.getDefaultState().with(HORIZONTAL_FACING, SOUTH);
    }

    private BlockState getPosZ() {
        return railing.getDefaultState().with(HORIZONTAL_FACING, EAST);
    }

    private BlockState getNegZ() {
        return railing.getDefaultState().with(HORIZONTAL_FACING, WEST);
    }
}
