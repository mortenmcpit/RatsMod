package net.felix.ratsmod.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.felix.ratsmod.entity.variant.RatVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class RatRenderer extends GeoEntityRenderer<RatEntity> {
    public static final Map<RatVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(RatVariant.class), (p_114874_) -> {
                p_114874_.put(RatVariant.DEFAULT,
                        new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat/rat.png"));
                p_114874_.put(RatVariant.ALBINO,
                        new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat/albino.png"));
                p_114874_.put(RatVariant.GREY,
                        new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat/grey.png"));
                p_114874_.put(RatVariant.HUSKY,
                        new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat/husky.png"));
                p_114874_.put(RatVariant.LIGHT_BROWN,
                        new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat/light_brown.png"));

            });
    public RatRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RatModel());
        this.shadowRadius = 0.3f;

    }

    @Override
    public ResourceLocation getTextureLocation(RatEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public RenderType getRenderType(RatEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer,
                                    int packedLight, ResourceLocation texture) {
        if (animatable.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        } else {
            poseStack.scale(0.8f, 0.8f, 0.8f);
        }

        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}