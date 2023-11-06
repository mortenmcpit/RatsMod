package net.felix.ratsmod.block.custom;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class CheeseCauldronBlock extends AbstractCauldronBlock {
    public CheeseCauldronBlock(Properties pProperties, Map<Item, CauldronInteraction> pInteractions) {
        super(pProperties, pInteractions);
    }

    @Override
    public boolean isFull(BlockState pState) {
        return false;
    }
}
