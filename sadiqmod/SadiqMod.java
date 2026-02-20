package com.sadiqmod;

import com.mojang.logging.LogUtils;
import com.sadiqmod.client.renderer.SadiqModel;
import com.sadiqmod.client.renderer.SadiqRenderer;
import com.sadiqmod.registry.ModEntities;
import com.sadiqmod.registry.ModItems;
import com.sadiqmod.registry.ModSounds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SadiqMod.MODID)
public class SadiqMod {

    public static final String MODID = "sadiqmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SadiqMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("SadiqMod Loaded! عمبة");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.SADIQ.get(), SadiqRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(SadiqModel.LAYER_LOCATION, SadiqModel::createBodyLayer);
        }
    }
}
