package com.matthewperiut.lethalfacility.gen.dimension;

import com.matthewperiut.lethalfacility.LethalFacility;
import com.matthewperiut.retrocommands.command.Teleport;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.dimension.PortalForcer;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public class LethalPortalForcer extends PortalForcer {
    public boolean teleportToValidPortal(World world, Entity entity) {
        if (world.dimension instanceof LethalDimension) {
            entity.setPositionAndAnglesKeepPrevAngles(4.5, 28, 4.5, entity.yaw, 0.0F);
            entity.velocityX = entity.velocityY = entity.velocityZ = 0.0;
            return true;
        }
        else if (world.dimension instanceof OverworldDimension) {
            Vec3d exit = LethalFacility.worldLethalEntrancePos;
            entity.setPositionAndAnglesKeepPrevAngles(exit.x, exit.y, exit.z, entity.yaw, 0.0F);
            entity.velocityX = entity.velocityY = entity.velocityZ = 0.0;
            return true;
        }
        return true;
    }
}
