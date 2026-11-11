package lw04.unguided;

import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputStream input = Main.class.getResourceAsStream("parcels.txt");
        Scanner sc = new Scanner(input);

        int n = sc.nextInt();
        int[] parcels = new int[n];
        for (int i = 0; i < n; i++) {
            parcels[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[] targets = new int[m];
        for (int i = 0; i < m; i++) {
            targets[i] = sc.nextInt();
        }
        sc.close();

        int[] afterTwoPasses = new int[n];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (parcels[j] < parcels[j + 1]) {
                    int temp = parcels[j];
                    parcels[j] = parcels[j + 1];
                    parcels[j + 1] = temp;
                }
            }
            if (i == 1) {
                for (int k = 0; k < n; k++) {
                    afterTwoPasses[k] = parcels[k];
                }
            }
        }

        System.out.println("\n==== After 2 Passes ====");
        for (int i = 0; i < n; i++) {
            System.out.print(afterTwoPasses[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        System.out.println("\n==== Fully Sorted ====");
        for (int i = 0; i < n; i++) {
            System.out.print(parcels[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        System.out.println("\n==== Search Results ====");
        for (int t = 0; t < m; t++) {
            int low = 0;
            int high = n - 1;
            int steps = 0;
            int index = -1;

            while (low <= high) {
                int mid = (low + high) / 2;
                steps++;
                if (parcels[mid] == targets[t]) {
                    index = mid;
                    break;
                } else if (parcels[mid] > targets[t]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            if (index != -1) {
                System.out.println(targets[t] + ": FOUND at index " + index + " (steps: " + steps + ")");
            } else {
                System.out.println(targets[t] + ": NOT FOUND (steps: " + steps + ")");
            }
        }
    }
}