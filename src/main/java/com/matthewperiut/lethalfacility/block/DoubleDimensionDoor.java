package com.matthewperiut.lethalfacility.block;

import com.matthewperiut.lethalfacility.gen.dimension.LethalPortalForcer;
import io.github.kydzombie.voxelshapes.api.HasVoxelShape;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Property;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.dimension.DimensionHelper;

import java.util.Random;

public class DoubleDimensionDoor extends TemplateBlock implements HasVoxelShape
{
    // 0 -> bottom left
    // 1 -> bottom middle
    // 2 -> bottom right
    // 3 -> top left
    // 4 -> top middle
    // 5 -> top right
    public static final Property<Integer> LOCATION = IntProperty.of("location", 0, 5);
    public DoubleDimensionDoor(Identifier identifier) {
        super(identifier, Material.METAL);
        setHardness(60000000.F);
        setResistance(6000000.0F);
        setDefaultState(getDefaultState().with(LOCATION, 1));
    }

    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (player.dimensionId != DimensionRegistry.INSTANCE.get(Identifier.of("lethalfacility:lethal")).getLegacyID()) {
            DimensionHelper.switchDimension(player, Identifier.of("lethalfacility:lethal"), 1, new LethalPortalForcer());
        } else {
            DimensionHelper.switchDimension(player, Identifier.of("minecraft:overworld"), 1, new LethalPortalForcer());
        }
        return true;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        if (world.getBlockState(x,y,z).get(LOCATION) == 1) {
            world.setBlockState(x-1,y,z,getDefaultState().with(LOCATION,0));
            // skipping 1 as this block is 1
            world.setBlockState(x+1,y,z,getDefaultState().with(LOCATION,2));
            world.setBlockState(x-1,y+1,z,getDefaultState().with(LOCATION,3));
            world.setBlockState(x,y+1,z,getDefaultState().with(LOCATION,4));
            world.setBlockState(x+1,y+1,z,getDefaultState().with(LOCATION,5));
        }
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LOCATION);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaque() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return 0;
    }

    @Override
    public Box[] getVoxelShape(World world, int x, int y, int z) {
        double offset1 = 0;
        double offset2 = 0;
        int loc = world.getBlockState(x,y,z).get(LOCATION);
        if (loc == 0 || loc == 3) {
            offset1 = 0.5;
        }
        if (loc == 2 || loc == 5) {
            offset2 = 0.5;
        }
        return new Box[]{ Box.create(x+offset1,y,z,x+1-offset2,y+1,z+0.125)};
    }
}
