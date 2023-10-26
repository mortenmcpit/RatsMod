package net.felix.ratsmod.event;

import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.ModEntityTypes;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = RatsMod.MOD_ID)
    public class ForgeEvents {

        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
        }
    }

    @Mod.EventBusSubscriber(modid = RatsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.RAT.get(), RatEntity.createAttributes());
        }

    }
}