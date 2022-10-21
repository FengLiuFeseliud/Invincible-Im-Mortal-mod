package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import fengliu.invincible.item.KungFuItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 添加支持自定义书的检查
 */
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class BookClientPlayerEntityMixin extends PlayerEntity{
    protected MinecraftClient client;

    public BookClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "useBook", at = @At("HEAD"))
    public void useBook(ItemStack book, Hand hand, CallbackInfo info) {
        if (book.getItem() instanceof KungFuItem) {
            this.client.setScreen(new BookEditScreen(this, book, hand));
        }
    }

    public boolean isSpectator() {
        return false;
    }


    public boolean isCreative() {
        return false;
    }
}
