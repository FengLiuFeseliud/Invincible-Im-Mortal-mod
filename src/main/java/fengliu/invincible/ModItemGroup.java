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
				stacks.add(new ItemStack(ModItems.JADE));
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
			.icon(() -> new ItemStack(ModItems.KUNG_FU))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.KUNG_FU));
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
			})
			.build();

    public static void allItemGroupBuild(){
        
    }
}
