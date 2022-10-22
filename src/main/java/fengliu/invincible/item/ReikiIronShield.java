package fengliu.invincible.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ReikiIronShield extends ManaShield {

    public ReikiIronShield(Settings settings, ManaSkillSettings ...manaSkillsSettings) {
        super(settings, manaSkillsSettings);
    }

    public static boolean activeSkill(World world, PlayerEntity user, Hand hand) {
        user.setAbsorptionAmount(2);
        return true;
    }
    
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_sword.tooltip"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_shield.tooltip_2"));
        tooltip.add(new TranslatableText("item.invincible.reiki_iron_shield.tooltip_3"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
