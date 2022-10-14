package fengliu.invincible.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实现一个简单的概率随机
 */
public class Probability_Random {

    /**
     * 进行随机
     * @param randomItem 随机项目列表
     * @return 随机到的项目
     */
    public static Random_Item random(Random_Item[] randomItem){
        List<Double> all_probability = allItemProbability(randomItem);
        double nextDouble = Math.random();

        all_probability.add(nextDouble);
        Collections.sort(all_probability);
        return randomItem[all_probability.indexOf(nextDouble)];
    }

    /**
     * 获取所有项目概率
     * @param randomItem 随机项目列表
     * @return 所有项目概率
     */
    public static List<Double> allItemProbability(Random_Item[] randomItem){
        List<Double> all_probability = new ArrayList<>();

        double sumRate = 0d;
        for(Random_Item item : randomItem){
            sumRate += item.getProbability();
        }

        double tempSumRate = 0d;
        for(Random_Item item : randomItem){
            tempSumRate += item.getProbability();
            all_probability.add(tempSumRate / sumRate);
        }

        return all_probability;
    }

    /**
     * 随机项目
     */
    public static class Random_Item {
        private double PROBABILITY;
        private Object ITEM;

        /**
         * 随机项目
         * @param probability 概率
         * @param Item 项目内容
         */
        public Random_Item(double probability, Object Item){
            PROBABILITY = Math.round(probability * 100) * 0.01;
            ITEM = Item;
        }

        public double getProbability(){
            return PROBABILITY;
        }
        
        /**
         * 获取项目内容, 如果项目内容为随机项目列表继续随机项目内容
         * @return 项目内容
         */
        public Object getItem(){
            if(!(ITEM instanceof Random_Item[])){
                return ITEM;
            }

            return random((Random_Item[]) ITEM);
        }
    }
}
