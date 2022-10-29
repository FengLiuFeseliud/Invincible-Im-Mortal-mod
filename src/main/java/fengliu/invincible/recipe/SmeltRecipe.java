package fengliu.invincible.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import fengliu.invincible.invincibleMod;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * 9格无序合成 支持多材料, 材料消耗数
 */
public class SmeltRecipe implements Recipe<SimpleInventory> {
    private final JsonObject input;
    private final ItemStack outputStack;
    public int count;
    private final Identifier id;
    private SimpleInventory Inventory;

    public static class JsonFormat {
        public JsonObject input;
        public String result;
        public int count;
    }

    public SmeltRecipe(JsonObject input, ItemStack outputStack, int count, Identifier id) {
        this.input = input;
        this.outputStack = outputStack;
        this.count = count;
        this.id = id;
    }

    public JsonObject getInput() {
        return input;
    }

    public int getCount(){
        return count;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient || inventory.size() != 9){
            return false;
        }
        JsonObject inputItems = input.getAsJsonObject();

        boolean empty = false;
        Map<Item, Boolean> allItems = new HashMap<>();
        inputItems.keySet().forEach((key) ->{
            allItems.put(Registry.ITEM.get(new Identifier(key)), false);
        });

        for(int index = 0; index < 9; index++){
            ItemStack stack = inventory.getStack(index);
            if(stack.isEmpty()){
                continue;
            }else{
                empty = true;
            }

            Item item = stack.getItem();
            String itemId = Registry.ITEM.getId(item).toString();
            if(inputItems.keySet().contains(itemId)){
                if(allItems.get(item) || inputItems.get(Registry.ITEM.getId(item).toString()).getAsInt() > stack.getCount()){
                    continue;
                }
                allItems.put(item, true);
            }else{
                return false;
            }
        }

        if(!empty){
            return false;
        }

        for(boolean itemIn: allItems.values()){
            if(!itemIn){
                return false;
            }
        }

        Inventory = inventory;
        return true;
    }

    /**
     * 获取扣除材料后的新库存
     */
    public SimpleInventory getInventory() {
        List<Item> decrementInItem = new ArrayList<>();

        input.getAsJsonObject().entrySet().forEach((dataItem) -> {
            for(int index = 0; index < Inventory.size(); index++){
                ItemStack stack = Inventory.getStack(index);
                Item item = Registry.ITEM.get(new Identifier(dataItem.getKey()));

                if(!stack.isOf(item) || decrementInItem.contains(item)){
                    continue;
                }
                
                stack.decrement(dataItem.getValue().getAsInt());
                decrementInItem.add(item);
            }
        });

        return Inventory;
    }

    @Override
    public ItemStack getOutput() {
        return outputStack;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Type implements RecipeType<SmeltRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "smelt_recipe";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public ItemStack craft(SimpleInventory var1) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int var1, int var2) {
        return false;
    }

    public static class Serializer implements RecipeSerializer<SmeltRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = new Identifier(invincibleMod.MOD_ID, "smelt_recipe");
        private Serializer() {}
    
        @Override
        public SmeltRecipe read(Identifier id, JsonObject json) {
            SmeltRecipe.JsonFormat recipeJson = new Gson().fromJson(json, SmeltRecipe.JsonFormat.class);
            if(recipeJson.input == null){
                throw new JsonSyntaxException("A required attribute is missing!");
            }
            
            if (recipeJson.count == 0){
                recipeJson.count = 1;
            }
            
            Item result = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.result));
            ItemStack output = new ItemStack(result, recipeJson.count);
            
            return new SmeltRecipe(recipeJson.input, output, recipeJson.count, id);
        }
        @Override
        public void write(PacketByteBuf packetData, SmeltRecipe recipe) {
            packetData.writeString(recipe.getInput().toString());
            packetData.writeInt(recipe.count);
            packetData.writeItemStack(recipe.getOutput());
        }
    
        @Override
        public SmeltRecipe read(Identifier id, PacketByteBuf packetData) {
            JsonObject input = new Gson().fromJson(packetData.readString(), JsonObject.class);
            return new SmeltRecipe(
                input, packetData.readItemStack(), packetData.readInt(), id
            );
        }
        
    }
    
}
