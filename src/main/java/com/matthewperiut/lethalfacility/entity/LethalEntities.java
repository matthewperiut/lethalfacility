package com.matthewperiut.lethalfacility.entity;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;

public class LethalEntities {
    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(MaskedEntity.class, "Masked");
    }
}
