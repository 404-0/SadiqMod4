package com.sadiqmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sadiqmod.entity.SadiqEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SadiqModel extends HumanoidModel<SadiqEntity> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("sadiqmod", "sadiq"), "main");

    public SadiqModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(SadiqEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // Dance animation when entity is dancing
        if (entity.isDancing()) {
            float speed = ageInTicks * 0.3F;
            this.leftArm.xRot = (float) Math.sin(speed) * 1.0F;
            this.rightArm.xRot = (float) -Math.sin(speed) * 1.0F;
            this.leftLeg.xRot = (float) Math.cos(speed) * 0.8F;
            this.rightLeg.xRot = (float) -Math.cos(speed) * 0.8F;
            this.head.yRot = (float) Math.sin(speed * 0.5F) * 0.5F;
            this.body.yRot = (float) Math.sin(speed * 0.3F) * 0.2F;
        }
    }
}
