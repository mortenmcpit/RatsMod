package net.felix.ratsmod.block.custom;

import net.felix.ratsmod.block.util.MilkCauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class MilkCauldronBlock extends AbstractCauldronBlock {
    public MilkCauldronBlock(BlockBehaviour.Properties p_153498_) {
        super(p_153498_, MilkCauldronInteraction.MILK);
    }


    public MilkCauldronBlock(Properties pProperties, Map<Item, CauldronInteraction> pInteractions) {
        super(pProperties, pInteractions);
    }

    @Override
    public boolean isFull(BlockState pState) {
        return true;
    }
    
}
