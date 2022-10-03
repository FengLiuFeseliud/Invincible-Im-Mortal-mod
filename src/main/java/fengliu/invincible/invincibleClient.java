package fengliu.invincible;

import org.lwjgl.glfw.GLFW;

import fengliu.invincible.api.Key;
import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.screen.Angle_Grinder_Screen;
import fengliu.invincible.screen.Zhen_Yan_Screen;
import fengliu.invincible.screen.handler.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.world.ServerWorld;

public class invincibleClient implements ClientModInitializer  {
	
    @Override
	public void onInitializeClient() {
		// 角磨机 UI
		HandledScreens.register(ModScreenHandlers.ANGLE_GRINDER_SCREENHANDLER, Angle_Grinder_Screen::new);
		// 一阶阵眼 UI
		HandledScreens.register(ModScreenHandlers.ZHEN_YAN_1_SCREENHANDLER, Zhen_Yan_Screen.Lv1::new);

		
		// 角磨机 渲染层设置
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ANGLE_GRINDER, RenderLayer.getCutout());

		
		// P键修炼
		KeyBinding reiki_practice = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.reiki_practice", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "key.invincible"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(client.world == null){
				// 不在游戏中
				return;
			}
			// 获取当前玩家所在服务器世界维度
			ServerWorld serverWorld = client.getServer().getWorld(client.world.getRegistryKey());

			while (reiki_practice.wasPressed()) {
				Key.reiki_practice(client.world, serverWorld, client.player);
			}
			
		});
	}
}
