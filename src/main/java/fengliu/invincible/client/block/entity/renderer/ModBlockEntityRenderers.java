package fengliu.invincible.client.block.entity.renderer;

import fengliu.invincible.block.entity.ModBlockEntitys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ModBlockEntityRenderers {
    
    public static void registerAllBlockEntityRenderers(){
        BlockEntityRendererRegistry.register(ModBlockEntitys.BOOK_SHELF_ENTITY, BookShelfRenderer::new);
    }
}
