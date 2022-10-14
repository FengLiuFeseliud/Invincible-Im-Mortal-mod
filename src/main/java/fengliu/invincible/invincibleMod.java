package fengliu.invincible;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengliu.invincible.block.ModBlocks;
import fengliu.invincible.entity.ModEntitys;
import fengliu.invincible.entity.block.ModBlockEntitys;
import fengliu.invincible.event.ModServerTickEvent;
import fengliu.invincible.item.ModItems;
import fengliu.invincible.item.block.ModBlockItems;
import fengliu.invincible.networking.ModMessage;
import fengliu.invincible.particle.ModParticle;
import fengliu.invincible.screen.handler.ModScreenHandlers;
import fengliu.invincible.world.ModWorlds;

public class invincibleMod implements ModInitializer {
	public static final String MOD_ID = "invincible";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerAllItem();

		ModBlocks.registerAllBlock();
		ModBlockItems.registerAllBlockItem();
		ModBlockEntitys.registryAllBlockEntitys();

		ModEntitys.registryAllEntitys();

		ModParticle.registerAllParticles();

		ModScreenHandlers.registerAllScreenHandlers();

		ModWorlds.registryAllWorlds();

		ModItemGroup.allItemGroupBuild();

		ModMessage.registerC2SPackets();

		ModServerTickEvent.register();

	}

}
