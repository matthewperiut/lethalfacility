package com.matthewperiut.lethalfacility.block;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;
import net.modificationstation.stationapi.api.util.Namespace;

public class LethalBlocks {
    @Entrypoint.Namespace
    private static Namespace NAMESPACE = null;

    public static Block platform;
    public static Block locked_door;
    public static Block unlocked_door;
    public static Block railing;

    @EventListener
    public static void registerItems(ItemRegistryEvent event) {
        platform = new TemplateTranslucentBlock(NAMESPACE.id("platform"), 0, Material.METAL, false)
                .setTranslationKey(NAMESPACE.id("platform")).setHardness(1);
        locked_door = new LockedDoor(NAMESPACE.id("locked_door"), Material.METAL, true).setTranslationKey(NAMESPACE.id("locked_door"));
        unlocked_door = new LockedDoor(NAMESPACE.id("unlocked_door"), Material.METAL, false).setTranslationKey(NAMESPACE.id("unlocked_door"));
        railing = new Railing(NAMESPACE.id("railing"), Material.METAL).setTranslationKey(NAMESPACE.id("railing")).setHardness(1);
    }
}
