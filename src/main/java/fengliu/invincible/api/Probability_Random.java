package fengliu.invincible.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Probability_Random {

    public static Random_Item random(Random_Item[] randomItem){
        List<Double> all_probability = allItemProbability(randomItem);
        double nextDouble = Math.random();

        all_probability.add(nextDouble);
        Collections.sort(all_probability);
        return randomItem[all_probability.indexOf(nextDouble)];
    }

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

    public static class Random_Item {
        private double PROBABILITY;
        private Object ITEM;

        public Random_Item(double probability, Object Item){
            PROBABILITY = Math.round(probability * 100) * 0.01;
            ITEM = Item;
        }

        public double getProbability(){
            return PROBABILITY;
        }

        public Object getItem(){
            if(!(ITEM instanceof Random_Item[])){
                return ITEM;
            }

            return random((Random_Item[]) ITEM);
        }
    }
}
