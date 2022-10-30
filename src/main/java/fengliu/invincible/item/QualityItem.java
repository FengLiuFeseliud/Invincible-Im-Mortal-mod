package fengliu.invincible.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

public interface QualityItem {
    int getLevel();
    int getExp();

    void setQuality(Map<Integer, Item> hashMap);
    Map<Integer, Item> getQualityItems();

    default public boolean canOut(int playerLevel){
        return getLevel() <= playerLevel;
    }

    default public int getNewLianDanExp(int playerExp){
        return playerExp + getExp();
    }

    default public Item getQualityItem(int playerExp){
        Map<Integer, Item> qualityItems = getQualityItems();
        if(qualityItems == null){
            setQuality(new HashMap<>());
            qualityItems = getQualityItems();
            if(qualityItems == null){
                return null;
            }
        }

        int[] oldExp = {0};
        qualityItems.forEach((exp, danYan) -> {
            if(playerExp > exp && exp >= oldExp[0]){
                oldExp[0] = exp;
            }
        });
        return qualityItems.get(oldExp[0]);
    }
    
}
