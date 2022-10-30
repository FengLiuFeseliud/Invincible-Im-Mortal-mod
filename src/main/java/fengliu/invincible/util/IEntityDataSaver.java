package fengliu.invincible.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    NbtCompound getPersistentData();
    void writePersistentData(NbtCompound nbt);
    CultivationCilentData getCilentCultivationData();
    CultivationServerData getServerCultivationData();
    KungFuCilentData getKungFuCilentData();
    KungFuServerData getKungFuServerData();
    LianDanServerData getLianDanServerData();
}
