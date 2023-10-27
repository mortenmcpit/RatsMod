package net.felix.ratsmod.entity.ai;

import net.felix.ratsmod.entity.custom.RatEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.UseAnim;

public class EatToHealGoal extends Goal {
    public final RatEntity rat;
    public int eatingTicks;

    public EatToHealGoal(RatEntity rat) {
        this.rat = rat;
    }

    @Override
    public boolean canUse() {
        return ((this.rat.isFood(this.rat.getMainHandItem()) && this.rat.getHealth() < this.rat.getMaxHealth())
                || (this.rat.getMainHandItem().getItem() instanceof PotionItem
                && !(this.rat.getMainHandItem().getItem() instanceof ThrowablePotionItem)))
                && !this.rat.getMoveControl().hasWanted();
    }


    //    This logic is copy over from RatsMischief and modified to work in Forge
    @Override
    public void tick() {
        if (!this.rat.level.isClientSide && this.rat.isEating()) {
            if (!this.rat.getMoveControl().hasWanted()) {
                super.tick();

                this.eatingTicks--;

                if (this.eatingTicks % 4 == 0 && this.eatingTicks > 0) {
                    if (this.rat.getMainHandItem().getItem().getUseAnimation(this.rat.getMainHandItem()) == UseAnim.DRINK) {
                        this.rat.level.playSound(null, this.rat, this.rat.getMainHandItem().getItem().getDrinkingSound(), SoundSource.NEUTRAL, 0.01F, this.rat.getRandom().nextFloat() * 0.4F + 0.8F);
                    } else if (this.rat.getMainHandItem().getItem().getUseAnimation(this.rat.getMainHandItem()) == UseAnim.EAT) {
//                        ((ServerWorld) this.rat.level).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, this.rat.getMainHandItem()), this.rat.getX() + this.rat.getRandom().nextGaussian() / 20f, this.rat.getY() + this.rat.getRandom().nextGaussian() / 20f, this.rat.getZ() + this.rat.getRandom().nextGaussian() / 20f, 5, this.rat.getRandom().nextGaussian() / 20f, 0.2D + this.rat.getRandom().nextGaussian() / 20f, this.rat.getRandom().nextGaussian() / 20f, 0.025f);
                        this.rat.level.playSound(null, this.rat, this.rat.getMainHandItem().getItem().getEatingSound(), SoundSource.NEUTRAL, 0.01F, this.rat.getRandom().nextFloat() * 0.4F + 0.8F);
                    }
                }

                /*if (this.eatingTicks <= 0) {
                    if (this.rat.getMainHandItem().getItem() instanceof PotionItem) {
                        ItemStack stack = this.rat.getMainHandItem();

                        if (!this.rat.level.isClientSide) {
                            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);

                            for (StatusEffectInstance statusEffectInstance : list) {
                                if (statusEffectInstance.getEffectType().isInstant()) {
                                    statusEffectInstance.getEffectType().applyInstantEffect(this.rat, this.rat, this.rat, statusEffectInstance.getAmplifier(), 1.0D);
                                } else {
                                    this.rat.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                                }
                            }
                        }

                        stack.decrement(1);
                        this.rat.dropStack(new ItemStack(Items.GLASS_BOTTLE));
                    } else if (this.rat.getMainHandItem().getItem().isFood()) {
                        if (this.rat.getMainHandItem().getUseAction() == UseAnim.DRINK) {
                            this.rat.dropStack(new ItemStack(Items.GLASS_BOTTLE));
                        }
                        this.rat.heal((float) this.rat.getMainHandItem().getItem().getFoodComponent().getHunger());
                        this.rat.getMainHandItem().getItem().getFoodComponent().getStatusEffects().forEach(statusEffectInstanceFloatPair -> {
                            this.rat.addStatusEffect(statusEffectInstanceFloatPair.getFirst());
                        });
                        this.rat.getMainHandItem().getItem().finishUsing(this.rat.getMainHandItem(), this.rat.level, this.rat);
                    }

                    this.rat.setEating(false);
                }*/
            } else {
                this.rat.setEating(false);
            }
        }
    }

    @Override
    public void start() {
        this.rat.setEating(true);
        this.eatingTicks = 30;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.rat.isEating();
    }
}
