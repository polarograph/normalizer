package club.polarite.normalizer.mixin;

import club.polarite.normalizer.Normalizer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Disables waterlogging client-side for a smoother experience with MLGs on slabs/leaves and other waterloggable blocks
 */
@Mixin(Item.class)
public abstract class WaterBucketMixin {
    @Inject(method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At("HEAD"),
            cancellable = true)
    private void onUseOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = context.getItemInHand();
        if (!(stack.getItem() instanceof BucketItem)) {
            return;
        }
        if (stack.getItem() != Items.WATER_BUCKET) {
            return;
        }

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos targetPos = clickedPos.relative(clickedFace);
        BlockState targetState = level.getBlockState(targetPos);

        if (!targetState.isAir() && targetState.canBeReplaced(Fluids.WATER)) {
            return;
        }

        if (level.getBlockState(clickedPos).canBeReplaced(Fluids.WATER)) {
            targetPos = clickedPos;
            targetState = level.getBlockState(clickedPos);
        }

        if (level.isClientSide()) {
            level.setBlock(targetPos, Blocks.WATER.defaultBlockState(), 11);
            Player player = context.getPlayer();
            level.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        Player player = context.getPlayer();
        if (player != null && !player.isCreative()) {
            InteractionHand hand = context.getHand();
            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}