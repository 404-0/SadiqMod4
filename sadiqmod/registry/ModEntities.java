package com.sadiqmod.registry;

import com.sadiqmod.SadiqMod;
import com.sadiqmod.entity.SadiqEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SadiqMod.MODID);

    public static final RegistryObject<EntityType<SadiqEntity>> SADIQ =
            ENTITY_TYPES.register("sadiq",
                    () -> EntityType.Builder.<SadiqEntity>of(SadiqEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .clientTrackingRange(8)
                            .build("sadiq"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
