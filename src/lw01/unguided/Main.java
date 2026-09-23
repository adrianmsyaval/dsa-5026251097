package lw01.unguided;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(Main.class.getResourceAsStream("washes.txt"));

        int count = scanner.nextInt();
        WashService[] services = new WashService[count];

        for (int i = 0; i < count; i++) {
            String type = scanner.next();
            String id = scanner.next();
            int days = scanner.nextInt();
            int units = scanner.nextInt();

            if (type.equalsIgnoreCase("MOTORCYCLE")) {
                services[i] = new MotorcycleWash(id, days);
            } else {
                services[i] = new CarWash(id, days);
            }

            services[i].calculateCharge(units);
        }

        scanner.close();

        for (int i = 0; i < count; i++) {
            System.out.println(services[i].summary());
        }
    }
}