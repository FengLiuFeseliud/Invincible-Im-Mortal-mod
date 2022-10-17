package fengliu.invincible.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.util.ReikiItemData;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class InjectionReikiRecipe implements Recipe<SimpleInventory> {
    private final Ingredient input;
    private final ItemStack outputStack;
    public int count;
    private final Identifier id;

    public static class JsonFormat {
        public JsonObject input;
        public String result;
        public int count;
    }

    public InjectionReikiRecipe(Ingredient input, ItemStack outputStack, int count, Identifier id) {
        this.input = input;
        this.outputStack = outputStack;
        this.count = count;
        this.id = id;
    }

    public Ingredient getInput() {
        return input;
    }

    public int getCount(){
        return count;
    }

    /**
     * 存在配方并且完成注入灵气目标返回 true
     */
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient){
            return false;
        }

        ItemStack stack = inventory.getStack(0);
        if(!ReikiItemData.canInjectionReiki(stack)){
            return false;
        }
        
        return input.test(stack) && ReikiItemData.isExceedTargetReiki(stack);
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

    public static class Type implements RecipeType<InjectionReikiRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "injection_reiki_recipe";
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

    public static class Serializer implements RecipeSerializer<InjectionReikiRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = new Identifier(invincibleMod.MOD_ID, "injection_reiki_recipe");
        private Serializer() {}
    
        @Override
        public InjectionReikiRecipe read(Identifier id, JsonObject json) {
            InjectionReikiRecipe.JsonFormat recipeJson = new Gson().fromJson(json, InjectionReikiRecipe.JsonFormat.class);
            if(recipeJson.input == null){
                throw new JsonSyntaxException("A required attribute is missing!");
            }
            
            if (recipeJson.count == 0){
                recipeJson.count = 1;
            }
    
            Ingredient input = Ingredient.fromJson(recipeJson.input);
            Item result = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.result));
            ItemStack output = new ItemStack(result, recipeJson.count);
            
            return new InjectionReikiRecipe(input, output, recipeJson.count, id);
        }
        @Override
        public void write(PacketByteBuf packetData, InjectionReikiRecipe recipe) {
            recipe.getInput().write(packetData);
            packetData.writeInt(recipe.count);
            packetData.writeItemStack(recipe.getOutput());
        }
    
        @Override
        public InjectionReikiRecipe read(Identifier id, PacketByteBuf packetData) {
            return new InjectionReikiRecipe(
                Ingredient.fromPacket(packetData), packetData.readItemStack(), packetData.readInt(), id
            );
        }
        
    }
    
}
