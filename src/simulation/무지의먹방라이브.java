package simulation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class 무지의먹방라이브 {
    private class Food implements Comparable<Food> {
        private int index;
        private long amount;

        public Food(int index, long amount) {
            this.index = index;
            this.amount = amount;
        }

        @Override
        public int compareTo(Food o) {
            if (this.amount == o.amount) return this.index - o.index;
            return (int) (this.amount - o.amount);
        }
    }

    public int solution(int[] food_times, long k) {
        PriorityQueue<Food> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i < food_times.length; i++) {
            priorityQueue.offer(new Food(i + 1, food_times[i]));
        }

        long prevAmount = 0;
        long extra = k;

        while (!priorityQueue.isEmpty()) {
            Food food = priorityQueue.peek();

            long currFoodAmount = food.amount - prevAmount;
            long currTotalAmount = currFoodAmount * priorityQueue.size();

            if (currFoodAmount < 0) return -1;
            if (extra < currTotalAmount) {
                int nextIndex = (int) (extra % priorityQueue.size());

                ArrayList<Food> sortedFood = new ArrayList<>(priorityQueue);
                sortedFood.sort(Comparator.comparingInt(o -> o.index));

                return sortedFood.get(nextIndex).index;
            }

            extra -= currTotalAmount;
            prevAmount = food.amount;

            priorityQueue.poll();
        }
        return -1;
    }
}
