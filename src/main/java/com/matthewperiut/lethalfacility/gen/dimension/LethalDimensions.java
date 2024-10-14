package com.matthewperiut.lethalfacility.gen.dimension;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.dimension.Dimension;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class LethalDimensions {
    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();
    public static Identifier LETHAL;

    @EventListener
    private static void registerDimensions(DimensionRegistryEvent event) {
        DimensionRegistry r = event.registry;
        r.register(LETHAL = Identifier.of(MOD_ID, "lethal"), new DimensionContainer<Dimension>(LethalDimension::new));

    }
}
