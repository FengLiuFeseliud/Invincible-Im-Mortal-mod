package fengliu.invincible.entity.block;

import java.util.ArrayList;
import java.util.List;

import fengliu.invincible.api.Ui_Block;
import fengliu.invincible.screen.handler.NotZhenFu_ScreenHandler;
import fengliu.invincible.structure.Zhen_Fu_Lv1;
import fengliu.invincible.util.ZhenFuData;
import fengliu.invincible.util.ZhenFuData.ZhenFuSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Zhen_Yan_Lv1_Entity extends Ui_Block.Entity{
    private ZhenFuSettings zhenFuType;
    private List<PlayerEntity> oldOnPlayerUes = new ArrayList<>();

    public Zhen_Yan_Lv1_Entity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.ZHEN_YAN_1_ENTITY, pos, state);

        this.setMaxItemStack(6);
    }

    @Override
    public Text getDisplayName() {
        if(zhenFuType == null){
            return new TranslatableText("block.invincible.zhen_yan_1.name");
        }
        return zhenFuType.Name;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        if(zhenFuType == null){
            return new NotZhenFu_ScreenHandler(syncId, inventory);
        }
        return zhenFuType.getScreenHandler(syncId, inventory);
    }

    public void setZhenFuType(ZhenFuSettings zhenFuType){
        this.zhenFuType = zhenFuType;
    }

    public ZhenFuSettings getZhenFuType(){
        return zhenFuType;
    }

    public void setOldOnPlayerUes(List<PlayerEntity> oldOnPlayerUes){
        this.oldOnPlayerUes = oldOnPlayerUes;
    }

    public List<PlayerEntity> getOldOnPlayerUes(){
        return oldOnPlayerUes;
    }

    public static void tick(World world, BlockPos pos, BlockState state, Zhen_Yan_Lv1_Entity be) {
        if(world.isClient){
            return;
        }

        ZhenFuSettings settings = be.getZhenFuType();
        if(settings == null){
            be.setZhenFuType(ZhenFuData.checkAllZhenFu(world, pos, new Zhen_Fu_Lv1()));
            return;
        }

        if(!settings.checkZhenFu(world, pos)){
            be.setZhenFuType(null);
            return;
        }

        List<PlayerEntity> newOnPlayerUes = ZhenFuData.onPlayerUes(settings, world, pos, state, be);
        ZhenFuData.onPlayerEndUes(settings, be.getOldOnPlayerUes(), newOnPlayerUes, world, pos, state, be);
        be.setOldOnPlayerUes(newOnPlayerUes);
        return;
    }
}
