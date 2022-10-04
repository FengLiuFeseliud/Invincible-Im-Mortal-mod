package fengliu.invincible.networking;

import fengliu.invincible.invincibleMod;
import fengliu.invincible.networking.packets.CultivationClientPackets;
import fengliu.invincible.networking.packets.CultivationServerPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessage {
    private static final String MOD_ID = invincibleMod.MOD_ID;

    public static final Identifier REIKI_PRACTICE = new Identifier(MOD_ID, "reiki_practice");
    public static final Identifier CULTIVATION_UP = new Identifier(MOD_ID, "cultivation_up");
    public static final Identifier CULTIVATION_INFO = new Identifier(MOD_ID, "cultivation_info");
    public static final Identifier CLEAR_MANA = new Identifier(MOD_ID, "clear_mana");
    public static final Identifier SYNC_DATA = new Identifier(MOD_ID, "sync_data");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(REIKI_PRACTICE, CultivationServerPackets::reiki_practice);
        ServerPlayNetworking.registerGlobalReceiver(CULTIVATION_UP, CultivationServerPackets::cultivation_up);
        ServerPlayNetworking.registerGlobalReceiver(CULTIVATION_INFO, CultivationServerPackets::cultivation_info);
        ServerPlayNetworking.registerGlobalReceiver(CLEAR_MANA, CultivationServerPackets::clear_mana);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SYNC_DATA, CultivationClientPackets::syncData);
    }
}
