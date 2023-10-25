package net.felix.ratsmod.item;

import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.block.ModBlocks;
import net.felix.ratsmod.fluid.ModFluids;
import net.felix.ratsmod.item.custom.CheeseItem;
import net.felix.ratsmod.item.custom.RatPouchItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RatsMod.MOD_ID);

    /*
    TODO:
        - placeholders right now
        - add logic to items
    */
    public static final RegistryObject<Item> LEATHER_RAT_POUCH = ITEMS.register("leather_rat_pouch",
            () -> new RatPouchItem(new Item.Properties().tab(ModCreativeModTab.RATSMOD_TAB)));
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new CheeseItem(new Item.Properties().tab(ModCreativeModTab.RATSMOD_TAB)
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(2f).build())));
    // Tutorial Items
    public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                    new Item.Properties().tab(ModCreativeModTab.RATSMOD_TAB)));
    public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.RATSMOD_TAB)
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(2f).build())));
    public static final RegistryObject<Item> SOAP_WATER_BUCKET = ITEMS.register("soap_water_bucket",
            () -> new BucketItem(ModFluids.SOURCE_SOAP_WATER,
                    new Item.Properties().tab(ModCreativeModTab.RATSMOD_TAB).craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
