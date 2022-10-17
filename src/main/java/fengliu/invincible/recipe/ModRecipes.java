package fengliu.invincible.recipe;

import fengliu.invincible.invincibleMod;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static final String MOD_ID = invincibleMod.MOD_ID;
    
    public static void registerAllRecipe(){
        Registry.register(Registry.RECIPE_SERIALIZER, InjectionReikiRecipe.Serializer.ID, InjectionReikiRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, InjectionReikiRecipe.Type.ID), InjectionReikiRecipe.Type.INSTANCE);
    }
}
