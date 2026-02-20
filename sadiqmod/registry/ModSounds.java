package com.sadiqmod.registry;

import com.sadiqmod.SadiqMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SadiqMod.MODID);

    public static final RegistryObject<SoundEvent> AMBA =
            SOUND_EVENTS.register("amba",
                    () -> SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(SadiqMod.MODID, "amba")));

    public static final RegistryObject<SoundEvent> ZOOT =
            SOUND_EVENTS.register("zoot",
                    () -> SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(SadiqMod.MODID, "zoot")));

    public static final RegistryObject<SoundEvent> LABUBU =
            SOUND_EVENTS.register("labubu",
                    () -> SoundEvent.createVariableRangeEvent(
                            new ResourceLocation(SadiqMod.MODID, "labubu")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
