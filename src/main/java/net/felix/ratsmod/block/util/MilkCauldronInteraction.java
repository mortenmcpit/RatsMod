package net.felix.ratsmod.block.util;

import net.felix.ratsmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class MilkCauldronInteraction implements CauldronInteraction {
    public static Map<Item, CauldronInteraction> MILK = CauldronInteraction.newInteractionMap();

    @Override
    public InteractionResult interact(BlockState pBlockState, Level pLevel, BlockPos pBlockPos,
                                      Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        return CauldronInteraction.emptyBucket(pLevel, pBlockPos, pPlayer, pHand, pStack,
                ModBlocks.MILK_CAULDRON.get().defaultBlockState(), SoundEvents.BOTTLE_EMPTY);
    }

    public static void bootstrap() {
        EMPTY.put(Items.MILK_BUCKET, new MilkCauldronInteraction());
        MILK.put(Items.BUCKET, (p_175725_, p_175726_, p_175727_, p_175728_, p_175729_, p_175730_) -> {
            return CauldronInteraction.fillBucket(p_175725_, p_175726_, p_175727_, p_175728_, p_175729_, p_175730_, new ItemStack(Items.MILK_BUCKET), (p_175660_) -> {
                return true;
            }, SoundEvents.BUCKET_FILL);
        });
    }
}
