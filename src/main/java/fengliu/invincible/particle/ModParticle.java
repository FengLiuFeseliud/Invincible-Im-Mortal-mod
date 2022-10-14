package fengliu.invincible.particle;

import fengliu.invincible.invincibleMod;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticle {
    public static final String MOD_ID = invincibleMod.MOD_ID;

    public static final DefaultParticleType REIKI_PARTICLE= FabricParticleTypes.simple();

    public static void registerAllParticles(){
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "reiki_particle"), REIKI_PARTICLE);
    }

    public static void registerClientAllParticles(){
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MOD_ID, "particle/reiki"));
        }));

        ParticleFactoryRegistry.getInstance().register(REIKI_PARTICLE, FlameParticle.Factory::new);
    }
}
