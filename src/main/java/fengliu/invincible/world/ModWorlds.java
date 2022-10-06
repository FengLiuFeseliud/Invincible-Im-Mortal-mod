package fengliu.invincible.world;

import fengliu.invincible.invincibleMod;
// import fengliu.invincible.block.ModBlocks;
// import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
// import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

public class ModWorlds {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    public static RegistryKey<DimensionOptions> Reiki_World_Key;

    public static void registryAllWorlds(){
        // 灵界
		Reiki_World_Key = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(MOD_ID, "reiki_world"));
		RegistryKey.of(Registry.WORLD_KEY, Reiki_World_Key.getValue());
		// 灵界世界类型
		RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(MOD_ID, "reiki_world_type"));
		// 灵界传送门
		// CustomPortalBuilder.beginPortal()  
		// 	.frameBlock(ModBlocks.REIKI_STONE_BRICKS)  
		// 	.customIgnitionSource(PortalIgnitionSource.FIRE)
		// 	.destDimID(new Identifier(MOD_ID, "reiki_world"))  
		// 	.forcedSize(3, 3)
		// 	.tintColor(240,255,255)
		// 	.registerPortal();
    }
}
