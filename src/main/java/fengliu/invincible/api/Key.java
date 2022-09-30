package fengliu.invincible.api;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.entity.Reiki;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Key {

    public static void reiki_practice(World world, PlayerEntity player){
        Vec3d pos = player.getPos();

        Reiki reiki = new Reiki(invincibleMod.REIKI, world);
        reiki.setPosition(pos);

        player.sendMessage(new LiteralText(reiki.toString()), false);
        world.spawnEntity(reiki);
    }
    
}
