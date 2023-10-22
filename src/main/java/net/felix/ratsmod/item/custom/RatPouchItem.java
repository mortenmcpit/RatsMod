package net.felix.ratsmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RatPouchItem extends Item {
    public RatPouchItem(Properties properties) {
        super(properties);
    }

    /*
    TODO:
        -this is placeholder logic
        - add functionalty to put Rat into pouch
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        // this puts out a random number with cooldown
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isCrouching()) {
            outPutRandomNumber(player);
            player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, hand);
    }

    // testing function
    private void outPutRandomNumber(Player player) {
        player.sendSystemMessage(Component.literal("Your number is: " + getRandomNumber()));
    }

    // testing function
    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
