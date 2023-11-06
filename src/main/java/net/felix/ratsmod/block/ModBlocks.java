package net.felix.ratsmod.block;

import net.felix.ratsmod.RatsMod;
import net.felix.ratsmod.block.custom.MilkCauldronBlock;
import net.felix.ratsmod.block.util.MilkCauldronInteraction;
import net.felix.ratsmod.item.ModCreativeModTab;
import net.felix.ratsmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks extends Blocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RatsMod.MOD_ID);
    public static final RegistryObject<Block> CHEESE_BLOCK = registerBlock("cheese_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.CACTUS)), ModCreativeModTab.RATSMOD_TAB);
    public static final RegistryObject<Block> MILK_CAULDRON = BLOCKS.register("milk_cauldron",
            () -> new MilkCauldronBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON), MilkCauldronInteraction.MILK));


    // helper methods
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
