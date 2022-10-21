package fengliu.invincible.block.entity;

import fengliu.invincible.structure.ZhenFuLv1;
import fengliu.invincible.util.ZhenFuData.ZhenFus;
import net.minecraft.block.BlockState;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class ZhenYanLv1Entity extends ZhenYanEntity{

    public ZhenYanLv1Entity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.ZHEN_YAN_1_ENTITY, pos, state);
        this.setMaxItemStack(2);
    }

    @Override
    public ZhenFus getZhenFus() {
        return new ZhenFuLv1();
    }

    @Override
    public TranslatableText getNotZhenFuDisplayName() {
        return new TranslatableText("block.invincible.zhen_yan_1");
    }
    
}
