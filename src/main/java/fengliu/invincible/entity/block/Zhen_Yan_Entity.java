package fengliu.invincible.entity.block;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.screen.handler.Zhen_Yan_ScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class Zhen_Yan_Entity {

    public static class Lv1 extends LootableContainerBlockEntity {

        private DefaultedList<ItemStack> inv = DefaultedList.ofSize(6, ItemStack.EMPTY);

        public Lv1(BlockPos pos, BlockState state) {
            super(invincibleMod.ZHEN_YAN_1_ENTITY, pos, state);
        }

        @Override
        protected Text getContainerName() {
            return new TranslatableText("block.invincible.zhen_yan_1.name");
        }

        @Override
        protected DefaultedList<ItemStack> getInvStackList() {
            return this.inv;
        }

        @Override
        protected void setInvStackList(DefaultedList<ItemStack> list) {
            this.inv = list;
        }

        @Override
        protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
            return new Zhen_Yan_ScreenHandler.Lv1(syncId, playerInventory);
        }

        @Override
        public int size() {
            return this.inv.size();
        }

        @Override
        protected void writeNbt(NbtCompound nbt) {
            super.writeNbt(nbt);
            Inventories.writeNbt(nbt, inv);
        }

        @Override
        public void readNbt(NbtCompound nbt) {
            super.readNbt(nbt);
            Inventories.readNbt(nbt, inv);
        }
    }
}
