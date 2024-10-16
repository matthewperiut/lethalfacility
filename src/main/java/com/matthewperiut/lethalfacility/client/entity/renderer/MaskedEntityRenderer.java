package com.matthewperiut.lethalfacility.client.entity.renderer;

import com.matthewperiut.lethalfacility.client.entity.model.MaskedEntityModel;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;

public class MaskedEntityRenderer extends LivingEntityRenderer {
    MaskedEntityModel model;

    public MaskedEntityRenderer(MaskedEntityModel entityModel, float shadowRadius) {
        super(entityModel, shadowRadius);
        this.model = entityModel;
    }

    @Override
    protected void renderMore(LivingEntity entity, float tickDelta) {
        super.renderMore(entity, tickDelta);

        bindTexture("/assets/lethalfacility/entity/mask.png");
        ((MaskedEntityModel) model).renderMore(0.0625F);
    }
}
