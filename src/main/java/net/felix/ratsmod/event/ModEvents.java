package net.felix.ratsmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.entity.ModEntityTypes;
import net.felix.ratsmod.entity.custom.RatEntity;
import net.felix.ratsmod.item.ModItems;
import net.felix.ratsmod.villager.ModVillagers;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = RatsMod.MOD_ID)
    public class ForgeEvents {

        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            if (event.getType() == VillagerProfession.TOOLSMITH) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.LEATHER_RAT_POUCH.get(), 1);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 2),
                        stack, 10, 8, 0.02F));
            }

            if (event.getType() == ModVillagers.CHEESE_MASTER.get()) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.BLUEBERRY.get(), 15);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 5),
                        stack, 10, 8, 0.02F));
            }
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