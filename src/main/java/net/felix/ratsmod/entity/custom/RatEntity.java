package net.felix.ratsmod.entity.custom;

import net.felix.ratsmod.entity.ModEntityTypes;
import net.felix.ratsmod.entity.ai.EatToHealGoal;
import net.felix.ratsmod.entity.variant.RatVariant;
import net.felix.ratsmod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Objects;

public class RatEntity extends TamableAnimal implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(RatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(RatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(RatEntity.class, EntityDataSerializers.INT);

    public RatEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setTame(false);
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new EatToHealGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(Items.CARROT, Items.APPLE, Items.PUMPKIN_SEEDS, ModItems.CHEESE.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

    }
    // Code from Alex's Mobs
    /*public void tick() {
        super.tick();
        if (canTargetItem(this.getMainHandItem())) {
            onEatItem();
            if (this.getMainHandItem().hasCraftingRemainingItem()) {
                this.spawnAtLocation(this.getMainHandItem().getCraftingRemainingItem());
            }
            this.getMainHandItem().shrink(1);
        }
    }*/
    //

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.run", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.sit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isEating()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.eat", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.flat", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.STOP;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        RatEntity baby = ModEntityTypes.RAT.get().create(level);
        RatVariant variant = Util.getRandom(RatVariant.values(), this.random);
        assert baby != null;
        baby.setVariant(variant);

        return baby;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == ModItems.CHEESE.get();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.RABBIT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    /* TAMEABLE */
    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        Item itemForTaming = Items.PUMPKIN_SEEDS;

        if (isFood(itemstack)) {
            return super.mobInteract(player, hand);
        }

        if (item == itemForTaming && !isTame()) {
            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            } else {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (!ForgeEventFactory.onAnimalTame(this, player)) {
                    if (!this.level.isClientSide) {
                        super.tame(player);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        this.level.broadcastEntityEvent(this, (byte) 7);
                        setSitting(true);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        if (isTame() && !this.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            setSitting(!isSitting());
            return InteractionResult.SUCCESS;
        }
        // Eating code from Alex's Mobs
        if (isTame() && isFood(itemstack)
//                && !isFood(itemstack)
                && this.getHealth() < this.getMaxHealth()) {
            if (this.getMainHandItem().isEmpty()) {
                ItemStack copy = itemstack.copy();
                copy.setCount(1);
                this.setItemInHand(InteractionHand.MAIN_HAND, copy);
                this.onEatItem();
                if (itemstack.hasCraftingRemainingItem()) {
                    this.spawnAtLocation(itemstack.getCraftingRemainingItem());
                }
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            } else {
                this.gameEvent(GameEvent.EAT);
                this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
                this.heal(5);
            }
            this.usePlayerItem(player, hand, itemstack);
            return InteractionResult.SUCCESS;
        }
        //

        if (itemstack.getItem() == itemForTaming) {
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSitting(tag.getBoolean("isSitting"));
        setEating(tag.getBoolean("isEating"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isSitting", this.isSitting());
        tag.putBoolean("isEating", this.isEating());
        tag.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(EATING, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isEating() {
        return this.entityData.get(EATING);
    }

    public void setEating(boolean eating) {
        this.entityData.set(EATING, eating);
    }

    // Code from Alex's Mobs
    public void onEatItem() {
        this.heal(10);
        this.level.broadcastEntityEvent(this, (byte) 92);
        this.gameEvent(GameEvent.EAT);
        this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
    }


    //

    @Override
    public Team getTeam() {
        return super.getTeam();
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public void setTame(boolean pTamed) {
        super.setTame(pTamed);
        if (pTamed) {
            Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(10.0D);
            Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(0.2D);
            Objects.requireNonNull(getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.7D);
        } else {
            Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(5.0D);
            Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(0.1D);
            Objects.requireNonNull(getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.5D);
        }
    }

    /* VARIANTS */
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_,
                                        MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_,
                                        @Nullable CompoundTag p_146750_) {
        RatVariant variant = Util.getRandom(RatVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    public RatVariant getVariant() {
        return RatVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(RatVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}