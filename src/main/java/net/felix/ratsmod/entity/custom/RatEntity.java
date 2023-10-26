package net.felix.ratsmod.entity.custom;

import net.felix.ratsmod.entity.ModEntityTypes;
import net.felix.ratsmod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RatEntity extends Animal implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public RatEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(
                Items.CARROT, Items.APPLE, Blocks.DANDELION, ModItems.CHEESE.get()), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        /*
        Goals from Ratsmischief - find equivalent for these fabric goals
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new EatToHealGoal(this));
        this.goalSelector.addGoal(3, new PounceAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(4, new RatMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new PickupItemGoal());
        this.goalSelector.addGoal(5, new BringItemToOwnerGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new FollowOwnerRatGoal(this, 1.0D, 20.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAroundGoal(this));
        this.targetSelector.addGoal(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.addGoal(2, new AttackWithOwnerGoal(this));
        this.targetSelector.addGoal(3, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.addGoal(4, new TargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.addGoal(8, new ChaseForFunGoal<>(this, CatEntity.class, true));
        this.targetSelector.addGoal(8, new UniversalAngerGoal<>(this, true));
         */
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.run", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.flat", true));
        return PlayState.CONTINUE;
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
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return ModEntityTypes.RAT.get().create(level);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.RABBIT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

}
