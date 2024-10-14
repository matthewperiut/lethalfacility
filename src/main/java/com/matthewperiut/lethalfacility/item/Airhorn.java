package com.matthewperiut.lethalfacility.item;

import com.matthewperiut.lethalfacility.api.ICooldown;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Airhorn extends TemplateItem {
    public Airhorn(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (((ICooldown) user).getCooldown() == 0) {
            ((ICooldown) user).setCooldown(20);
            world.worldEvent(69000, (int) user.x, (int) user.y, (int) user.z, 0);
        }
        return super.use(stack, world, user);
    }
}
