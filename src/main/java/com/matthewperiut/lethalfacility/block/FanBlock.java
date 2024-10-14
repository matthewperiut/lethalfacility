package com.matthewperiut.lethalfacility.block;


import com.matthewperiut.lethalfacility.blockentity.FanBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

public class FanBlock extends TemplateBlockWithEntity {
    public FanBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new FanBlockEntity();
    }
}
