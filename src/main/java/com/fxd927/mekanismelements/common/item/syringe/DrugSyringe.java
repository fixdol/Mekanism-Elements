package com.fxd927.mekanismelements.common.item.syringe;

import com.fxd927.mekanismelements.common.registries.MSItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class DrugSyringe extends Item {
    private static final String USE_COUNT_TAG = "UseCount";
    private final int maxUses;

    public DrugSyringe(Properties properties, int maxUses) {
        super(properties.stacksTo(1));
        this.maxUses = maxUses;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        player.hurt(player.damageSources().magic(),1);
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            CompoundTag tag = itemStack.getOrCreateTag();
            int useCount = tag.getInt(USE_COUNT_TAG);

            if (useCount < maxUses) {
                useCount++;
                tag.putInt(USE_COUNT_TAG, useCount);
                applyEffect(level, player, itemStack);
                //level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                ItemStack filledSyringe = getEmptySyringe();
                player.setItemInHand(hand, filledSyringe);
                //level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = target.getCommandSenderWorld();
        if (!level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            int useCount = tag.getInt(USE_COUNT_TAG);

            if (useCount < maxUses) {
                useCount++;
                tag.putInt(USE_COUNT_TAG, useCount);
                applyEffectToEntity(level, target, attacker);
                level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                ItemStack filledSyringe = getEmptySyringe();
                if (attacker instanceof Player player) {
                    player.setItemInHand(player.getUsedItemHand(), filledSyringe);
                } else {
                    stack.shrink(1);
                }
                level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    protected void applyEffect(Level level, Player player, ItemStack stack) {
        MobEffectInstance currentEffect = player.getEffect(getEffectType());
        int newDuration = getBaseDuration();
        if (currentEffect != null) {
            newDuration += currentEffect.getDuration();
        }
        player.addEffect(new MobEffectInstance(getEffectType(), newDuration, getEffectAmplifier()));
    }
    protected void applyEffectToEntity(Level level, LivingEntity target, LivingEntity attacker) {
        MobEffectInstance currentEffect = target.getEffect(getEffectType());
        int newDuration = getBaseDuration();
        if (currentEffect != null) {
            newDuration += currentEffect.getDuration();
        }
        target.addEffect(new MobEffectInstance(getEffectType(), newDuration, getEffectAmplifier()));
    }
    protected ItemStack getEmptySyringe() {
        return new ItemStack(MSItems.SYRINGE.get());
    }

    protected abstract MobEffect getEffectType();

    protected abstract int getBaseDuration();

    protected abstract int getEffectAmplifier();



    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        ItemProperties.register(this, new ResourceLocation("use_count"),
                (stack, level, entity, seed) -> {
                    if (stack.hasTag() && stack.getTag().contains(USE_COUNT_TAG)) {
                        return stack.getTag().getInt(USE_COUNT_TAG);
                    }
                    return 0;
                });
    }
}
