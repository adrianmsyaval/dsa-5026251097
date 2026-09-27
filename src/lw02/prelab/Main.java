package lw02.prelab;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws Exception {
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();

        Scanner scanner = new Scanner(Main.class.getResourceAsStream("transactions.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(" ");
            transactions.add(parts);

            String name = parts[0];
            if (findCustomerIndex(customers, name) == -1) {
                customers.add(new String[] { name, "0" });
            }
        }
        scanner .close();

        Queue<String[]> queue = new LinkedList<>();
        for (String[] t : transactions) {
            queue.offer(t);
        }

        Stack<String[]> failedTransactions = new Stack<>();

        while (!queue.isEmpty()) {
            String[] t = queue.poll();
            String name = t[0];
            String type = t[1];
            int amount = Integer.parseInt(t[2]);

            int index = findCustomerIndex(customers, name);
            String[] customer = customers.get(index);
            int balance = Integer.parseInt(customer[1]);

            if (type.equals("DEPOSIT")) {
                balance = balance + amount;
                customer[1] = String.valueOf(balance);
            } else if (type.equals("WITHDRAW")) {
                if (amount > balance) {
                    failedTransactions.push(t);
                } else {
                    balance = balance - amount;
                    customer[1] = String.valueOf(balance);
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + " : " + customer[1]);
        }

        System.out.println();
        System.out.println("=== Failed Transactions ===");
        while (!failedTransactions.isEmpty()) {
            String[] t = failedTransactions.pop();
            System.out.println(t[0] + " " + t[1] + " " + t[2]);
        }
    }

    private static int findCustomerIndex(LinkedList<String[]> customers, String name) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i)[0].equals(name)) {
                return i;
            }
        }
        return -1;
    }
}