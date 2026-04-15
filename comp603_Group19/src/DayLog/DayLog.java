package DayLog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DayLog {

    public static final DateTimeFormatter DATE_FORMAT
            = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final Scanner scanner = new Scanner(System.in);

    private final HabitManager habitManager = new HabitManager();
    private final CalendarManager calendarManager = new CalendarManager();

    public static void main(String[] args) {
        new DayLog().start();
    }

    private void start() {
        printHeader();
        StorageManager.loadHabits(habitManager);
        StorageManager.loadEvents(calendarManager);

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Habits");
            System.out.println("2. Calendar");
            System.out.println("3. Overview");
            System.out.println("4. Exit");

            int choice = readInt();
            switch (choice) {
                case 1 ->
                    habitMenu();
                case 2 ->
                    calendarMenu();
                case 3 ->
                    overviewMenu();
                case 4 ->
                    running = false;
                default ->
                    System.out.println("Invalid option.");
            }
        }

        StorageManager.saveHabits(habitManager.getHabits());
        StorageManager.saveEvents(calendarManager.getAllEvents());
        System.out.println("Goodbye!");
    }

    private void printHeader() {
        System.out.println("=================================");
        System.out.println("              DAYLOG              ");
        System.out.println("      Habit Tracker & Calendar    ");
        System.out.println("=================================");
    }

    // ================= HABITS =================
    private void habitMenu() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- HABIT MENU ---");
            System.out.println("1. Habit Checklist (Today)");
            System.out.println("2. Manage Habits");
            System.out.println("3. Back");

            int c = readInt();
            switch (c) {
                case 1 ->
                    habitChecklist();
                case 2 ->
                    manageHabits();
                case 3 ->
                    loop = false;
                default ->
                    System.out.println("Invalid option.");
            }
        }
    }

    private void habitChecklist() {
        LocalDate today = LocalDate.now();
        var todayHabits = habitManager.getHabitsForDay(today.getDayOfWeek());

        if (todayHabits.isEmpty()) {
            System.out.println("No habits scheduled for today.");
            return;
        }

        boolean loop = true;
        while (loop) {
            System.out.println("\nDate: " + today.format(DATE_FORMAT)
                    + " (" + today.getDayOfWeek() + ")");

            for (int i = 0; i < todayHabits.size(); i++) {
                var h = todayHabits.get(i);
                System.out.println((i + 1) + ". "
                        + (h.isCompletedOn(today) ? "[X] " : "[ ] ")
                        + h);
            }

            System.out.print("Enter habit number to mark done (0 to back): ");
            int c = readInt();
            if (c == 0) {
                loop = false;
            } else if (c > 0 && c <= todayHabits.size()) {
                todayHabits.get(c - 1).markDone(today);
                System.out.println("Habit marked as done.");
            } else {
                System.out.println("Invalid habit number.");
            }
        }
    }

    // ✅ ✅ ✅ RESTORED MANAGE HABITS ✅ ✅ ✅
    private void manageHabits() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- MANAGE HABITS ---");
            System.out.println("1. View Habits");
            System.out.println("2. Add Habit");
            System.out.println("3. Edit Habit");
            System.out.println("4. Delete Habit");
            System.out.println("5. Back");

            int c = readInt();
            switch (c) {
                case 1 ->
                    viewHabits();
                case 2 ->
                    addHabit();
                case 3 ->
                    editHabit();
                case 4 ->
                    deleteHabit();
                case 5 ->
                    loop = false;
                default ->
                    System.out.println("Invalid option.");
            }
        }
    }

    private void viewHabits() {
        if (habitManager.getHabits().isEmpty()) {
            System.out.println("No habits available.");
            return;
        }
        for (int i = 0; i < habitManager.getHabits().size(); i++) {
            System.out.println((i + 1) + ". " + habitManager.getHabits().get(i));
        }
    }

    private void addHabit() {
        System.out.print("Habit name: ");
        String name = scanner.nextLine();

        if (habitManager.existsByName(name)) {
            System.out.println("A habit with this name already exists.");
            return;
        }

        System.out.print("Frequency: ");
        habitManager.addHabit(name, scanner.nextLine());
        System.out.println("Habit added successfully.");
    }

    private void editHabit() {
        viewHabits();
        System.out.print("Select habit number to edit: ");
        int idx = readInt() - 1;

        System.out.print("New name: ");
        String n = scanner.nextLine();
        System.out.print("New frequency: ");
        String f = scanner.nextLine();

        habitManager.editHabit(idx, n, f);
        System.out.println("Habit updated successfully.");
    }

    private void deleteHabit() {
        viewHabits();
        System.out.print("Select habit number to delete: ");
        int idx = readInt() - 1;

        habitManager.deleteHabit(idx);
        System.out.println("Habit deleted successfully.");
    }

    // ================= CALENDAR =================
    private void calendarMenu() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- CALENDAR MENU ---");
            System.out.println("1. Add Event");
            System.out.println("2. View Events");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Back");

            int c = readInt();
            if (c == 1) {
                calendarManager.addEventPrompt();
            } else if (c == 2) {
                calendarManager.viewEventsMenu();
            } else if (c == 3) {
                calendarManager.editEventWithBack();
            } else if (c == 4) {
                calendarManager.deleteEventWithBack();
            } else if (c == 5) {
                loop = false;
            }
        }
    }

    // ================= OVERVIEW =================
    private void overviewMenu() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- OVERVIEW ---");
            System.out.println("1. Daily Overview");
            System.out.println("2. Monthly Overview");
            System.out.println("3. Yearly Overview");
            System.out.println("4. Back");

            int c = readInt();
            if (c == 1) {
                dailyOverview();
            } else if (c == 2) {
                calendarManager.monthlyOverview();
            } else if (c == 3) {
                calendarManager.yearlyOverview();
            } else if (c == 4) {
                loop = false;
            }
        }
    }

    private void dailyOverview() {
        LocalDate today = LocalDate.now();
        System.out.println("\nDate: " + today.format(DATE_FORMAT)
                + " (" + today.getDayOfWeek() + ")");

        System.out.println("\nHabits:");
        habitManager.getHabitsForDay(today.getDayOfWeek())
                .forEach(h -> System.out.println(
                (h.isCompletedOn(today) ? "[X] " : "[ ] ") + h));

        System.out.println("\nEvents:");
        calendarManager.printEventsForDate(today);
    }

    // ================= INPUT =================
    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
