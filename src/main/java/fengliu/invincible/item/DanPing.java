package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DanPing extends Item {

    public DanPing(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.dan_ping.tooltip"));
        NbtCompound nbt = stack.getOrCreateNbt();
        if(!nbt.contains("Items")){
            super.appendTooltip(stack, world, tooltip, context);
            return;
        }
        tooltip.add(new LiteralText(""));

        NbtList nbtList = (NbtList) nbt.get("Items");
        nbtList.forEach((item) -> {
            NbtCompound itemNbt = (NbtCompound) item;
            tooltip.add(new LiteralText(
                Registry.ITEM.get(new Identifier(itemNbt.getString("id"))).getName().getString() + " - " + itemNbt.getInt("Count")
            ));
        });
        super.appendTooltip(stack, world, tooltip, context);
    }

    /**
     * 存入丹药
     * @param danPingStack 丹瓶格
     * @param danYanStack 丹药格
     */
    public static void add (ItemStack danPingStack,ItemStack danYanStack){
        NbtCompound nbt = danPingStack.getOrCreateNbt();
        NbtList nbtList = new NbtList();
        if(nbt.contains("Items")){
            nbtList = (NbtList) nbt.get("Items");
        }

        int size = nbtList.size();
        if(size == 4){
            return;
        }

        NbtCompound itemNbt = new NbtCompound();
        itemNbt.putInt("Slot", size);
        itemNbt.putString("id", Registry.ITEM.getId(danYanStack.getItem()).toString());
        itemNbt.putInt("Count", danYanStack.getCount());

        nbtList.add(itemNbt);
        nbt.put("Items", nbtList);
        danYanStack.setCount(0);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient){
            return super.use(world, user, hand);
        }

        ItemStack stack = user.getStackInHand(hand);
        NbtCompound nbt = stack.getOrCreateNbt();
        if(!nbt.contains("Items")){
            return super.use(world, user, hand);
        }

        NbtList nbtList = (NbtList) nbt.get("Items");
        if(nbtList.size() == 0){
            return super.use(world, user, hand);
        }
        PlayerInventory inventory = user.getInventory();

        NbtCompound itemNbt = (NbtCompound) nbtList.remove(nbtList.size() - 1);
        ItemStack danYanStack = new ItemStack(
            Registry.ITEM.get(new Identifier(itemNbt.getString("id"))), itemNbt.getInt("Count")
        );

        int slot = inventory.getEmptySlot();
        if(slot == -1){
            world.spawnEntity(new ItemEntity(
                world, user.getX(), user.getY(), user.getZ(), danYanStack
            ));
            return super.use(world, user, hand);
        }
        
        inventory.setStack(slot, danYanStack);
        return super.use(world, user, hand);
    }
}
