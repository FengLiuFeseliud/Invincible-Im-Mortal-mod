package fengliu.invincible.block.item;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockItems {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    // 灵石砖
	public static final BlockItem REIKI_STONE_BRICKS_ITEM= new BaseBlockItem(
		"block.invincible.reiki_stone_bricks.tooltip",
		ModBlocks.REIKI_STONE_BRICKS,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 玉石原石矿
	public static final BlockItem JADE_ROUGH_STONE_ORE_ITEM= new BaseBlockItem(
		"block.invincible.jade_rough_stone_ore.tooltip",
		ModBlocks.JADE_ROUGH_STONE_ORE,
		new FabricItemSettings()
			.maxCount(64)
	);
    // 一级灵石矿
	public static final BlockItem REIKI_STONE_ORE_1_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_1.tooltip",
		ModBlocks.REIKI_STONE_ORE_1,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 二级灵石矿
	public static final BlockItem REIKI_STONE_ORE_2_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_2.tooltip",
		ModBlocks.REIKI_STONE_ORE_2,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 三级灵石矿
	public static final BlockItem REIKI_STONE_ORE_3_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_3.tooltip",
		ModBlocks.REIKI_STONE_ORE_3,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 四级灵石矿
	public static final BlockItem REIKI_STONE_ORE_4_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_4.tooltip",
		ModBlocks.REIKI_STONE_ORE_4,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 深层玉石原石矿
	public static final BlockItem DEEPSLATE_JADE_ROUGH_STONE_ORE_ITEM= new BaseBlockItem(
		"block.invincible.jade_rough_stone_ore.tooltip",
		ModBlocks.DEEPSLATE_JADE_ROUGH_STONE_ORE,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 一级深层灵石矿
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_1_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_1.tooltip",
		ModBlocks.DEEPSLATE_REIKI_STONE_ORE_1,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 二级深层灵石矿
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_2_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_2.tooltip",
		ModBlocks.DEEPSLATE_REIKI_STONE_ORE_2,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 三级深层灵石矿
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_3_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_3.tooltip",
		ModBlocks.DEEPSLATE_REIKI_STONE_ORE_3,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 四级深层灵石矿
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_4_ITEM = new BaseBlockItem(
		"block.invincible.reiki_stone_ore_4.tooltip",
		ModBlocks.DEEPSLATE_REIKI_STONE_ORE_4,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 灵草
	public static final BlockItem REIKI_GRASS_ITEM = new BaseBlockItem(
		"block.invincible.reiki_grass.tooltip",
		ModBlocks.REIKI_GRASS,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 回气草
	public static final BlockItem HUI_QI_CAO_ITEM = new BaseBlockItem(
		"block.invincible.hui_qi_cao.tooltip",
		ModBlocks.HUI_QI_CAO,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 角磨机
	public static final BlockItem ANGLE_GRINDER_ITEM = new BaseBlockItem(
		"block.invincible.angle_grinder.tooltip",
		ModBlocks.ANGLE_GRINDER,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 注灵台
	public static final BlockItem INJECTION_REIKI_STANDS_ITEM = new BaseBlockItem(
		"block.invincible.injection_reiki_stands.tooltip",
		ModBlocks.INJECTION_REIKI_STANDS,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 丹炉
	public static final BlockItem DAN_LU = new BaseBlockItem(
		"",
		ModBlocks.DAN_LU,
		new FabricItemSettings()
			.maxCount(64)
	);
	// 一阶阵眼
	public static final BlockItem ZHEN_YAN_1_ITEM = new BaseBlockItem(
		"block.invincible.zhen_yan_1.tooltip",
		ModBlocks.ZHEN_YAN_1,
		new FabricItemSettings()
			.maxCount(64)
	);

    public static void registerAllBlockItem(){
        // 灵石砖
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_bricks"), REIKI_STONE_BRICKS_ITEM);
		// 玉石原石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "jade_rough_stone_ore"), JADE_ROUGH_STONE_ORE_ITEM);
		// 一级灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_1"), REIKI_STONE_ORE_1_ITEM);
		// 二级灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_2"), REIKI_STONE_ORE_2_ITEM);
		// 三级灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_3"), REIKI_STONE_ORE_3_ITEM);
		// 四级灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_4"), REIKI_STONE_ORE_4_ITEM);
		// 深层玉石原石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_jade_rough_stone_ore"), DEEPSLATE_JADE_ROUGH_STONE_ORE_ITEM);
		// 一级深层灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_1"), DEEPSLATE_REIKI_STONE_ORE_1_ITEM);
		// 二级深层灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_2"), DEEPSLATE_REIKI_STONE_ORE_2_ITEM);
		// 三级深层灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_3"), DEEPSLATE_REIKI_STONE_ORE_3_ITEM);
		// 四级深层灵石矿
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_4"), DEEPSLATE_REIKI_STONE_ORE_4_ITEM);
		// 灵草
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_grass"), REIKI_GRASS_ITEM);
		// 回气草
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_cao"), HUI_QI_CAO_ITEM);
		// 角磨机
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "angle_grinder"), ANGLE_GRINDER_ITEM);
		// 注灵台
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "injection_reiki_stands"), INJECTION_REIKI_STANDS_ITEM);
		// 丹炉
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dan_lu"), DAN_LU);
		// 一阶阵眼
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "zhen_yan_1"), ZHEN_YAN_1_ITEM);
    }
}
