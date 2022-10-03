package fengliu.invincible.entity.block;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.screen.handler.Zhen_Yan_ScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class Zhen_Yan_Entity {

    public static class Lv1 extends Ui_Block.Entity {

        public Lv1(BlockPos pos, BlockState state) {
            super(ModBlockEntitys.ZHEN_YAN_1_ENTITY, pos, state);

            this.setMaxItemStack(6);
        }

        @Override
        public Text getDisplayName() {
            return new TranslatableText("block.invincible.zhen_yan_1.name");
        }

        @Override
        public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
            return new Zhen_Yan_ScreenHandler.Lv1(syncId, inventory, this);
        }
    }
}
