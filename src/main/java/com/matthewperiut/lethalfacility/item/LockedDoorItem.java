package com.matthewperiut.lethalfacility.item;

import com.matthewperiut.lethalfacility.block.LethalBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateDoorItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class LockedDoorItem extends TemplateDoorItem
{
    boolean locked = false;
    public LockedDoorItem(Identifier identifier, Material arg, boolean locked) {
        super(identifier, arg);
        this.locked = locked;
    }

    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if (side != 1) {
            return false;
        } else {
            ++y;
            Block door;
            if (locked) {
                door = LethalBlocks.locked_door;
            } else {
                door = LethalBlocks.unlocked_door;
            }

            if (!door.canPlaceAt(world, x, y, z)) {
                return false;
            } else {
                int var9 = MathHelper.floor((double)((user.yaw + 180.0F) * 4.0F / 360.0F) - 0.5) & 3;
                byte var10 = 0;
                byte var11 = 0;
                if (var9 == 0) {
                    var11 = 1;
                }

                if (var9 == 1) {
                    var10 = -1;
                }

                if (var9 == 2) {
                    var11 = -1;
                }

                if (var9 == 3) {
                    var10 = 1;
                }

                int var12 = (world.shouldSuffocate(x - var10, y, z - var11) ? 1 : 0) + (world.shouldSuffocate(x - var10, y + 1, z - var11) ? 1 : 0);
                int var13 = (world.shouldSuffocate(x + var10, y, z + var11) ? 1 : 0) + (world.shouldSuffocate(x + var10, y + 1, z + var11) ? 1 : 0);
                boolean var14 = world.getBlockId(x - var10, y, z - var11) == door.id || world.getBlockId(x - var10, y + 1, z - var11) == door.id;
                boolean var15 = world.getBlockId(x + var10, y, z + var11) == door.id || world.getBlockId(x + var10, y + 1, z + var11) == door.id;
                boolean var16 = false;
                if (var14 && !var15) {
                    var16 = true;
                } else if (var13 > var12) {
                    var16 = true;
                }

                if (var16) {
                    var9 = var9 - 1 & 3;
                    var9 += 4;
                }

                world.pauseTicking = true;
                world.setBlock(x, y, z, door.id, var9);
                world.setBlock(x, y + 1, z, door.id, var9 + 8);
                world.pauseTicking = false;
                world.notifyNeighbors(x, y, z, door.id);
                world.notifyNeighbors(x, y + 1, z, door.id);
                --stack.count;
                return true;
            }
        }
    }
}
