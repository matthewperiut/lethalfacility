package com.matthewperiut.lethalfacility.block;

import com.matthewperiut.lethalfacility.client.LethalTextures;
import com.matthewperiut.lethalfacility.gen.dimension.LethalDimension;
import com.matthewperiut.lethalfacility.gen.dimension.LethalDimensions;
import com.matthewperiut.lethalfacility.gen.dimension.LethalPortalForcer;
import com.matthewperiut.lethalfacility.item.LethalItems;
import com.matthewperiut.retrocommands.dimension.BareTravelAgent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.template.block.TemplateDoorBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.dimension.DimensionHelper;

import java.util.Random;

public class DimensionDoor extends LockedDoor
{
    public DimensionDoor(Identifier identifier) {
        super(identifier, Material.METAL, true);
        setHardness(60000000.F);
        setResistance(6000000.0F);
    }

    @Override
    public int getTexture(int side, int meta) {
        if (meta >= 8) {
            return LethalTextures.door_top;
        } else {
            return LethalTextures.door_bottom;
        }
    }

    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (player.dimensionId != DimensionRegistry.INSTANCE.get(Identifier.of("lethalfacility:lethal")).getLegacyID()) {
            DimensionHelper.switchDimension(player, Identifier.of("lethalfacility:lethal"), 1, new LethalPortalForcer());
        } else {
            DimensionHelper.switchDimension(player, Identifier.of("minecraft:overworld"), 1, new LethalPortalForcer());
        }
        return true;
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
}
