package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import fengliu.invincible.item.KungFuItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 添加支持自定义书的检查
 */
@Mixin(ServerPlayerEntity.class)
public class BookServerPlayerEntityMixin extends PlayerEntity{
    public ServerPlayNetworkHandler networkHandler;

    public BookServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "useBook", at = @At("HEAD"))
    public void useBook(ItemStack book, Hand hand, CallbackInfo info) {
        if (book.getItem() instanceof KungFuItem) {
            this.networkHandler.sendPacket(new OpenWrittenBookS2CPacket(hand));
        }
    }

    public boolean isSpectator() {
        return false;
    }

    public boolean isCreative() {
        return false;
    }
}
