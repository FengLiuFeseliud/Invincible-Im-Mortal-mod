package fengliu.invincible;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengliu.invincible.entity.Reiki;
import fengliu.invincible.entity.block.Zhen_Yan_Entity;
import fengliu.invincible.entity.renderer.Reiki_Renderer;
import fengliu.invincible.item.Jade_Rough_Stone;
import fengliu.invincible.item.Kung_Fu;
import fengliu.invincible.item.Reiki_Stone;
import fengliu.invincible.item.block.Jade_Rough_Stone_Ore;
import fengliu.invincible.item.block.Reiki_Stone_Bricks_Item;
import fengliu.invincible.item.block.Reiki_Stone_Ore;
import fengliu.invincible.item.block.Zhen_Yan_Item;
import fengliu.invincible.screen.handler.Zhen_Yan_ScreenHandler;
import fengliu.invincible.screen.handler.Zhen_Yan_ScreenHandler.Lv1;
import fengliu.invincible.block.Zhen_Yan;


public class invincibleMod implements ModInitializer {
	public static final String MOD_ID = "invincible";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<Reiki> REIKI = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "reiki"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, Reiki::new)
				.dimensions(EntityDimensions.fixed(0.75f, 0.75f)
			).build()
    );

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

	// 功法
	public static final Kung_Fu KUNG_FU = new Kung_Fu(
		new FabricItemSettings()
			.maxCount(1)
	);

	// 灵石砖
	public static final Block REIKI_STONE_BRICKS= new Block(
		FabricBlockSettings.of(Material.STONE).strength(10.0f).requiresTool()
	);
	public static final BlockItem REIKI_STONE_BRICKS_ITEM= new Reiki_Stone_Bricks_Item(
		REIKI_STONE_BRICKS,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 玉石原石矿
	public static final Block JADE_ROUGH_STONE_ORE = new Block(
		FabricBlockSettings.of(Material.STONE).strength(6.5f).requiresTool()
	);
	public static final BlockItem JADE_ROUGH_STONE_ORE_ITEM= new Jade_Rough_Stone_Ore(
		JADE_ROUGH_STONE_ORE,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 一级灵石矿
	public static final Block REIKI_STONE_ORE_1= new Block(
		FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()
	);
	public static final BlockItem REIKI_STONE_ORE_1_ITEM = new Reiki_Stone_Ore.Lv1(
		REIKI_STONE_ORE_1,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 二级灵石矿
	public static final Block REIKI_STONE_ORE_2= new Block(
		FabricBlockSettings.of(Material.STONE).strength(6.5f).requiresTool()
	);
	public static final BlockItem REIKI_STONE_ORE_2_ITEM = new Reiki_Stone_Ore.Lv2(
		REIKI_STONE_ORE_2,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 三级灵石矿
	public static final Block REIKI_STONE_ORE_3= new Block(
		FabricBlockSettings.of(Material.STONE).strength(8.0f).requiresTool()
	);
	public static final BlockItem REIKI_STONE_ORE_3_ITEM = new Reiki_Stone_Ore.Lv3(
		REIKI_STONE_ORE_3,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 四级灵石矿
	public static final Block REIKI_STONE_ORE_4= new Block(
		FabricBlockSettings.of(Material.STONE).strength(9.5f).requiresTool()
	);
	public static final BlockItem REIKI_STONE_ORE_4_ITEM = new Reiki_Stone_Ore.Lv4(
		REIKI_STONE_ORE_4,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 深层玉石原石矿
	public static final Block DEEPSLATE_JADE_ROUGH_STONE_ORE = new Block(
		FabricBlockSettings.of(Material.STONE).strength(7.5f).requiresTool()
	);
	public static final BlockItem DEEPSLATE_JADE_ROUGH_STONE_ORE_ITEM= new Jade_Rough_Stone_Ore(
		DEEPSLATE_JADE_ROUGH_STONE_ORE,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 一级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_1= new Block(
		FabricBlockSettings.of(Material.STONE).strength(6.0f).requiresTool()
	);
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_1_ITEM = new Reiki_Stone_Ore.Lv1(
		DEEPSLATE_REIKI_STONE_ORE_1,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 二级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_2= new Block(
		FabricBlockSettings.of(Material.STONE).strength(7.5f).requiresTool()
	);
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_2_ITEM = new Reiki_Stone_Ore.Lv2(
		DEEPSLATE_REIKI_STONE_ORE_2,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 三级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_3= new Block(
		FabricBlockSettings.of(Material.STONE).strength(9.0f).requiresTool()
	);
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_3_ITEM = new Reiki_Stone_Ore.Lv3(
		DEEPSLATE_REIKI_STONE_ORE_3,
		new FabricItemSettings()
			.maxCount(64)
	);

	// 四级深层灵石矿
	public static final Block DEEPSLATE_REIKI_STONE_ORE_4= new Block(
		FabricBlockSettings.of(Material.STONE).strength(10.5f).requiresTool()
	);
	public static final BlockItem DEEPSLATE_REIKI_STONE_ORE_4_ITEM = new Reiki_Stone_Ore.Lv4(
		DEEPSLATE_REIKI_STONE_ORE_4,
		new FabricItemSettings()
			.maxCount(64)
	);

	public static final Block ZHEN_YAN_1 = new Zhen_Yan.Lv1(
		AbstractBlock.Settings.of(Material.STONE).strength(10.5f).requiresTool()
	);
	public static final BlockItem ZHEN_YAN_1_ITEM = new Zhen_Yan_Item.Lv1(
		ZHEN_YAN_1,
		new FabricItemSettings()
			.maxCount(64)
	);
	public static BlockEntityType<Zhen_Yan_Entity.Lv1> ZHEN_YAN_1_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, 
		new Identifier(MOD_ID, "zhen_yan_1_entity"), 
		FabricBlockEntityTypeBuilder.create(Zhen_Yan_Entity.Lv1::new, ZHEN_YAN_1).build(null)
	);

	public static RegistryKey<DimensionOptions> Reiki_World_Key;

	// 材料组
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "item_group"))
			.icon(() -> new ItemStack(REIKI_STONE_3))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(JADE_ROUGH_STONE));
				stacks.add(new ItemStack(REIKI_STONE_0));
				stacks.add(new ItemStack(REIKI_STONE_1));
				stacks.add(new ItemStack(REIKI_STONE_2));
				stacks.add(new ItemStack(REIKI_STONE_3));
				stacks.add(new ItemStack(REIKI_STONE_4));
				stacks.add(new ItemStack(REIKI_STONE_5));
			})
			.build();

	// 方块组
	public static final ItemGroup BLOCK_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "block_group"))
			.icon(() -> new ItemStack(REIKI_STONE_BRICKS_ITEM))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(REIKI_STONE_BRICKS));
				stacks.add(new ItemStack(JADE_ROUGH_STONE_ORE));
				stacks.add(new ItemStack(REIKI_STONE_ORE_1));
				stacks.add(new ItemStack(REIKI_STONE_ORE_2));
				stacks.add(new ItemStack(REIKI_STONE_ORE_3));
				stacks.add(new ItemStack(REIKI_STONE_ORE_4));
				stacks.add(new ItemStack(DEEPSLATE_JADE_ROUGH_STONE_ORE));
				stacks.add(new ItemStack(DEEPSLATE_REIKI_STONE_ORE_1));
				stacks.add(new ItemStack(DEEPSLATE_REIKI_STONE_ORE_2));
				stacks.add(new ItemStack(DEEPSLATE_REIKI_STONE_ORE_3));
				stacks.add(new ItemStack(DEEPSLATE_REIKI_STONE_ORE_4));
			})
			.build();

	// 功法组
	public static final ItemGroup KUNG_FU_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "kung_fu_group"))
			.icon(() -> new ItemStack(KUNG_FU))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(KUNG_FU));
			})
			.build();

	// 阵法组
	public static final ItemGroup ZHEN_FU_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "zhen_fu_group"))
			.icon(() -> new ItemStack(REIKI_STONE_BRICKS_ITEM))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ZHEN_YAN_1));
			})
			.build();

	@Override
	public void onInitialize() {
		// 灵气
		EntityRendererRegistry.register(REIKI, (context) -> {
            return new Reiki_Renderer(context);
        });


		// 灵石砖
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_bricks"), REIKI_STONE_BRICKS);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_bricks"), REIKI_STONE_BRICKS_ITEM);
		// 玉石原石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "jade_rough_stone_ore"), JADE_ROUGH_STONE_ORE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "jade_rough_stone_ore"), JADE_ROUGH_STONE_ORE_ITEM);
		// 一级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_1"), REIKI_STONE_ORE_1);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_1"), REIKI_STONE_ORE_1_ITEM);
		// 二级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_2"), REIKI_STONE_ORE_2);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_2"), REIKI_STONE_ORE_2_ITEM);
		// 三级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_3"), REIKI_STONE_ORE_3);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_3"), REIKI_STONE_ORE_3_ITEM);
		// 四级灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "reiki_stone_ore_4"), REIKI_STONE_ORE_4);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_stone_ore_4"), REIKI_STONE_ORE_4_ITEM);
		// 深层玉石原石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_jade_rough_stone_ore"), DEEPSLATE_JADE_ROUGH_STONE_ORE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_jade_rough_stone_ore"), DEEPSLATE_JADE_ROUGH_STONE_ORE_ITEM);
		// 一级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_1"), DEEPSLATE_REIKI_STONE_ORE_1);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_1"), DEEPSLATE_REIKI_STONE_ORE_1_ITEM);
		// 二级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_2"), DEEPSLATE_REIKI_STONE_ORE_2);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_2"), DEEPSLATE_REIKI_STONE_ORE_2_ITEM);
		// 三级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_3"), DEEPSLATE_REIKI_STONE_ORE_3);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_3"), DEEPSLATE_REIKI_STONE_ORE_3_ITEM);
		// 四级深层灵石矿
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_4"), DEEPSLATE_REIKI_STONE_ORE_4);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "deepslate_reiki_stone_ore_4"), DEEPSLATE_REIKI_STONE_ORE_4_ITEM);
		// 一阶阵眼
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "zhen_yan_1"), ZHEN_YAN_1);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "zhen_yan_1"), ZHEN_YAN_1_ITEM);


		// 玉石原石
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "jade_rough_stone"), JADE_ROUGH_STONE);
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

		// 灵界
		Reiki_World_Key = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(MOD_ID, "reiki_world"));
		RegistryKey.of(Registry.WORLD_KEY, Reiki_World_Key.getValue());
		// 灵界世界类型
		RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(MOD_ID, "reiki_world_type"));
		// 灵界传送门
		CustomPortalBuilder.beginPortal()  
			.frameBlock(REIKI_STONE_BRICKS)  
			.customIgnitionSource(PortalIgnitionSource.FIRE)
			.destDimID(new Identifier(MOD_ID, "reiki_world"))  
			.forcedSize(3, 3)
			.tintColor(240,255,255)
			.registerPortal();
	}

	public static final ScreenHandlerType<Zhen_Yan_ScreenHandler.Lv1> ZHEN_YAN_1_SCREENHANDLER;
	
	static {
		// 一阶阵眼 UI
		ZHEN_YAN_1_SCREENHANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "ui_zhen_yan_1"), Zhen_Yan_ScreenHandler.Lv1::new);
	}

}
