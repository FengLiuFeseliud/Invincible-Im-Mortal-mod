package fengliu.invincible.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    NbtCompound getPersistentData();
    CultivationData getCultivationData();
}
