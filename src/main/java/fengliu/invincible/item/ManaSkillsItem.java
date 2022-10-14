package fengliu.invincible.item;

import fengliu.invincible.util.CultivationServerData;
import fengliu.invincible.util.IEntityDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * 真元技能物品
 */
public interface ManaSkillsItem{

    /**
     * 使用真元技能时真元不足
     * @param player 玩家
     * @param skillsSettings 真元技能
     */
    default void manaInsufficient(PlayerEntity player, ManaSkillSettings skillsSettings){
        player.sendMessage(new TranslatableText("info.invincible.mana_insufficient", skillsSettings.Name.getString()), true);
    }

    /**
     * 获取当前物品选中的真元技能
     * @param user 玩家
     * @param ManaSkillSettings 真元技能列表
     * @return
     */
    default int getUesSkill(PlayerEntity user, ManaSkillSettings[] ManaSkillSettings){
        NbtCompound nbt = user.getStackInHand(user.getActiveHand()).getNbt();

        int skillIndex;
        if(!nbt.contains("invincible.skill_index")){
            skillIndex = 0;

            nbt.putInt("invincible.skill_index", skillIndex);
            user.getStackInHand(user.getActiveHand()).setNbt(nbt);
        }else{
            skillIndex = nbt.getInt("invincible.skill_index");
        }

        if(skillIndex < 0){
            skillIndex = ManaSkillSettings.length - 1;
        }

        if(skillIndex > ManaSkillSettings.length - 1){
            skillIndex = ManaSkillSettings.length - 1;
        }

        return skillIndex;
    }

    /**
     * 设置当前物品选中的真元技能 并设置冷却
     * @param user  玩家
     * @param ManaSkillSettings 真元技能列表
     * @param skillIndex 技能索引
     */
    default void setUesSkill(PlayerEntity user, ManaSkillSettings[] ManaSkillSettings, int skillIndex){
        ItemStack stack = user.getStackInHand(user.getActiveHand());

        if(skillIndex < 0){
            skillIndex = ManaSkillSettings.length - 1;
        }

        if(skillIndex > ManaSkillSettings.length - 1){
            skillIndex = 0;
        }
        user.getItemCooldownManager().set(stack.getItem(), ManaSkillSettings[skillIndex].SkillCD);
        stack.getNbt().putInt("invincible.skill_index", skillIndex);
    }

    /**
     * 尝试使用当前物品选中的真元技能
     * @param skillsSettings 真元技能
     * @param item 物品
     * @param player 玩家
     * @return 使用成功返回 true 并设置冷却
     */
    default boolean tryUesSkill(ManaSkillSettings skillsSettings, Item item, PlayerEntity player){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        switch (skillsSettings.canUesSkill(cultivationData.getMana(), item)) {
            case SKILL_UES:
                if(skillsSettings.SkillCD != 0){
                    player.getItemCooldownManager().set(item, skillsSettings.SkillCD);
                }
                cultivationData.consumeMana(skillsSettings.Consume);
                return true;
            case MANA_INSUFFICIENT:
                manaInsufficient(player, skillsSettings);
                return false;
            default:
                return false;
        }
    }

    /**
     * 普通真元技能 (右键使用) 设置
     */
    public class ManaSkillSettings {
        public TranslatableText Name = new TranslatableText("info.skill_default_name");
        public int Consume = 0;
        public int SkillCD = 0;
        public final Skill Skill;
        
        /**
         * 设置技能效果
         * @param skillFunction 技能函数
         */
        public ManaSkillSettings(Skill skillFunction){
            Skill = skillFunction;
        }

        public ManaSkillSettings setName(TranslatableText name){
            Name = name;
            return this;
        }
        
        public ManaSkillSettings setConsume(int consume){
            Consume = consume;
            return this;
        }
        
        /**
         * 设置冷却 (秒)
         * @param skillCD 冷却
         */
        public ManaSkillSettings setSkillCd(int skillCD){
            // 转换为 tick 一秒 20 tick
            SkillCD = skillCD * 20;
            return this;
        }
        
        /**
         * 判断是否可以使用技能
         * @param manaAll 当前可用的所有真元值
         * @param item 物品
         * @return 返回技能状态枚举值
         */
        public SkillState canUesSkill(int manaAll, Item item){
            if(Consume > manaAll){
                return SkillState.MANA_INSUFFICIENT;
            }
    
            return SkillState.SKILL_UES;
        }
    }

    /**
     * 攻击命中时触发的真元技能设置
     * 不能设置冷却
     */
    public class PostHitManaSkillSettings extends ManaSkillSettings{
        public final PostHitSkill PostHitSkill;

        /**
         * 设置技能效果
         * @param skillFunction 技能函数
         */
        public PostHitManaSkillSettings(PostHitSkill skillFunction) {
            super(null);
            PostHitSkill = skillFunction;
        }

        @Override
        public PostHitManaSkillSettings setName(TranslatableText name){
            Name = name;
            return this;
        }
        
        @Override
        public PostHitManaSkillSettings setConsume(int consume){
            Consume = consume;
            return this;
        }

        @Override
        public PostHitManaSkillSettings setSkillCd(int skillCD) {
            return (PostHitManaSkillSettings) super.setSkillCd(0);
        }
    }

    enum SkillState {
        SKILL_UES, MANA_INSUFFICIENT
    }

    /**
     * 普通真元技能函数
     */
    interface Skill {
        /**
         * 技能函数
         * @param world 当前世界
         * @param player 玩家
         * @param hand 主手 / 副手
         * @return
         */
        boolean function(World world, PlayerEntity player, Hand hand);
    }

    /**
     * 攻击命中真元技能函数
     */
    interface PostHitSkill {
        /**
         * 技能函数
         * @param stack 物品格 (物品栏的一个格子)
         * @param target 攻击目标
         * @param attacker 攻击者
         * @return
         */
        boolean function(ItemStack stack, LivingEntity target, LivingEntity attacker);
    }
}