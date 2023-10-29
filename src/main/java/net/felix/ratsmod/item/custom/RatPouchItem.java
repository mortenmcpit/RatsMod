package net.felix.ratsmod.item.custom;

import net.felix.ratsmod.entity.custom.RatEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class RatPouchItem extends Item {
private static final Predicate<RatEntity> CLOSEST_RAT_PREDICATE = (ratEntity) -> ratEntity.isTame();
private final int size;
    public RatPouchItem(Properties properties, int size) {
        super(properties);
        this.size = size;
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.ratsmod.more_info").withStyle(
                    ChatFormatting.AQUA
            ));
        } else {
            components.add(Component.translatable("tooltip.ratsmod.leather_rat_pouch").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
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
