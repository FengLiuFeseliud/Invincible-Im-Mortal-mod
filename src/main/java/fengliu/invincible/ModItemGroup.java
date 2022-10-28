package fengliu.invincible;

import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.item.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    // 材料组
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "item_group"))
			.icon(() -> new ItemStack(ModItems.REIKI_STONE_3))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.JADE_ROUGH_STONE));
				stacks.add(new ItemStack(ModItems.REIKI_STONE_0));
				stacks.add(new ItemStack(ModItems.REIKI_STONE_1));
				stacks.add(new ItemStack(ModItems.REIKI_STONE_2));
				stacks.add(new ItemStack(ModItems.REIKI_STONE_3));
				stacks.add(new ItemStack(ModItems.REIKI_STONE_4));
				stacks.add(new ItemStack(ModItems.REIKI_STONE_5));
			})
			.build();
	// 方块组
	public static final ItemGroup BLOCK_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "block_group"))
			.icon(() -> new ItemStack(ModBlocks.REIKI_STONE_BRICKS))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModBlocks.REIKI_STONE_BRICKS));
				stacks.add(new ItemStack(ModBlocks.JADE_ROUGH_STONE_ORE));
				stacks.add(new ItemStack(ModBlocks.REIKI_STONE_ORE_1));
				stacks.add(new ItemStack(ModBlocks.REIKI_STONE_ORE_2));
				stacks.add(new ItemStack(ModBlocks.REIKI_STONE_ORE_3));
				stacks.add(new ItemStack(ModBlocks.REIKI_STONE_ORE_4));
				stacks.add(new ItemStack(ModBlocks.DEEPSLATE_JADE_ROUGH_STONE_ORE));
				stacks.add(new ItemStack(ModBlocks.DEEPSLATE_REIKI_STONE_ORE_1));
				stacks.add(new ItemStack(ModBlocks.DEEPSLATE_REIKI_STONE_ORE_2));
				stacks.add(new ItemStack(ModBlocks.DEEPSLATE_REIKI_STONE_ORE_3));
				stacks.add(new ItemStack(ModBlocks.DEEPSLATE_REIKI_STONE_ORE_4));
			})
			.build();
	// 功法组
	public static final ItemGroup KUNG_FU_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "kung_fu_group"))
			.icon(() -> new ItemStack(ModItems.JU_QI_KUNG))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.JU_QI_KUNG));
				stacks.add(new ItemStack(ModItems.HUAN_HUO_KUNG));
			})
			.build();
	// 阵法组
	public static final ItemGroup ZHEN_FU_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "zhen_fu_group"))
			.icon(() -> new ItemStack(ModBlocks.ZHEN_YAN_1))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModBlocks.ZHEN_YAN_1));
			})
			.build();
	// 炼器组
	public static final ItemGroup LIAN_QI_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "lian_qi_group"))
			.icon(() -> new ItemStack(ModBlocks.ANGLE_GRINDER))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModBlocks.ANGLE_GRINDER));
				stacks.add(new ItemStack(ModBlocks.INJECTION_REIKI_STANDS));
				stacks.add(new ItemStack(ModItems.JADE));
				stacks.add(new ItemStack(ModItems.REIKI_IRON_EMPTY));
				stacks.add(new ItemStack(ModItems.REIKI_IRON));
			})
			.build();
	// 法器组
	public static final ItemGroup FA_QI_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "fa_qi_group"))
			.icon(() -> new ItemStack(ModItems.REIKI_IRON_SWORD))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.REIKI_IRON_PICKAXE));
				stacks.add(new ItemStack(ModItems.REIKI_IRON_HAMMER));
				stacks.add(new ItemStack(ModItems.REIKI_IRON_SWORD));
				stacks.add(new ItemStack(ModItems.REIKI_IRON_DAGGER));
				stacks.add(new ItemStack(ModItems.REIKI_IRON_SHIELD));
			})
			.build();
	// 炼丹组
	public static final ItemGroup LIAN_DAN_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "lian_dan_group"))
			.icon(() -> new ItemStack(ModBlocks.DAN_LU))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModBlocks.DAN_LU));
			})
			.build();
	// 丹药组
	public static final ItemGroup DAN_YAO_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "dan_yao_group"))
			.icon(() -> new ItemStack(ModItems.HUI_QI_DAN_1))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.DAN_PING));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_1));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_2));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_3));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_4));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_5));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_6));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_7));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_8));
				stacks.add(new ItemStack(ModItems.HUI_QI_DAN_9));
			})
			.build();
	// 草药组
	public static final ItemGroup CAO_YAO_GROUP = FabricItemGroupBuilder.create(
		new Identifier(MOD_ID, "cao_yao_group"))
			.icon(() -> new ItemStack(ModBlocks.HUI_QI_CAO))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModBlocks.HUI_QI_CAO));
			})
			.build();

    public static void allItemGroupBuild(){
        
    }
}
