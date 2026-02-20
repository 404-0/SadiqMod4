package com.sadiqmod.item;

import com.sadiqmod.registry.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;

public class AmbaButtonItem extends Item {

    private static final int COOLDOWN_TICKS = 60; // 3 second cooldown

    public AmbaButtonItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Play all 3 sounds one after another
            level.playSound(null, player.blockPosition(),
                    ModSounds.AMBA.get(), SoundSource.PLAYERS, 2.0F, 1.0F);

            // Broadcast all 3 phrases
            if (level instanceof ServerLevel serverLevel) {
                for (ServerPlayer sp : serverLevel.players()) {
                    sp.sendSystemMessage(Component.literal("§6§l[زر العمبة] §fعمبة! زوط! لابوبو! 🔊"));
                }

                // Schedule zoot after 1 second
                serverLevel.getServer().execute(() -> {
                    try { Thread.sleep(800); } catch (InterruptedException ignored) {}
                    level.playSound(null, player.blockPosition(),
                            ModSounds.ZOOT.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                });

                // Schedule labubu after 2 seconds
                serverLevel.getServer().execute(() -> {
                    try { Thread.sleep(1600); } catch (InterruptedException ignored) {}
                    level.playSound(null, player.blockPosition(),
                            ModSounds.LABUBU.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                });
            }

            // Add cooldown
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal("§bزر العمبة");
    }
}
