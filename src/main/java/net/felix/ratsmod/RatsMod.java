package net.felix.ratsmod;

import com.mojang.logging.LogUtils;
import net.felix.ratsmod.block.ModBlocks;
import net.felix.ratsmod.entity.ModEntityTypes;
import net.felix.ratsmod.entity.client.RatRenderer;
import net.felix.ratsmod.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RatsMod.MOD_ID)
public class RatsMod {
    public static final String MOD_ID = "ratsmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RatsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

//        Register Mod Classes
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.RAT.get(), RatRenderer::new);
        }
    }
}
