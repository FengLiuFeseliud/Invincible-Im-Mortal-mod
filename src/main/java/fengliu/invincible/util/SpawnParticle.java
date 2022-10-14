package fengliu.invincible.util;

import fengliu.invincible.particle.ModParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpawnParticle {
    
    /**
     * 在玩家身边生成吸收灵气的粒子效果
     * @param player 玩家
     */
    public static void absordRaiki(PlayerEntity player){
        World world =  player.getWorld();
        Vec3d pos = player.getPos();

        for(float spawn_pos = 0.0f; spawn_pos < 1.0f; spawn_pos += 0.5){
            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX(), pos.getY() + spawn_pos, pos.getZ(), 0, 0, 0
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX() - 2 + spawn_pos, pos.getY() + spawn_pos, pos.getZ(), +0.08d ,0.03d, 0
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX() + 2 - spawn_pos, pos.getY() + spawn_pos, pos.getZ(), -0.08d ,0.03d, 0
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX(), pos.getY() + spawn_pos, pos.getZ() - 2 + spawn_pos, 0,0.03d, +0.08d 
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX(), pos.getY() + spawn_pos, pos.getZ() + 2 - spawn_pos, 0 ,0.03d, -0.08d 
            );
        }

        for(float spawn_pos = 0.0f; spawn_pos < 1.5f; spawn_pos += 0.5){
            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX() + 2 - spawn_pos, pos.getY() + spawn_pos, pos.getZ() + 2 - spawn_pos, -0.05d ,0.025d, -0.05d
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX() + 2 - spawn_pos, pos.getY() + spawn_pos, pos.getZ() - 2 + spawn_pos, -0.05d ,0.025d, +0.05d
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX() - 2 + spawn_pos, pos.getY() + spawn_pos, pos.getZ() - 2 + spawn_pos, +0.05d ,0.025d, +0.05d
            );

            world.addParticle(ModParticle.REIKI_PARTICLE,  false, 
                pos.getX() - 2 + spawn_pos, pos.getY() + spawn_pos, pos.getZ() + 2 - spawn_pos, +0.05d ,0.025d, -0.05d
            );
        }

    }
}
