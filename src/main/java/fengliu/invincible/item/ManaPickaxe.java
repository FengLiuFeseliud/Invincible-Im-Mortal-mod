package fengliu.invincible.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ManaPickaxe extends PickaxeItem implements ManaSkillsItem {
    private ManaSkillSettings[] ManaSkillSettings;
    private PostMineManaSkillSettings PostMineManaSkillSettings;

    public ManaPickaxe(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ManaSkillSettings = manaSkillsSettings;
    }

    public ManaPickaxe(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, PostMineManaSkillSettings postMineManaSkillSettings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        PostMineManaSkillSettings = postMineManaSkillSettings;
        ManaSkillSettings = manaSkillsSettings;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient || ManaSkillSettings.length == 0){
            return super.use(world, user, hand);
        }
        ManaSkillSettings uesSkill = ManaSkillSettings[getUesSkill(user, ManaSkillSettings)];

        if(!tryUesSkill(uesSkill, this, user)){
            return super.use(world, user, hand);
        }

        if(uesSkill.Skill.function(world, user, hand)){
            consumeMana(uesSkill, user);
        }
        return super.use(world, user, hand);
    }

    public void setManaSkillSettings(PlayerEntity user){
        if(ManaSkillSettings == null){
            return;
        }
        
        setUesSkill(user, ManaSkillSettings, getUesSkill(user, ManaSkillSettings) + 1);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(PostMineManaSkillSettings == null){
            return super.postMine(stack, world, state, pos, miner);
        }
        
        if(!tryUesSkill(PostMineManaSkillSettings, this, (PlayerEntity) miner)){
            return super.postMine(stack, world, state, pos, miner);
        }

        if(PostMineManaSkillSettings.PostMineSkill.function(stack, world, state, pos, miner)){
            consumeMana(PostMineManaSkillSettings, (PlayerEntity) miner);
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
