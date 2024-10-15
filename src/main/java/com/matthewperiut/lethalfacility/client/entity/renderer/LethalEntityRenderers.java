package com.matthewperiut.lethalfacility.client.entity.renderer;

import com.matthewperiut.lethalfacility.client.entity.model.MaskedEntityModel;
import com.matthewperiut.lethalfacility.entity.MaskedEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class LethalEntityRenderers {
    @EventListener
    public static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(MaskedEntity.class, new MaskedEntityRenderer(new MaskedEntityModel(), 1.F));
    }
}
