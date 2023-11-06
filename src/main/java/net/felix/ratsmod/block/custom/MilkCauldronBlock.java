package net.felix.ratsmod.block.custom;

import net.felix.ratsmod.block.ModBlocks;
import net.felix.ratsmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class MilkCauldronBlock extends AbstractCauldronBlock {

    public MilkCauldronBlock(Properties pProperties, Map<Item, CauldronInteraction> pInteractions) {
        super(pProperties, pInteractions);
    }

    @Override
    public boolean isFull(BlockState pState) {
        return true;
    }

    // TODO:
    //  by now turns the whole MilkCauldron to a Block of Cheese if a cheese is thrown into it
    //  - Change behaviour:
    //      - Either with different item
    //      - or content turns to cheese and can be harvested?
    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (isEntityInsideContent(pState, pPos, pEntity)) {

            if (pEntity instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() == ModItems.CHEESE.get()) {
                pLevel.setBlock(pPos, ModBlocks.CHEESE_BLOCK.get().defaultBlockState(), 3);
                itemEntity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    protected double getContentHeight(BlockState pState) {
        return 0.5D;
    }
}
