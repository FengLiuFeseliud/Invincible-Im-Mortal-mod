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

public interface ManaSkillsItem{
    default void manaInsufficient(PlayerEntity player, ManaSkillSettings skillsSettings){
        player.sendMessage(new TranslatableText("info.invincible.mana_insufficient", skillsSettings.Name.getString()), true);
    }

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

    default boolean tryUesSkill(ManaSkillSettings skillsSettings, Item item, PlayerEntity player){
        CultivationServerData cultivationData = ((IEntityDataSaver) player).getServerCultivationData();
        switch (skillsSettings.canUesSkill(cultivationData.getMana(), item, player)) {
            case SKILL_UES:
                player.getItemCooldownManager().set(item, skillsSettings.SkillCD);
                cultivationData.consumeMana(skillsSettings.Consume);
                return true;
            case MANA_INSUFFICIENT:
                manaInsufficient(player, skillsSettings);
                return false;
            default:
                return false;
        }
    }

    public class ManaSkillSettings {
        public TranslatableText Name = new TranslatableText("info.skill_default_name");
        public int Consume = 0;
        public int SkillCD = 0;
        public final Skill Skill;
    
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

        public ManaSkillSettings setSkillCd(int skillCD){
            SkillCD = skillCD * 20;
            return this;
        }
    
        public SkillState canUesSkill(int manaAll, Item item, PlayerEntity player){
            if(Consume > manaAll){
                return SkillState.MANA_INSUFFICIENT;
            }
    
            return SkillState.SKILL_UES;
        }
    }

    public class PostHitManaSkillSettings extends ManaSkillSettings{
        public final PostHitSkill PostHitSkill;

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

    interface Skill {
        boolean function(World world, PlayerEntity player, Hand hand);
    }

    interface PostHitSkill {
        boolean function(ItemStack stack, LivingEntity target, LivingEntity attacker);
    }
}