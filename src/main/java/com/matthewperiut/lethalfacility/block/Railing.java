package com.matthewperiut.lethalfacility.block;

import io.github.kydzombie.voxelshapes.api.HasVoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.state.property.Property;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Objects;

public class Railing extends TemplateTranslucentBlock implements HasVoxelShape {
    public static final Property<Boolean> CORNER = BooleanProperty.of("corner");

    public Railing(Identifier identifier, Material material) {
        super(identifier, 0, material, false);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(CORNER, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING).add(CORNER);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getHorizontalPlayerFacing();
        boolean s = Objects.requireNonNull(context.getPlayer()).isSneaking();
        return getDefaultState().with(Properties.HORIZONTAL_FACING, direction).with(CORNER, s);
    }

    World world;

    private Box getSingleShape(Direction d) {
            switch (d) {
                case NORTH:
                    return Box.createCached(0.F, 0.F, 0.F, 0.125F, 1.F, 1.F);
                case EAST:
                    return Box.createCached(0.F, 0.F, 0.F, 1.F, 1.F, 0.125F);
                case SOUTH:
                    return Box.createCached(0.875F, 0.F, 0.F, 1.F, 1.F, 1.F);
                case WEST:
                    return Box.createCached(0.F, 0.F, 0.875F, 1.F, 1.F, 1.F);
            }
        return Box.createCached(0,0,0,0,0,0);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public Box[] getVoxelShape(World world, int x, int y, int z) {
        BlockState bs = world.getBlockState(x,y,z);
        if (bs.get(CORNER)) {
            Direction dir = bs.get(Properties.HORIZONTAL_FACING);
            Box secondary = getSingleShape(dir.rotateClockwise(Direction.Axis.Y)).translate(x, y, z);
            switch(dir) {
                case EAST -> secondary = secondary.translate(0,0,0.0625).contract(0,0,0.0625);
                case WEST -> secondary = secondary.translate(0,0,-0.0625).contract(0,0,0.0625);
                case NORTH -> secondary = secondary.translate(0.0625, 0, 0).contract(0.0625,0,0);
                case SOUTH -> secondary = secondary.translate(-0.0625,0, 0).contract(0.0625,0,0);
            }

            return new Box[]{ getSingleShape(dir).translate(x, y, z),
                              secondary };
        } else {
            return new Box[]{ getSingleShape(bs.get(Properties.HORIZONTAL_FACING)).translate(x, y, z) };
        }
    }
}
