package net.felix.ratsmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab {
    public static final CreativeModeTab RATSMOD_TAB = new CreativeModeTab("ratstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CHEESE.get());
        }
    };
}
