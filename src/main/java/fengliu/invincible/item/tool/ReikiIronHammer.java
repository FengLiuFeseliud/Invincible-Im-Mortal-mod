package fengliu.invincible.item.tool;

import java.util.List;
import java.util.Map;

import fengliu.invincible.item.ManaHammer;
import fengliu.invincible.util.CheckStructure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReikiIronHammer extends ManaHammer {

    public ReikiIronHammer(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostHitManaSkillSettings postHitManaSkillSettings, PostMineManaSkillSettings postMineManaSkillSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, postHitManaSkillSettings, postMineManaSkillSettings);
    }

    public static boolean postHitSkill(ItemStack stack, LivingEntity target, LivingEntity attacker){
        attacker.getMainHandStack().getNbt().putFloat("invincible.player_attack_damage", 5);
        return true;
    }
    
    public static boolean postMineSkill(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner){
        if(!(miner instanceof PlayerEntity)){
            return false;
        }

        PlayerEntity player = (PlayerEntity) miner;
        Map<BlockPos, BlockState> scopeAllBlock = CheckStructure.getLivingEntityLookFacingScopeAllBlock(world, player, pos, 0, 3, 1);
        if(scopeAllBlock.size() == 0){
            return false;
        }

        /**
         * 破坏可以被该工具采集的方块并掉落
         */
        scopeAllBlock.forEach((bpos, bs) -> {
            if(!bs.isAir() && player.canHarvest(bs)){
                state.getBlock().onBreak(world, pos, state, player);

                BlockEntity blockEntity = world.getBlockState(bpos).hasBlockEntity() ? world.getBlockEntity(bpos) : null;
                for(ItemStack breakStack: Block.getDroppedStacks(bs, (ServerWorld) world, bpos, blockEntity, player, stack)){
                    world.spawnEntity(new ItemEntity(world, bpos.getX(), bpos.getY(), bpos.getZ(), breakStack));
                }

                world.setBlockState(bpos, Blocks.AIR.getDefaultState());
            }
        });
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_hammer.tooltip_2"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_hammer.tooltip_3"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_hammer.tooltip_4"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_hammer.tooltip_5"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    
}
