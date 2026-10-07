package lw03.unguided;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        Set<String> registeredStudents = new HashSet<>();
        Map<String, String> attendance = new HashMap<>();
        List<String> checkinResults = new ArrayList<>();

        Scanner registrationsScanner = new Scanner(Main.class.getResourceAsStream("registrations.txt"));
        while (registrationsScanner.hasNextLine()) {
            String studentID = registrationsScanner.nextLine();
            if (registeredStudents.add(studentID)) {
                attendance.put(studentID, "Not checked in");
            }
        }
        registrationsScanner.close();

        int rejectedAttempts = 0;
        Scanner checkinsScanner = new Scanner(Main.class.getResourceAsStream("checkins.txt"));
        while (checkinsScanner.hasNextLine()) {
            String studentID = checkinsScanner.nextLine();

            if (!registeredStudents.contains(studentID)) {
                checkinResults.add(studentID + ": Rejected (not registered)");
                rejectedAttempts++;
            } else if (attendance.get(studentID).equals("Checked in")) {
                checkinResults.add(studentID + ": Rejected (already checked in)");
                rejectedAttempts++;
            } else {
                attendance.put(studentID, "Checked in");
                checkinResults.add(studentID + ": Checked in");
            }
        }
        checkinsScanner.close();

        int successfulCheckins = 0;
        for (String studentID : registeredStudents) {
            if (attendance.get(studentID).equals("Checked in")) {
                successfulCheckins++;
            }
        }

        System.out.println("===== Event Check-In Results =====");
        for (String result : checkinResults) {
            System.out.println(result);
        }

        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + registeredStudents.size());
        System.out.println("Successful check-ins: " + successfulCheckins);
        System.out.println("Absent students: " + (registeredStudents.size() - successfulCheckins));
        System.out.println("Rejected attempts: " + rejectedAttempts);
    }
}