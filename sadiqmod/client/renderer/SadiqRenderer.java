package com.sadiqmod.client.renderer;

import com.sadiqmod.entity.SadiqEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SadiqRenderer extends HumanoidMobRenderer<SadiqEntity, SadiqModel> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("sadiqmod", "textures/entity/sadiq/sadiq.png");

    public SadiqRenderer(EntityRendererProvider.Context context) {
        super(context, new SadiqModel(
                context.bakeLayer(SadiqModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SadiqEntity entity) {
        return TEXTURE;
    }
}
