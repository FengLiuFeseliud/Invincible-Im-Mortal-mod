package fengliu.invincible.block.entity;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntitys {
    private static final String MOD_ID = invincibleMod.MOD_ID;
    // 书架
	public static BlockEntityType<BookShelfEntity> BOOK_SHELF_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, 
        new Identifier(MOD_ID, "book_shelf_entity"), 
        FabricBlockEntityTypeBuilder.create(BookShelfEntity::new, ModBlocks.BOOK_SHELF).build(null)
    );
    // 角磨机
	public static BlockEntityType<AngleGrinderEntity> ANGLE_GRINDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, 
        new Identifier(MOD_ID, "angle_grinder_entity"), 
        FabricBlockEntityTypeBuilder.create(AngleGrinderEntity::new, ModBlocks.ANGLE_GRINDER).build(null)
    );
    // 注灵台
    public static BlockEntityType<InjectionReikiStandsEntity> INJECTION_REIKI_STANDS_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, 
        new Identifier(MOD_ID, "injection_reiki_stands_entity"), 
        FabricBlockEntityTypeBuilder.create(InjectionReikiStandsEntity::new, ModBlocks.INJECTION_REIKI_STANDS).build(null)
    );
    // 注灵台
    public static BlockEntityType<DanLuEntity> DAN_LU_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, 
        new Identifier(MOD_ID, "dan_lu_entity"), 
        FabricBlockEntityTypeBuilder.create(DanLuEntity::new, ModBlocks.DAN_LU).build(null)
    );
    // 一阶阵眼
    public static BlockEntityType<ZhenYanLv1Entity> ZHEN_YAN_1_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, 
        new Identifier(MOD_ID, "zhen_yan_lv1_entity"), 
        FabricBlockEntityTypeBuilder.create(ZhenYanLv1Entity::new, ModBlocks.ZHEN_YAN_1).build(null)
    );
    
    public static void registryAllBlockEntitys(){
        
    }
}
