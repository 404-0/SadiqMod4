package com.sadiqmod.registry;

import com.sadiqmod.SadiqMod;
import com.sadiqmod.item.AmbaButtonItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SadiqMod.MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SadiqMod.MODID);

    public static final RegistryObject<Item> AMBA_BUTTON =
            ITEMS.register("amba_button", () -> new AmbaButtonItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<CreativeModeTab> SADIQ_TAB =
            CREATIVE_TABS.register("sadiq_tab", () -> CreativeModeTab.builder()
                    .title(Component.literal("SadiqMod"))
                    .icon(() -> AMBA_BUTTON.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        output.accept(AMBA_BUTTON.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
    }
}
