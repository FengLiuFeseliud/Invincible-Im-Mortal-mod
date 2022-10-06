package fengliu.invincible.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import fengliu.invincible.util.ReikiItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

/*
 * 为每个物品叠保存灵气数据
 */

@Mixin(ItemStack.class)
public abstract class ModItemStackDataSaveMixin{
    @Shadow(aliases = "item") private Item item;
    @Shadow(aliases = "empty") private boolean empty;
    @Shadow(aliases = "nbt") private NbtCompound nbt;

    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void writeNbt(NbtCompound nbt, CallbackInfoReturnable<CallbackInfo> info) {
        ReikiItem reikiItem = (ReikiItem) getItem();

        nbt.putInt("invincible.max_reiki", reikiItem.getMaxReiki());
        if(nbt.contains("invincible.reiki")){
            nbt.putInt("invincible.reiki", reikiItem.getReiki());
        }
    }

    public Item getItem() {
        return this.empty ? Items.AIR : this.item;
    }

    public NbtCompound getOrCreateNbt() {
        if (this.nbt == null) {
            nbt = new NbtCompound();
        }
        return this.nbt;
    }

}
