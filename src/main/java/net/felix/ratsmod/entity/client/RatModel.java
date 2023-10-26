package net.felix.ratsmod.entity.client;

import com.google.common.collect.Maps;
import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.felix.ratsmod.entity.variant.RatVariant;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Map;

public class RatModel extends AnimatedGeoModel<RatEntity> {

    @Override
    public ResourceLocation getModelResource(RatEntity object) {
        return new ResourceLocation(RatsMod.MOD_ID, "geo/rat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RatEntity object) {
        return RatRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(RatEntity animatable) {
        return new ResourceLocation(RatsMod.MOD_ID, "animations/rat.animation.json");
    }
}