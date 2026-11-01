package fixdol.mekanismelements.common.item.syringe;

import fixdol.mekanismelements.common.registries.MSItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class DrugSyringe extends Item {
    private static final String USE_COUNT_TAG = "UseCount";
    private final int maxUses;

    public DrugSyringe(Properties properties, int maxUses) {
        super(properties.stacksTo(1));
        this.maxUses = maxUses;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        player.hurtOrSimulate(player.damageSources().magic(), 1);
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            int useCount = getUseCount(itemStack);

            if (useCount < maxUses) {
                setUseCount(itemStack, useCount + 1);
                applyEffect(level, player, itemStack);
                //level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                ItemStack filledSyringe = getEmptySyringe();
                player.setItemInHand(hand, filledSyringe);
                //level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = target.level();
        if (!level.isClientSide()) {
            int useCount = getUseCount(stack);

            if (useCount < maxUses) {
                setUseCount(stack, useCount + 1);
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
        super.hurtEnemy(stack, target, attacker);
    }

    private static int getUseCount(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        return customData == null ? 0 : customData.copyTag().getIntOr(USE_COUNT_TAG, 0);
    }

    private static void setUseCount(ItemStack stack, int useCount) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag tag = customData != null ? customData.copyTag() : new CompoundTag();
        tag.putInt(USE_COUNT_TAG, useCount);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        //Expose the use count to the item model definition (range_dispatch on custom_model_data) as replacement for the removed item properties
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of((float) useCount), List.of(), List.of(), List.of()));
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

    protected abstract Holder<MobEffect> getEffectType();

    protected abstract int getBaseDuration();

    protected abstract int getEffectAmplifier();
}
