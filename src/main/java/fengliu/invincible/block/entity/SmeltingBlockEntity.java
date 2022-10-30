package fengliu.invincible.block.entity;

import java.util.List;
import java.util.Optional;

import fengliu.invincible.recipe.SmeltRecipe;
import fengliu.invincible.item.QualityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

/**
 * 可使用 SmeltRecipe 配方的块实体
 * 
 * 需要 10 格, 最后一格为输出口
 */
public interface SmeltingBlockEntity{
    DefaultedList<ItemStack> getItems();
    Optional<SmeltRecipe> getMatch();
    PlayerEntity getPlayer();
    /**
     * 返回当前制作经验
     */
    int getPlayerExp();

    /**
     * 保存使用者
     */
    void setUesPlayer(PlayerEntity player);
    /**
     * 获取记录的使用者
     * @param world 世界
     * @return 玩家 ( null )
     */
    default public PlayerEntity getUesPlayer(World world){
        PlayerEntity uesPlayer = getPlayer();
        if(uesPlayer == null){
            return uesPlayer;
        }
        
        if(uesPlayer.isDead()){
            setUesPlayer(world.getPlayerByUuid(uesPlayer.getUuid()));
            uesPlayer = getPlayer();
        }
        return uesPlayer;
    }
    
    /**
     * 获取配方匹配器
     * @param world 当前世界
     * @return 配方匹配器
     */
    default public Optional<SmeltRecipe> getRecipeMatch(World world){
        SimpleInventory inventory = new SimpleInventory(9);
        for(int index = 0; index < 9; index++){
            inventory.addStack(getItems().get(index));
        }
        return world.getRecipeManager().getFirstMatch(SmeltRecipe.Type.INSTANCE, inventory, world);
    }

    /**
     * 获取配方输出物品
     * @return 输出物品
     */
    default public Item getMatchOutSmeltItem(){
        Item outItem =getMatch().get().getOutput().getItem();

        if(!(outItem instanceof QualityItem)){
            return outItem;
        }
        return ((QualityItem) outItem).getQualityItem(getPlayerExp());
    }

    /**
     * 消耗配方需要的块实体库存材料
     */
    default public void consumeRecipeItems(){
        DefaultedList<ItemStack> items = getItems();

        List<ItemStack> consumeItemsStack = getMatch().get().getInventory().clearToList();
        int consumeItemsSize = consumeItemsStack.size();
        for(int index = 0; index < 9; index++){
            if(index > consumeItemsSize - 1){
                items.set(index, new ItemStack(Items.AIR));
                continue;
            }
            items.set(index, consumeItemsStack.get(index));
        }
    }

    /**
     * 设置块实体输出
     */
    default public void setOutSmeltItem(){
        ItemStack outSmeltStack = getItems().get(9);
        Optional<SmeltRecipe> match = getMatch();

        int outItemCount = match.get().getCount();
        if(outSmeltStack.isEmpty()){
            getItems().set(9, new ItemStack(getMatchOutSmeltItem(), outItemCount));
        }else{
            outSmeltStack.increment(outItemCount);
        }
    }

    /**
     * 检查输出口是否可以进行配方合成
     * @return true 可以
     */
    default public boolean canSmelting(){
        Item outItem = getMatchOutSmeltItem();
        ItemStack outDanLuStack = getItems().get(9);
        if((!outDanLuStack.isOf(outItem) && !outDanLuStack.isEmpty()) || outDanLuStack.getCount() >= outItem.getMaxCount()){
            return false;
        }
        return true;
    }
}
