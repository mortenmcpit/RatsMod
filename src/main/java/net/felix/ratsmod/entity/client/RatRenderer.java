package net.felix.ratsmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RatRenderer extends GeoEntityRenderer<RatEntity> {
    public RatRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RatModel());
        this.shadowRadius = 0.3f;

    }

    @Override
    public ResourceLocation getTextureLocation(RatEntity animatable) {
        return new ResourceLocation(RatsMod.MOD_ID, "textures/entity/rat.png");
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
