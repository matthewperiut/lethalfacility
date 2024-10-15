package com.matthewperiut.lethalfacility.client.entity.model;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import pl.telvarost.mojangfixstationapi.mixinterface.ModelPartAccessor;

import static com.matthewperiut.lethalfacility.LethalFacility.mojangfix;

public class MaskedEntityModel extends BipedEntityModel {
    public ModelPart mask;

    public MaskedEntityModel() {
        this(0.0F);
    }

    public MaskedEntityModel(float dilation) {
        this(dilation, 0.0F);
    }

    public MaskedEntityModel(float dilation, float pivotOffsetY) {
        this.leftArmPose = false;
        this.rightArmPose = false;
        this.sneaking = false;
        this.cape = createModelPart(0, 0);
        this.cape.addCuboid(-5.0F, 0.0F, -1.0F, 10, 16, 1, dilation);
        this.ears = createModelPart(24, 0);
        this.ears.addCuboid(-3.0F, -6.0F, -1.0F, 6, 6, 1, dilation);
        this.head = createModelPart(0, 0);
        this.head.addCuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, dilation);
        this.head.setPivot(0.0F, 0.0F + pivotOffsetY, 0.0F);
        this.hat = createModelPart(32, 0);
        this.hat.addCuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, dilation + 0.5F);
        this.hat.setPivot(0.0F, 0.0F + pivotOffsetY, 0.0F);
        this.body = createModelPart(16, 16);
        this.body.addCuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, dilation);
        this.body.setPivot(0.0F, 0.0F + pivotOffsetY, 0.0F);
        this.rightArm = createModelPart(40, 16);
        this.rightArm.addCuboid(-3.0F, -2.0F, -2.0F, 4, 12, 4, dilation);
        this.rightArm.setPivot(-5.0F, 2.0F + pivotOffsetY, 0.0F);
        this.leftArm = createModelPart(40, 16);
        this.leftArm.mirror = true;
        this.leftArm.addCuboid(-1.0F, -2.0F, -2.0F, 4, 12, 4, dilation);
        this.leftArm.setPivot(5.0F, 2.0F + pivotOffsetY, 0.0F);
        this.rightLeg = createModelPart(0, 16);
        this.rightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, dilation);
        this.rightLeg.setPivot(-2.0F, 12.0F + pivotOffsetY, 0.0F);
        this.leftLeg = createModelPart(0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, dilation);
        this.leftLeg.setPivot(2.0F, 12.0F + pivotOffsetY, 0.0F);
        this.mask = new ModelPart(32, 0);
        this.mask.addCuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, dilation + 1.F);
        this.mask.setPivot(0.0F, 0.0F + pivotOffsetY, 0.0F);
    }

    private ModelPart createModelPart(int x, int y) {
        ModelPart modelRenderer = new ModelPart(x, y);
        if (mojangfix) {
            ((ModelPartAccessor) modelRenderer).setTextureHeight(64);
        }
        return modelRenderer;
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        super.setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
        this.mask.yaw = this.head.yaw;
        this.mask.pitch = this.head.pitch;
    }

    public void renderMore(float scale) {
        mask.render(scale);
    }
}
