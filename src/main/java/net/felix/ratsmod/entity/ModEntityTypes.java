package net.felix.ratsmod.entity;

import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RatsMod.MOD_ID);

    public static final RegistryObject<EntityType<RatEntity>> RAT = ENTITY_TYPES.register("rat",
            () -> EntityType.Builder.of(RatEntity::new, MobCategory.CREATURE)
                    .sized(0.4F, 0.7F)
                    .build(new ResourceLocation(RatsMod.MOD_ID, "rat").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}