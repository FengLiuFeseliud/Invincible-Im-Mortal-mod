package fengliu.invincible.block;

import fengliu.invincible.invincibleMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    // 灵石砖
	public static final Block REIKI_STONE_BRICKS= new Block(
		FabricBlockSettings.of(Material.STONE).strength(10.0f).requiresTool()
	);
	// 书架
	public static final Block BOOK_SHELF = new BookShelf(
		FabricBlockSettings.of(Material.WOOD).strength(1.5f)
	);
	// 金币堆
	public static final Block GOLD_HEAP = new SnowBlock(
		FabricBlockSettings.of(Material.SNOW_LAYER).strength(0.5f)
	);
    // 玉石原石矿
	public static final Block JADE_ROUGH_STONE_ORE = new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(6.5f).requiresTool(),
		UniformIntProvider.create(2, 6)
	);
    // 一级灵石矿
	public static final Block REIKI_STONE_ORE_1= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool(),
		UniformIntProvider.create(2, 6)
	);
	// 二级灵石矿
	public static final Block REIKI_STONE_ORE_2= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(6.5f).requiresTool(),
		UniformIntProvider.create(3, 7)
	);
    // 三级灵石矿
	public static final Block REIKI_STONE_ORE_3= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(8.0f).requiresTool(),
		UniformIntProvider.create(4, 12)
	);
	// 四级灵石矿
	public static final Block REIKI_STONE_ORE_4= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(9.5f).requiresTool(),
		UniformIntProvider.create(12, 24)
	);
	// 深层玉石原石矿
	public static final Block DEEPSLATE_JADE_ROUGH_STONE_ORE = new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(7.5f).requiresTool(), 
		UniformIntProvider.create(2, 6)
	);
	// 一级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_1= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(6.0f).requiresTool(),
		UniformIntProvider.create(2, 6)
	);
	// 二级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_2= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(7.5f).requiresTool(),
		UniformIntProvider.create(3, 7)
	);
	// 三级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_3= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(9.0f).requiresTool(),
		UniformIntProvider.create(4, 12)
	);
	// 四级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_4= new OreBlock(
		FabricBlockSettings.of(Material.STONE).strength(10.5f).requiresTool(),
		UniformIntProvider.create(12, 24)
	);
	// 灵草
	public static final Block REIKI_GRASS = new FlowerBlock(
		StatusEffects.SATURATION, 30,
		FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly()
	);
	// 回气草
	public static final Block HUI_QI_CAO = new FlowerBlock(
		StatusEffects.SATURATION, 30,
		FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly()
	);
	// 角磨机
	public static final Block ANGLE_GRINDER = new AngleGrinder(
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()
	);
	// 丹炉
	public static final Block DAN_LU = new DanLu(
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()
	);
	// 注灵台
	public static final Block INJECTION_REIKI_STANDS = new InjectionReikiStands(
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()
	);
	// 一阶阵眼
	public static final Block ZHEN_YAN_1 = new ZhenYanLv1(
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque()
	);

	@Environment(EnvType.CLIENT)
	public static void setAllBlockRenderLayerMap(){
		BlockRenderLayerMap.INSTANCE.putBlock(BOOK_SHELF, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ZHEN_YAN_1, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ANGLE_GRINDER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(DAN_LU, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(REIKI_GRASS, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(HUI_QI_CAO, RenderLayer.getTranslucent());
	};

    public static void registerAllBlock(){
        // 灵石砖
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_bricks"), REIKI_STONE_BRICKS);
		// 书架
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "book_shelf"), BOOK_SHELF);
		// 金币堆
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "gold_heap"), GOLD_HEAP);
		// 玉石原石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "jade_rough_stone_ore"), JADE_ROUGH_STONE_ORE);
		// 一级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_1"), REIKI_STONE_ORE_1);
		// 二级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_2"), REIKI_STONE_ORE_2);
		// 三级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_3"), REIKI_STONE_ORE_3);
		// 四级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_4"), REIKI_STONE_ORE_4);
		// 深层玉石原石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_jade_rough_stone_ore"), DEEPSLATE_JADE_ROUGH_STONE_ORE);
		// 一级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_1"), DEEPSLATE_REIKI_STONE_ORE_1);
		// 二级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_2"), DEEPSLATE_REIKI_STONE_ORE_2);
		// 三级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_3"), DEEPSLATE_REIKI_STONE_ORE_3);
		// 四级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_4"), DEEPSLATE_REIKI_STONE_ORE_4);
		// 灵草
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_grass"), REIKI_GRASS);
		// 回气草
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "hui_qi_cao"), HUI_QI_CAO);
		// 角磨机
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "angle_grinder"), ANGLE_GRINDER);
		// 注灵台
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "injection_reiki_stands"), INJECTION_REIKI_STANDS);
		// 丹炉
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "dan_lu"), DAN_LU);
		// 一阶阵眼
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "zhen_yan_1"), ZHEN_YAN_1);
    }
}   
