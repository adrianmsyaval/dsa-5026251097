package lw03.prelab;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws Exception {
		managePlaylist();
		manageParticipants();
		manageInventory();
	}

	private static void managePlaylist() throws Exception {
	List<String> playlist = new ArrayList<>();

	try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("playlist.txt"))) {
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.isEmpty()) {
				continue;
			}

			String operation = line.split("\\s+")[0];
			if (operation.equals("ADD")) {
				String song = line.substring(operation.length()).trim();
				playlist.add(song);
			} else if (operation.equals("INSERT")) {
				String[] parts = line.split("\\s+", 3);
				int index = Integer.parseInt(parts[1]);
				playlist.add(index, parts[2]);
			} else if (operation.equals("REMOVE")) {
				String song = line.substring(operation.length()).trim();
				playlist.remove(song);
			}
		}
	}

	System.out.println("===== Problem 1 =====");
	System.out.println("Total songs: " + playlist.size());
	for (int i = 0; i < playlist.size(); i++) {
		System.out.println((i + 1) + ": " + playlist.get(i));
	}
}

	private static void manageParticipants() throws Exception {
		Set<String> participants = new LinkedHashSet<>();
		int duplicateRegistrations = 0;

		try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("participants.txt"))) {
			while (scanner.hasNextLine()) {
				String name = scanner.nextLine().trim();
				if (!name.isEmpty()) {
					if (participants.contains(name)) {
						duplicateRegistrations++;
					} else {
						participants.add(name);
					}
				}
			}
		}

		System.out.println("=== Problem 2 ===");
		System.out.println("Unique participants: " + participants.size());
		int position = 1;
		for (String participant : participants) {
			System.out.println(position++ + ". " + participant);
		}
		System.out.println("Duplicate registrations: " + duplicateRegistrations);
	}

	private static void manageInventory() throws Exception {
		Map<String, Integer> inventory = new LinkedHashMap<>();
		int failedSales = 0;

		try (Scanner scanner = new Scanner(Main.class.getResourceAsStream("inventory.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] parts = line.split("\\s+");
				String operation = parts[0];
				String product = parts[1];
				int quantity = Integer.parseInt(parts[2]);

				if (operation.equals("ADD")) {
					if (inventory.containsKey(product)) {
						int currentStock = inventory.get(product);
						inventory.put(product, currentStock + quantity);
					} else {
						inventory.put(product, quantity);
					}
				} else if (operation.equals("SELL")) {
					if (inventory.containsKey(product)) {
						int currentStock = inventory.get(product);
						if (currentStock >= quantity) {
							inventory.put(product, currentStock - quantity);
						} else {
							failedSales++;
						}
					} else {
						failedSales++;
					}
				}
			}
		}

		System.out.println("=== Problem 3 ===");
		for (String product : inventory.keySet()) {
			System.out.println(product + ": " + inventory.get(product));
		}
		System.out.println("Failed sales: " + failedSales);
	}
}