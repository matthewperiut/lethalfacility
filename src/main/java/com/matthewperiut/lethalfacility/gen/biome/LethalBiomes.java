package com.matthewperiut.lethalfacility.gen.biome;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;

public class LethalBiomes {
    public static Biome LETHAL;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {
        LETHAL = BiomeBuilder.start("lethal")
                .grassAndLeavesColor(0x000000)
                .fogColor(0x000000)
                .precipitation(false)
                .snow(false)
                .build();

    }
}
