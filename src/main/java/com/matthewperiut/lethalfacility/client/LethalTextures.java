package com.matthewperiut.lethalfacility.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class LethalTextures {
    @Entrypoint.Namespace
    private static Namespace NAMESPACE = null;

    public static int door_bottom, door_top;
    @EventListener
    public static void registerTexture(TextureRegisterEvent event) {
        ExpandableAtlas terrain = Atlases.getTerrain();
        door_bottom = terrain.addTexture(NAMESPACE.id("block/door_bottom")).index;
        door_top = terrain.addTexture(NAMESPACE.id("block/door_top")).index;
    }
}
