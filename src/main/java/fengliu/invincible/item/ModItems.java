package fengliu.invincible.item;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.item.ManaSkillsItem.ManaSkillSettings;
import fengliu.invincible.item.ManaSkillsItem.PostHitManaSkillSettings;
import fengliu.invincible.item.ManaSkillsItem.PostMineManaSkillSettings;
import fengliu.invincible.item.kungfu.HuanHuoKung;
import fengliu.invincible.item.kungfu.HuanHuoKung.HuanHuoKungSettings;
import fengliu.invincible.item.kungfu.JuQiKung;
import fengliu.invincible.item.kungfu.JuQiKung.JuQiKungSettings;
import fengliu.invincible.item.tool.*;
import fengliu.invincible.item.danyan.*;
import fengliu.invincible.util.KungFuCilentData.KungFuTiekSettings;
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
	// 金币
	public static final Item GOLD = new Gold(
		new FabricItemSettings()
			.maxCount(64)
	);
    // 灵石
	public static final Item REIKI_STONE_0 = new ReikiStone.Lv0(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 一级灵石
	public static final Item REIKI_STONE_1 = new ReikiStone.Lv1(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 二级灵石
	public static final Item REIKI_STONE_2 = new ReikiStone.Lv2(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 三级灵石
	public static final Item REIKI_STONE_3 = new ReikiStone.Lv3(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 四级灵石
	public static final Item REIKI_STONE_4= new ReikiStone.Lv4(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 五级灵石
	public static final Item REIKI_STONE_5= new ReikiStone.Lv5(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 玉石原石
	public static final Item JADE_ROUGH_STONE= new JadeRoughStone(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 玉石原石
	public static final Item JADE= new Jade(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 丹瓶
	public static final Item DAN_PING = new DanPing(
		new FabricItemSettings()
			.maxCount(1)
	);
	// 一品回气丹
	public static final Item HUI_QI_DAN = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		1, 20
	);
	// 一品一纹回气丹
	public static final Item HUI_QI_DAN_1 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		2, 20
	);
	// 一品二纹回气丹
	public static final Item HUI_QI_DAN_2 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		3, 20
	);
	// 一品三纹回气丹
	public static final Item HUI_QI_DAN_3 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		4, 30
	);
	// 一品四纹回气丹
	public static final Item HUI_QI_DAN_4 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		5, 30
	);
	// 一品五纹回气丹
	public static final Item HUI_QI_DAN_5 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		6, 30
	);
	// 一品六纹回气丹
	public static final Item HUI_QI_DAN_6 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		7, 40
	);
	// 一品七纹回气丹
	public static final Item HUI_QI_DAN_7 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		8, 40
	);
	// 一品八纹回气丹
	public static final Item HUI_QI_DAN_8 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		9, 40
	);
	// 一品九纹回气丹
	public static final Item HUI_QI_DAN_9 = new HuiQiDan(
		new FabricItemSettings()
			.maxCount(16),
		10, 50
	);
	// 一品吸收丹
	public static final Item XI_SHOU_DAN = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		1, 5
	);
	// 一品一纹吸收丹
	public static final Item XI_SHOU_DAN_1 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		2, 5
	);
	// 一品二纹吸收丹
	public static final Item XI_SHOU_DAN_2 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		3, 10
	);
	// 一品三纹吸收丹
	public static final Item XI_SHOU_DAN_3 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		4, 10
	);
	// 一品四纹吸收丹
	public static final Item XI_SHOU_DAN_4 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		5, 10
	);
	// 一品五纹吸收丹
	public static final Item XI_SHOU_DAN_5 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		6, 20
	);
	// 一品六纹吸收丹
	public static final Item XI_SHOU_DAN_6 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		7, 20
	);
	// 一品七纹吸收丹
	public static final Item XI_SHOU_DAN_7 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		8, 20
	);
	// 一品八纹吸收丹
	public static final Item XI_SHOU_DAN_8 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		9, 30
	);
	// 一品九纹吸收丹
	public static final Item XI_SHOU_DAN_9 = new XiShouDan(
		new FabricItemSettings()
			.maxCount(16),
		10, 30
	);
	// 聚气功
	public static final Item JU_QI_KUNG = new JuQiKung(
		new FabricItemSettings()
			.maxCount(1),
		new JuQiKungSettings(
			new KungFuTiekSettings(JuQiKungSettings::tiek1)
				.setName(new TranslatableText("kung_fu.tiek.1"))
				.setProficiency(10),
			new KungFuTiekSettings(JuQiKungSettings::tiek2)
				.setName(new TranslatableText("kung_fu.tiek.2"))
				.setProficiency(20),
			new KungFuTiekSettings(JuQiKungSettings::tiek3)
				.setName(new TranslatableText("kung_fu.tiek.3"))
				.setProficiency(30)
		)
			.setName(new TranslatableText("item.invincible.ju_qi_kung"))
			.setTexture(new Identifier(MOD_ID, "textures/kung_fu/test.png"))
			.setConsume(25)
			.setComboTime(250)
	);
	// 唤火功
	public static final Item HUAN_HUO_KUNG = new HuanHuoKung(
		new FabricItemSettings()
			.maxCount(1),
		new HuanHuoKungSettings(
			new KungFuTiekSettings(HuanHuoKungSettings::tiek1)
				.setName(new TranslatableText("kung_fu.tiek.1"))
				.setProficiency(10),
			new KungFuTiekSettings(HuanHuoKungSettings::tiek2)
				.setName(new TranslatableText("kung_fu.tiek.2"))
				.setProficiency(20),
			new KungFuTiekSettings(HuanHuoKungSettings::tiek3)
				.setName(new TranslatableText("kung_fu.tiek.3"))
				.setProficiency(30)
		)
			.setName(new TranslatableText("item.invincible.huan_huo_kung"))
			.setTexture(new Identifier(MOD_ID, "textures/kung_fu/test2.png"))
			.setConsume(25)
			.setComboTime(250)
	);
	// 未注灵的灵铁
	public static final Item REIKI_IRON_EMPTY = new ReikiIronEmpty(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 灵铁
	public static final Item REIKI_IRON = new ReikiIron(
		new FabricItemSettings()
			.maxCount(64)
	);
	// 灵铁剑
	public static final Item REIKI_IRON_SWORD = new ReikiIronSword(
		ToolMaterials.IRON, 4, -2.4f, 
			new FabricItemSettings()
				.maxCount(1),
			new ManaSkillSettings(ReikiIronSword::activeSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(100)
				.setSkillCd(5)
	);
	// 灵铁匕首
	public static final Item REIKI_IRON_DAGGER = new ReikiIronDagger(
		ToolMaterials.IRON, 2, 1, 
			new FabricItemSettings()
				.maxCount(1),
			new PostHitManaSkillSettings(ReikiIronDagger::postHitSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(20)
	);
	// 灵铁盾
	public static final Item REIKI_IRON_SHIELD = new ReikiIronShield(
		new FabricItemSettings()
				.maxCount(1),
		new ManaSkillSettings(ReikiIronShield::activeSkill)
			.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
			.setConsume(20)
			.setSkillCd(0)
	);
	// 灵铁镐
	public static final Item REIKI_IRON_PICKAXE = new ReikiIronPickaxe(
		ToolMaterials.IRON, 1, -2.4f,
			new FabricItemSettings()
					.maxCount(1),
			new ManaSkillSettings(ReikiIronPickaxe::activeSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(100)
				.setSkillCd(10)
	);
	// 灵铁锤
	public static final Item REIKI_IRON_HAMMER = new ReikiIronHammer(
		ToolMaterials.IRON, 6, -3.1f,
			new FabricItemSettings()
					.maxCount(1),
			new PostHitManaSkillSettings(ReikiIronHammer::postHitSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(50),
			new PostMineManaSkillSettings(ReikiIronHammer::postMineSkill)
				.setName(new TranslatableText("item.invincible.reiki_iron_sword.skill_1"))
				.setConsume(20)
	);

	// public static final Item TEST = new MinecartItem(AbstractMinecartEntity.Type.COMMAND_BLOCK, new Settings().maxCount(1));
	public static void setAllItemReiki(){
		// 未注灵的灵铁
		((ReikiItem) REIKI_IRON_EMPTY).settings(
			new ReikiSettings()
				.maxReiki(0)
				.injectionReikTarget(6500)
				.maxInjectionReiki(7000)
				.canInjectionReiki(true)
		);
		// 灵铁
		((ReikiItem) REIKI_IRON).settings(
			new ReikiSettings()
				.maxReiki(6500)
		);
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
		
		// 金币
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "gold"), GOLD);
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
		// 丹瓶
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dan_ping"), DAN_PING);
		// 一品回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan"), HUI_QI_DAN);
		// 一品一纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_1"), HUI_QI_DAN_1);
		// 一品二纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_2"), HUI_QI_DAN_2);
		// 一品三纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_3"), HUI_QI_DAN_3);
		// 一品四纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_4"), HUI_QI_DAN_4);
		// 一品五纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_5"), HUI_QI_DAN_5);
		// 一品六纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_6"), HUI_QI_DAN_6);
		// 一品七纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_7"), HUI_QI_DAN_7);
		// 一品八纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_8"), HUI_QI_DAN_8);
		// 一品九纹回气丹
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "hui_qi_dan_9"), HUI_QI_DAN_9);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan"), XI_SHOU_DAN);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_1"), XI_SHOU_DAN_1);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_2"), XI_SHOU_DAN_2);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_3"), XI_SHOU_DAN_3);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_4"), XI_SHOU_DAN_4);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_5"), XI_SHOU_DAN_5);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_6"), XI_SHOU_DAN_6);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_7"), XI_SHOU_DAN_7);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_8"), XI_SHOU_DAN_8);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "xi_shou_dan_9"), XI_SHOU_DAN_9);
		// 聚气功
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "ju_qi_kung"), JU_QI_KUNG);
		// 唤火功
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "huan_huo_kung"), HUAN_HUO_KUNG);
		// 未注灵的灵铁
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_empty"), REIKI_IRON_EMPTY);
		// 灵铁
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron"), REIKI_IRON);
		// 灵铁剑
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_sword"), REIKI_IRON_SWORD);
		// 灵铁匕首
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_dagger"), REIKI_IRON_DAGGER);
		// 灵铁盾
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_shield"), REIKI_IRON_SHIELD);
		// 灵铁镐
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_pickaxe"), REIKI_IRON_PICKAXE);
		// 灵铁锤
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "reiki_iron_hammer"), REIKI_IRON_HAMMER);
		// Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test"), TEST);
    }
}
