package com.matthewperiut.lethalfacility.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class InvisibleLight extends TemplateBlock  {
    public InvisibleLight(Identifier identifier, Material material) {
        super(identifier, material);
        setBoundingBox(0,0,0,0,0,0);
    }

    @Override
    public boolean hasCollision() {
        return false;
    }

    @Override
    public void onSteppedOn(World world, int x, int y, int z, Entity entity) {
        world.setBlock(x,y,z,0);
    }
}
