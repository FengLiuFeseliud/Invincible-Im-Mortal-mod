package fengliu.invincible.item;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.item.Item;

public class ModItems {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    // 灵石
	public static final Item REIKI_STONE_0 = new Reiki_Stone.Lv0(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 一级灵石
	public static final Item REIKI_STONE_1 = new Reiki_Stone.Lv1(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 二级灵石
	public static final Item REIKI_STONE_2 = new Reiki_Stone.Lv2(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 三级灵石
	public static final Item REIKI_STONE_3 = new Reiki_Stone.Lv3(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 四级灵石
	public static final Item REIKI_STONE_4= new Reiki_Stone.Lv4(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 五级灵石
	public static final Item REIKI_STONE_5= new Reiki_Stone.Lv5(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 玉石原石
	public static final Item JADE_ROUGH_STONE= new Jade_Rough_Stone(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 玉石原石
	public static final Item JADE= new Jade(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 功法
	public static final Kung_Fu KUNG_FU = new Kung_Fu(
		new FabricItemSettings()
			.maxCount(1)
	);

    public static void registerAllItem(){
        // 玉石原石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "jade_rough_stone"), JADE_ROUGH_STONE);
		// 玉石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "jade"), JADE);
		// 灵石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_0"), REIKI_STONE_0);
		// 一级灵石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_1"), REIKI_STONE_1);
		// 二级灵石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_2"), REIKI_STONE_2);
		// 三级灵石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_3"), REIKI_STONE_3);
		// 四级灵石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_4"), REIKI_STONE_4);
		// 五级灵石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_5"), REIKI_STONE_5);
		// 功法
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "kung_fu"), KUNG_FU);
    }
}
