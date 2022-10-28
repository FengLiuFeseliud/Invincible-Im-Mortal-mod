package fengliu.invincible.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CanSitEntity extends MinecartEntity {
    public CanSitEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }
    public CanSitEntity(World world, double x, double y, double z) {
        // this(ModEntitys.CanSitEntity, world);
        this(null, world);
        this.updatePosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }
    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (this.hasPassengers()) {
            return ActionResult.PASS;
        } else if (!this.world.isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public void removeAllPassengers() {
        for(int i = this.getPassengerList().size() - 1; i >= 0; --i) {
            ((Entity)this.getPassengerList().get(i)).stopRiding();
        }
        this.remove(RemovalReason.DISCARDED);

    }
}