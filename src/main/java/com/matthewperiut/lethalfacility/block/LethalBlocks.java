package com.matthewperiut.lethalfacility.block;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;
import net.modificationstation.stationapi.api.util.Namespace;

public class LethalBlocks {
    @Entrypoint.Namespace
    private static Namespace NAMESPACE = null;

    public static Block hard_metal_ground;
    public static Block fan_block;
    public static Block invisible_light;
    public static Block wall;
    public static Block platform;
    public static Block double_dimension_door;
    public static Block dimension_door;
    public static Block locked_door;
    public static Block unlocked_door;
    public static Block railing;

    @EventListener
    public static void registerItems(ItemRegistryEvent event) {
        hard_metal_ground = new TemplateBlock(NAMESPACE.id("hard_metal_ground"), Material.METAL).setTranslationKey(NAMESPACE.id("hard_metal_ground")).setHardness(1.F);
        fan_block = new FanBlock(NAMESPACE.id("fan"), Material.METAL).setTranslationKey(NAMESPACE.id("fan"));
        invisible_light = new InvisibleLight(NAMESPACE.id("invisible_light"), Material.AIR).setLuminance(1.0F).setTranslationKey((NAMESPACE.id("invisible_light")));
        wall = new TemplateBlock(NAMESPACE.id("wall"), Material.METAL).setTranslationKey(NAMESPACE.id("wall")).setHardness(1);
        platform = new TemplateTranslucentBlock(NAMESPACE.id("platform"), 0, Material.METAL, false)
                .setTranslationKey(NAMESPACE.id("platform")).setHardness(1);
        double_dimension_door = new DoubleDimensionDoor(NAMESPACE.id("double_dimension_door")).setTranslationKey(NAMESPACE.id("double_dimension_door"));
        dimension_door = new DimensionDoor(NAMESPACE.id("dimension_door")).setTranslationKey(NAMESPACE.id("dimension_door"));
        locked_door = new LockedDoor(NAMESPACE.id("locked_door"), Material.METAL, true).setTranslationKey(NAMESPACE.id("locked_door"));
        unlocked_door = new LockedDoor(NAMESPACE.id("unlocked_door"), Material.METAL, false).setTranslationKey(NAMESPACE.id("unlocked_door"));
        railing = new Railing(NAMESPACE.id("railing"), Material.METAL).setTranslationKey(NAMESPACE.id("railing")).setHardness(1);
    }
}
