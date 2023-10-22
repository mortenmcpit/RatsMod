package net.felix.ratsmod.item;

import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.item.custom.CheeseItem;
import net.felix.ratsmod.item.custom.RatPouchItem;
import net.minecraft.world.item.Item;
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
            () -> new CheeseItem(new Item.Properties().tab(ModCreativeModTab.RATSMOD_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
