package fengliu.invincible.entity;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntitys {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    public static final EntityType<Reiki> REIKI = Registry.register(
        Registry.ENTITY_TYPE,
        new Identifier(MOD_ID, "reiki"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, Reiki::new)
            .dimensions(EntityDimensions.fixed(0.75f, 0.75f)
        ).build()
    );

    // public static final EntityType<Entity> CanSitEntity = Registry.register(
    //     Registry.ENTITY_TYPE,
    //     new Identifier(MOD_ID, "chair_entity"),
    //     FabricEntityTypeBuilder.create(SpawnGroup.MISC, CanSitEntity::new)
    //         .dimensions(EntityDimensions.fixed(0.75f, 0.75f)
    //     ).build()
    // );

    public static void registryAllEntitys(){
        
    }
}
