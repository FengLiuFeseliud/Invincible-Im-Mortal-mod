package fengliu.invincible.item;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fengliu.invincible.util.scheduledExecutor;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Reiki_iron_sword extends ManaSword{
    public Reiki_iron_sword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(toolMaterial, attackDamage, attackSpeed, settings, manaSkillsSettings);
    }

    private static void clearAllNbt(PlayerEntity user){
        user.getStackInHand(user.getActiveHand()).getNbt().remove("invincible.player_attack_damage");
    }

    public static boolean activeSkill(World world, PlayerEntity user, Hand hand) {
        user.getStackInHand(user.getActiveHand()).getNbt().putFloat("invincible.player_attack_damage", 10);
        scheduledExecutor.scheduledExecutorService.schedule(() ->  clearAllNbt(user), (long) 2, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip_2"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip_3"));
        super.appendTooltip(stack, world, tooltip, context);
    }

}
