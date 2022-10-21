package fengliu.invincible.block;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
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
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()
	);

    public static void registerAllBlock(){
        // 灵石砖
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_bricks"), REIKI_STONE_BRICKS);
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
