package fengliu.invincible.entity.block;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.screen.handler.Angle_Grinder_ScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class Angle_Grinder_Entity extends Ui_Block.Entity {

    public Angle_Grinder_Entity(BlockPos blockPos, BlockState blockState) {
        super(invincibleMod.ANGLE_GRINDER_ENTITY, blockPos, blockState);

        this.setMaxItemStack(2);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("block.invincible.angle_grinder.name");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new Angle_Grinder_ScreenHandler(syncId, playerInventory, this);
    }
    
}
