package fengliu.invincible.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class LivingEntityInfo {
    
    /**
     * 获取实体面向六个方向 (东南西北上下)
     * @param entity 实体
     * @return 方向
     */
    public static Facing facing(LivingEntity entity){
        float pitch = entity.getPitch();
        if(pitch > 80.0f){
            return Facing.DOWN;
        }

        if(pitch < -80.0f){
            return Facing.UP;
        }

        int key = MathHelper.floor((double)(entity.getYaw() * 4.0F / 360.0F) + 0.5D) & 3;
        return switch (key) {
            case 0 ->
                Facing.SOUTH;
            case 1 ->
                Facing.WEST;
            case 2 ->
                Facing.NORTH;
            case 3 ->
                Facing.EAST;
            default ->
                Facing.SOUTH;
        };
    }

    enum Facing{
        SOUTH, WEST, NORTH, EAST, UP, DOWN, value
    }
}
