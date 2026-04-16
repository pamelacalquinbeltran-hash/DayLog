package DayLog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DayLog {

    public static final DateTimeFormatter DATE_FORMAT
            = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final Scanner scanner = new Scanner(System.in);

    private final HabitManager habitManager = new HabitManager();   // LOCKED
    private final CalendarManager calendarManager = new CalendarManager();

    public static void main(String[] args) {
        new DayLog().start();
    }

    private void start() {
        printHeader();

        // Load persisted data
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

        // Save before exit
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

    // ================= HABITS (FROZEN) =================
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

        // Get only habits that apply to today
        var todayHabits = habitManager.getHabitsForDay(today.getDayOfWeek());

        if (todayHabits.isEmpty()) {
            System.out.println("No habits scheduled for today.");
            return;
        }

        boolean loop = true;
        while (loop) {
            System.out.println("\n===== HABIT CHECKLIST =====");
            System.out.println("Date: " + today.format(DATE_FORMAT)
                    + " (" + today.getDayOfWeek() + ")");
            System.out.println();

            // ---- TABLE HEADER ----
            System.out.printf("%-4s %-20s %-15s %-8s%n",
                    "No", "Habit", "Frequency", "Status");
            System.out.println("------------------------------------------------");

            // ---- TABLE ROWS ----
            for (int i = 0; i < todayHabits.size(); i++) {
                Habit h = todayHabits.get(i);
                String status = h.isCompletedOn(today) ? "[X]" : "[ ]";

                System.out.printf("%-4d %-20s %-15s %-8s%n",
                        i + 1,
                        h.getName(),
                        "[" + h.toString().replace(h.getName(), "").replace("[", "").replace("]", "").trim() + "]",
                        status);
            }

            System.out.println();
            System.out.print("Enter habit number to mark done (0 to back): ");

            int choice = readInt();

            if (choice == 0) {
                loop = false;
            } else if (choice > 0 && choice <= todayHabits.size()) {
                Habit selectedHabit = todayHabits.get(choice - 1);

                if (selectedHabit.isCompletedOn(today)) {
                    System.out.println("This habit is already marked as done today.");
                } else {
                    selectedHabit.markDone(today);
                    System.out.println("Habit \"" + selectedHabit.getName()
                            + "\" marked as done for today.");
                }
            } else {
                System.out.println("Invalid habit number. Please try again.");
            }
        }
    }

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

        System.out.printf("%-4s %-25s %-20s%n",
                "No", "Habit", "Frequency");
        System.out.println("-----------------------------------------------");

        for (int i = 0; i < habitManager.getHabits().size(); i++) {
            Habit h = habitManager.getHabits().get(i);

            // Extract frequency cleanly from toString()
            String frequency = h.toString()
                    .replace(h.getName(), "")
                    .replace("[", "")
                    .replace("]", "")
                    .trim();

            System.out.printf("%-4d %-25s %-20s%n",
                    i + 1,
                    h.getName(),
                    frequency);
        }
    }

    private void addHabit() {
        System.out.print("Habit name: ");
        String name = scanner.nextLine().trim();

        if (habitManager.existsByName(name)) {
            System.out.println("Error: A habit named \"" + name + "\" already exists.");
            return;
        }

        System.out.print("Frequency (Daily / Mon,Tue etc): ");
        String freq = scanner.nextLine();

        habitManager.addHabit(name, freq);
        System.out.println("Habit \"" + name + "\" added successfully.");
    }

    private void editHabit() {
        if (habitManager.getHabits().isEmpty()) {
            System.out.println("No habits to edit.");
            return;
        }

        viewHabits();
        System.out.print("Select habit number to edit or enter 0 to exit: ");
        int choice = readInt();

        if (choice == 0) {
            return;
        }

        int index = choice - 1;
        if (index < 0 || index >= habitManager.getHabits().size()) {
            System.out.println("Invalid habit number.");
            return;
        }

        System.out.print("New name: ");
        String newName = scanner.nextLine().trim();

        if (!habitManager.getHabits().get(index).getName()
                .equalsIgnoreCase(newName)
                && habitManager.existsByName(newName)) {

            System.out.println("Error: A habit named \"" + newName + "\" already exists.");
            return;
        }

        System.out.print("New frequency: ");
        String newFrequency = scanner.nextLine();

        habitManager.editHabit(index, newName, newFrequency);
        System.out.println("Habit updated successfully.");
        viewHabits();
    }

    private void deleteHabit() {
        if (habitManager.getHabits().isEmpty()) {
            System.out.println("No habits to delete.");
            return;
        }

        viewHabits();
        System.out.print("Select habit number to delete or enter 0 to exit: ");
        int choice = readInt();

        if (choice == 0) {
            return;
        }

        int index = choice - 1;
        if (index < 0 || index >= habitManager.getHabits().size()) {
            System.out.println("Invalid habit number.");
            return;
        }

        habitManager.deleteHabit(index);
        System.out.println("Habit deleted successfully.");
        viewHabits();
    }

    private void printHabitTable(java.util.List<Habit> habits, LocalDate date) {
        if (habits.isEmpty()) {
            System.out.println("No habits to display.");
            return;
        }

        System.out.printf("%-4s %-20s %-15s %-8s%n",
                "No", "Habit", "Frequency", "Status");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < habits.size(); i++) {
            Habit h = habits.get(i);
            String status = h.isCompletedOn(date) ? "[X]" : "[ ]";

            System.out.printf("%-4d %-20s %-15s %-8s%n",
                    i + 1,
                    h.getName(),
                    h.toString().replace(h.getName(), "").trim(), // shows [frequency]
                    status);
        }
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
            switch (c) {
                case 1 ->
                    calendarManager.addEventPrompt();
                case 2 ->
                    calendarManager.viewEventsMenu();
                case 3 ->
                    calendarManager.editEventWithBack();
                case 4 ->
                    calendarManager.deleteEventWithBack();
                case 5 ->
                    loop = false;
                default ->
                    System.out.println("Invalid option.");
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
            switch (c) {
                case 1 ->
                    dailyOverview();
                case 2 ->
                    calendarManager.monthlyOverview();
                case 3 ->
                    calendarManager.yearlyOverview();
                case 4 ->
                    loop = false;
                default ->
                    System.out.println("Invalid option.");
            }
        }
    }

    private void dailyOverview() {
        LocalDate today = LocalDate.now();

        System.out.println("\n--- DAILY OVERVIEW ---");
        System.out.println("Date: " + today.format(DATE_FORMAT)
                + " (" + today.getDayOfWeek() + ")");

        System.out.println("\nHabits:");
        habitManager.getHabitsForDay(today.getDayOfWeek())
                .forEach(h
                        -> System.out.println((h.isCompletedOn(today) ? "[X] " : "[ ] ") + h));

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
