package com.sadiqmod.entity;

import com.sadiqmod.registry.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.List;

public class SadiqEntity extends Monster {

    private int phraseCooldown = 0;
    private int danceTicks = 0;
    private boolean isDancing = false;

    public SadiqEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        // Handle dancing
        if (isDancing) {
            danceTicks--;
            // Spin while dancing (client side effect via yRot)
            this.setYRot(this.getYRot() + 15.0F);
            if (danceTicks <= 0) {
                isDancing = false;
                // Re-enable AI after dancing
                this.setNoAi(false);
            }
            return;
        }

        // Check for nearby players every 40 ticks (~2 seconds)
        if (!this.level().isClientSide) {
            phraseCooldown--;
            if (phraseCooldown <= 0) {
                List<Player> nearbyPlayers = this.level().getEntitiesOfClass(
                        Player.class,
                        this.getBoundingBox().inflate(3.0D)
                );

                if (!nearbyPlayers.isEmpty()) {
                    playRandomPhrase();
                    phraseCooldown = 50; // 2.5 seconds cooldown
                }
            }
        }
    }

    private void playRandomPhrase() {
        if (this.level().isClientSide) return;

        int rand = this.random.nextInt(3);

        switch (rand) {
            case 0 -> {
                this.level().playSound(null, this.blockPosition(),
                        ModSounds.AMBA.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
                broadcastMessage("§e[صادق]: §fعمبة!!");
            }
            case 1 -> {
                this.level().playSound(null, this.blockPosition(),
                        ModSounds.ZOOT.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
                broadcastMessage("§e[صادق]: §fزوط!!");
            }
            case 2 -> {
                this.level().playSound(null, this.blockPosition(),
                        ModSounds.LABUBU.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
                broadcastMessage("§e[صادق]: §fلابوبو!!");
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && !this.level().isClientSide) {
            // Say "عمبة" when hit
            this.level().playSound(null, this.blockPosition(),
                    ModSounds.AMBA.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
            broadcastMessage("§c[صادق]: §fعمبة!! §7*يتألم*");
        }
        return result;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide) {
            broadcastMessage("§4§l✦ تمت الزطزطة بنجاح ✦");
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        // Give end rod → dance!
        if (heldItem.is(Items.END_ROD) && !isDancing) {
            if (!this.level().isClientSide) {
                isDancing = true;
                danceTicks = 100; // Dance for 5 seconds
                this.setNoAi(true); // Stop chasing player while dancing
                this.getNavigation().stop();

                // Consume the end rod
                if (!player.getAbilities().instabuild) {
                    heldItem.shrink(1);
                }

                broadcastMessage("§d[صادق]: §f*يرقص بسعادة* 🕺");

                // Play a happy sound while dancing
                this.level().playSound(null, this.blockPosition(),
                        ModSounds.LABUBU.get(), SoundSource.HOSTILE, 1.5F, 1.2F);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    private void broadcastMessage(String message) {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (ServerPlayer sp : serverLevel.players()) {
                sp.sendSystemMessage(Component.literal(message));
            }
        }
    }

    // Custom name above head
    @Override
    public Component getName() {
        return Component.literal("§6صادق");
    }

    public boolean isDancing() {
        return isDancing;
    }
}