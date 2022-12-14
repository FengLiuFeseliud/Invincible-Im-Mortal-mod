package fengliu.invincible;

import org.lwjgl.glfw.GLFW;

import fengliu.invincible.api.Key;
import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.client.block.entity.renderer.ModBlockEntityRenderers;
import fengliu.invincible.networking.ModMessage;
import fengliu.invincible.particle.ModParticle;
import fengliu.invincible.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class invincibleClient implements ClientModInitializer  {
	
    @Override
	public void onInitializeClient() {
		ModMessage.registerS2CPackets();
		
		ModParticle.registerClientAllParticles();

		ModBlocks.setAllBlockRenderLayerMap();

		ModBlockEntityRenderers.registerAllBlockEntityRenderers();

		ModScreens.registerAllScreen();

	
		// EntityRendererRegistry.INSTANCE.register((EntityType<? extends CanSitEntity>) ModEntitys.CanSitEntity, (context) -> {
		// 	return new CanSitEntityRenderer(context);
		// });
		
		// P键提升
		KeyBinding cultivation_up  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.cultivation_up", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, "key.invincible"));
		// O键修炼
		KeyBinding reiki_practice = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.reiki_practice", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, "key.invincible"));
		// I键炼化
		KeyBinding cultivation_item  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.cultivation_item", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, "key.invincible"));
		// U键查看修为
		KeyBinding cultivation_info  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.cultivation_info", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "key.invincible"));
		// !测试! 消耗所有灵力
		KeyBinding clear_mana = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.clear_mana", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "key.invincible"));
		// T键切换物品技能
		KeyBinding set_ues_skill = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.set_ues_skill", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_T, "key.invincible"));
		// R键切换功法
		KeyBinding set_ues_kung_fu = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.set_ues_kung_fu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.invincible"));

		KeyBinding set_ues_kung_fu_group = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.set_ues_kung_fu_group", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.invincible"));

		KeyBinding ues_kung_fu = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.invincible.ues_kung_fu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "key.invincible"));
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(client.world == null){
				// 不在游戏中
				return;
			}

			while (reiki_practice.wasPressed()) {
				Key.reiki_practice(client);
			}

			while(cultivation_item.wasPressed()) {
				Key.cultivation_item(client);
			}

			if(cultivation_up.wasPressed()) {
				Key.cultivation_up(client);
			}
			
			if(cultivation_info.wasPressed()) {
				Key.cultivation_info(client);
			}

			if(clear_mana.wasPressed()) {
				Key.clear_mana(client);
			}

			if(set_ues_skill.wasPressed()) {
				Key.set_ues_skill(client);
			}

			if(set_ues_kung_fu.wasPressed()) {
				Key.set_ues_kung_fu(client);
			}

			if(set_ues_kung_fu_group.wasPressed()) {
				Key.set_ues_kung_fu_group(client);
			}

			if(ues_kung_fu.wasPressed()) {
				Key.ues_kung_fu(client);
			}
			
		});
	}
}
