package net.felix.ratsmod.entity.client;

import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RatModel extends AnimatedGeoModel<RatEntity> {
    @Override
    public ResourceLocation getModelResource(RatEntity object) {
        return new ResourceLocation(RatsMod.MOD_ID, "geo/rat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RatEntity object) {
        return new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RatEntity animatable) {
        return new ResourceLocation(RatsMod.MOD_ID, "animations/rat.animation.json");
    }
}