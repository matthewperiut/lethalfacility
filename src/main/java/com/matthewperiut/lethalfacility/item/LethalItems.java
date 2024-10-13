package com.matthewperiut.lethalfacility.item;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

public class LethalItems {

    @Entrypoint.Namespace
    private static Namespace NAMESPACE = null;

    public static Item key;
    public static Item locked_door;
    public static Item unlocked_door;

    @EventListener
    public static void registerItems(ItemRegistryEvent event) {
        key = new TemplateItem(NAMESPACE.id("key"))
                .setTranslationKey(NAMESPACE.id("key"))
                .setMaxCount(1);
        locked_door = new LockedDoorItem(NAMESPACE.id("locked_door_item"), Material.METAL, true)
                .setTranslationKey(NAMESPACE.id("locked_door_item"))
                .setTexturePosition(12, 2);
        unlocked_door = new LockedDoorItem(NAMESPACE.id("unlocked_door_item"), Material.METAL, false)
                .setTranslationKey(NAMESPACE.id("unlocked_door_item"))
                .setTexturePosition(12, 2);
    }
}
