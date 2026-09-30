package lw02.unguided;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws Exception {
        LinkedList<String[]> orderList = new LinkedList<>();
        LinkedList<String[]> foodList = new LinkedList<>();
        LinkedList<String[]> drinkList = new LinkedList<>();
        LinkedList<String[]> successList = new LinkedList<>();

        foodList.add(new String[] { "Bakso", "2" });
        foodList.add(new String[] { "Sate", "1" });
        foodList.add(new String[] { "Soto", "2" });
        drinkList.add(new String[] { "EsTeh", "4" });
        drinkList.add(new String[] { "EsJeruk", "2" });

        Scanner scanner = new Scanner(Main.class.getResourceAsStream("orders.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                orderList.add(line.split(" "));
            }
        }
        scanner.close();

        Queue<String[]> orderQueue = new LinkedList<>();
        for (String[] order : orderList) {
            orderQueue.offer(order);
        }

        Stack<String[]> failedOrders = new Stack<>();

        while (!orderQueue.isEmpty()) {
            String[] order = orderQueue.poll();
            String foodName = order[1];
            String drinkName = order[2];
            int foodIndex = foodName.equals("-") ? -1 : findItemIndex(foodList, foodName);
            int drinkIndex = drinkName.equals("-") ? -1 : findItemIndex(drinkList, drinkName);

            boolean foodAvailable = foodName.equals("-")
                || (foodIndex != -1 && Integer.parseInt(foodList.get(foodIndex)[1]) > 0);
            boolean drinkAvailable = drinkName.equals("-")
                || (drinkIndex != -1 && Integer.parseInt(drinkList.get(drinkIndex)[1]) > 0);

            if (foodAvailable && drinkAvailable) {
                if (foodIndex != -1) {
                    reduceStock(foodList.get(foodIndex));
                }
                if (drinkIndex != -1) {
                    reduceStock(drinkList.get(drinkIndex));
                }
                successList.add(order);
            } else {
                failedOrders.push(order);
            }
        }

        System.out.println("=== Successfully Processed Orders ===");
        for (String[] order : successList) {
            printOrder(order);
        }

        System.out.println("\n=== Remaining Food Stock ===");
        printStock(foodList);

        System.out.println("\n=== Remaining Drink Stock ===");
        printStock(drinkList);

        System.out.println("\n=== Failed Orders ===");
        while (!failedOrders.isEmpty()) {
            printOrder(failedOrders.pop());
        }
    }

    private static int findItemIndex(LinkedList<String[]> itemList, String itemName) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i)[0].equals(itemName)) {
                return i;
            }
        }
        return -1;
    }

    private static void reduceStock(String[] item) {
        int stock = Integer.parseInt(item[1]);
        item[1] = String.valueOf(stock - 1);
    }

    private static void printOrder(String[] order) {
        System.out.println(order[0] + " " + order[1] + " " + order[2] + " " + order[3]);
    }

    private static void printStock(LinkedList<String[]> itemList) {
        for (String[] item : itemList) {
            System.out.println(item[0] + " : " + item[1]);
        }
    }
}