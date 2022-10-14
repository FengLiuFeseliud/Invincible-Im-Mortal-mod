package fengliu.invincible.item;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.item.ManaSkillsItem.ManaSkillSettings;
import fengliu.invincible.item.ManaSkillsItem.PostHitManaSkillSettings;
import fengliu.invincible.util.ReikiItem.ReikiSettings;
import fengliu.invincible.util.ReikiItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.TranslatableText;

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
	// 灵铁
	public static final Reiki_iron REIKI_IRON = new Reiki_iron(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 灵铁剑
	public static final Reiki_iron_sword REIKI_IRON_SWORD = new Reiki_iron_sword(
		ToolMaterials.IRON, 7, 1, 
			new FabricItemSettings()
				.maxCount(1),
			new ManaSkillSettings(Reiki_iron_sword::activeSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(100)
				.setSkillCd(5)
	);
	// 灵铁匕首
	public static final Reiki_iron_dagger REIKI_IRON_DAGGER = new Reiki_iron_dagger(
		ToolMaterials.IRON, 3, 3, 
			new FabricItemSettings()
				.maxCount(1),
			new PostHitManaSkillSettings(Reiki_iron_dagger::activeSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(20)
	);

	// public static final Item TEST = new MinecartItem(AbstractMinecartEntity.Type.COMMAND_BLOCK, new Settings().maxCount(1));
	public static void setAllItemReiki(){
		// 灵石
		((ReikiItem) REIKI_STONE_0).settings(
			new ReikiSettings()
				.maxReiki(0)
				.injectionReikTarget(500)
				.maxInjectionReiki(1000)
				.canInjectionReiki(true)
		);
		// 一级灵石
		((ReikiItem) REIKI_STONE_1).settings(
			new ReikiSettings()
				.maxReiki(1000)
				.injectionReikTarget(2000)
				.maxInjectionReiki(3000)
				.canInjectionReiki(true)
		);
		// 二级灵石
		((ReikiItem) REIKI_STONE_2).settings(
			new ReikiSettings()
				.maxReiki(3000)
				.injectionReikTarget(6000)
				.maxInjectionReiki(9000)
				.canInjectionReiki(true)
		);
	}
	
    public static void registerAllItem(){
		setAllItemReiki();
		
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
		// 灵铁
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron"), REIKI_IRON);
		// 灵铁剑
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_sword"), REIKI_IRON_SWORD);
		// 灵铁匕首
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_dagger"), REIKI_IRON_DAGGER);
		// Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test"), TEST);
    }
}
