package com.matthewperiut.lethalfacility.block;

import com.matthewperiut.lethalfacility.client.LethalTextures;
import com.matthewperiut.lethalfacility.item.LethalItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateDoorBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class LockedDoor extends TemplateDoorBlock
{
    boolean needsKey;

    public LockedDoor(Identifier identifier, Material material, boolean needsKey) {
        super(identifier, material);
        this.needsKey = needsKey;
        if (needsKey) {
            setHardness(60000000.F);
            setResistance(6000000.0F);
        } else {
            setHardness(5.0F);
            setResistance(5.0F);
        }
    }

    @Override
    public int getTexture(int side, int meta) {
        if (meta >= 8) {
            return LethalTextures.door_top;
        } else {
            return LethalTextures.door_bottom;
        }
    }

    private void unlock(World world, int x, int y, int z) {
        if (world.getBlockId(x,y,z) == LethalBlocks.locked_door.id)
            world.setBlockWithoutNotifyingNeighbors(x, y , z, LethalBlocks.unlocked_door.id, world.getBlockMeta(x, y, z));
    }

    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        int meta = world.getBlockMeta(x, y, z);

        if (needsKey) {
            if (player.inventory.main[player.inventory.selectedSlot] != null && player.inventory.main[player.inventory.selectedSlot].itemId == LethalItems.key.id) {
                if ((meta & 8) != 0) {
                    if (world.getBlockId(x, y - 1, z) == this.id) {
                        for (int i = x - 1; i < x + 2; i++) {
                            for (int j = z - 1; j < z + 2; j++) {
                                unlock(world, i, y, j);
                                unlock(world, i, y - 1, j);
                            }
                        }
                    }
                } else {
                    if (world.getBlockId(x, y + 2, z) == this.id) {
                        for (int i = x - 1; i < x + 2; i++) {
                            for (int j = z - 1; j < z + 1; j++) {
                                unlock(world, i, y, j);
                                unlock(world, i, y + 1, j);
                            }
                        }
                    }
                }

                player.inventory.main[player.inventory.selectedSlot].count--;
                if (player.inventory.main[player.inventory.selectedSlot].count <= 0) {
                    player.inventory.main[player.inventory.selectedSlot] = null;
                }
            } else {
                player.sendMessage("This door is locked!");
            }
            return true;
        } else {
            if ((meta & 8) != 0) {
                if (world.getBlockId(x, y - 1, z) == this.id) {
                    this.onUse(world, x, y - 1, z, player);
                }

                return true;
            } else {
                if (world.getBlockId(x, y + 1, z) == this.id) {
                    world.setBlockMeta(x, y + 1, z, (meta ^ 4) + 8);
                }

                world.setBlockMeta(x, y, z, meta ^ 4);
                world.setBlocksDirty(x, y - 1, z, x, y, z);
                world.worldEvent(player, 69003, x, y, z, 0);
                return true;
            }
        }
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
        if ((blockMeta & 8) != 0) {
            return 0;
        } else {
            return LethalItems.unlocked_door.id;
        }
    }
}
