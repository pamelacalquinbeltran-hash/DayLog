package DayLog;

import java.time.LocalDate;
import java.util.Scanner;

public class DayLog {

    private static final Scanner scanner = new Scanner(System.in);

    private final HabitManager habitManager = new HabitManager();
    private final CalendarManager calendarManager = new CalendarManager();

    public static void main(String[] args) {
        new DayLog().start();
    }

    private void start() {
        showTitle();
        mainMenu();
        System.out.println("Saving data...");
        System.out.println("Goodbye!");
    }

    private void showTitle() {
        System.out.println("=================================");
        System.out.println("              DAYLOG              ");
        System.out.println("      Habit Tracker & Calendar    ");
        System.out.println("=================================");
    }

    // ================= MAIN MENU =================
    private void mainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Habits");
            System.out.println("2. Calendar");
            System.out.println("3. Daily Overview");
            System.out.println("4. Exit");
            int choice = readInt();

            switch (choice) {
                case 1 ->
                    habitMenu();
                case 2 ->
                    calendarMenu();
                case 3 ->
                    dailyOverview();
                case 4 ->
                    running = false;
                default ->
                    System.out.println("Invalid option");
            }
        }
    }

    // ================= HABITS =================
    private void habitMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n--- HABIT MENU ---");
            System.out.println("1. Habit Checklist (Today)");
            System.out.println("2. Manage Habits");
            System.out.println("3. Back");
            int choice = readInt();

            switch (choice) {
                case 1 ->
                    habitChecklist();
                case 2 ->
                    manageHabits();
                case 3 ->
                    inMenu = false;
                default ->
                    System.out.println("Invalid option");
            }
        }
    }

    private void habitChecklist() {
        if (!habitManager.hasHabits()) {
            System.out.println("No habits available.");
            return;
        }

        System.out.println("\n--- TODAY'S HABIT CHECKLIST ---");
        for (int i = 0; i < habitManager.getHabits().size(); i++) {
            boolean done = habitManager.isCompletedOnDate(i, LocalDate.now());
            System.out.println((i + 1) + ". " + (done ? "[X] " : "[ ] ")
                    + habitManager.getHabits().get(i));
        }

        System.out.print("Toggle habit (0 to back): ");
        int choice = readInt();
        if (choice > 0) {
            habitManager.toggleCompletion(choice - 1, LocalDate.now());
        }
    }

    private void manageHabits() {
        boolean managing = true;

        while (managing) {
            System.out.println("\n--- MANAGE HABITS ---");
            System.out.println("1. View Current Habits");
            System.out.println("2. Add Habit");
            System.out.println("3. Edit Habit");
            System.out.println("4. Delete Habit");
            System.out.println("5. Back");
            int choice = readInt();

            switch (choice) {
                case 1 ->
                    viewHabits();
                case 2 ->
                    addHabit();
                case 3 ->
                    editHabit();
                case 4 ->
                    deleteHabit();
                case 5 ->
                    managing = false;
                default ->
                    System.out.println("Invalid option");
            }
        }
    }

    private void viewHabits() {
        if (!habitManager.hasHabits()) {
            System.out.println("No habits added yet.");
            return;
        }

        for (int i = 0; i < habitManager.getHabits().size(); i++) {
            System.out.println((i + 1) + ". " + habitManager.getHabits().get(i));
        }
    }

    private void addHabit() {
        System.out.print("Habit name: ");
        String name = scanner.nextLine();

        System.out.print("Frequency: ");
        String frequency = scanner.nextLine();

        habitManager.addHabit(name, frequency);

        System.out.println("Habit \"" + name + "\" with frequency \""
                + frequency + "\" was added successfully.");
    }

    private void editHabit() {
        if (!habitManager.hasHabits()) {
            System.out.println("No habits to edit.");
            return;
        }

        viewHabits();
        System.out.print("Select habit: ");
        int index = readInt() - 1;

        System.out.print("New name: ");
        String name = scanner.nextLine();

        System.out.print("New frequency: ");
        String frequency = scanner.nextLine();

        habitManager.editHabit(index, name, frequency);

        System.out.println("Habit updated to \"" + name
                + "\" [" + frequency + "].");
    }

    private void deleteHabit() {
        if (!habitManager.hasHabits()) {
            System.out.println("No habits to delete.");
            return;
        }

        viewHabits();
        System.out.print("Select habit to delete: ");
        int index = readInt() - 1;

        String removed = habitManager.getHabits().get(index).toString();
        habitManager.deleteHabit(index);

        System.out.println("Habit " + removed + " was deleted successfully.");
    }

    // ================= CALENDAR =================
    private void calendarMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n--- CALENDAR MENU ---");
            System.out.println("1. Add Event");
            System.out.println("2. View Events by Date");
            System.out.println("3. Delete Event");
            System.out.println("4. Back");
            int choice = readInt();

            switch (choice) {
                case 1 ->
                    addEvent();
                case 2 ->
                    viewEventsByDate();
                case 3 ->
                    deleteEvent();
                case 4 ->
                    inMenu = false;
                default ->
                    System.out.println("Invalid option");
            }
        }
    }

    private void addEvent() {
        System.out.print("Event title: ");
        String title = scanner.nextLine();

        System.out.print("Event date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        calendarManager.addEvent(title, date, desc);

        System.out.println("Event \"" + title + "\" added successfully.");
    }

    private void viewEventsByDate() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        var events = calendarManager.getEventsForDate(date);
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i));
        }
    }

    private void deleteEvent() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        var events = calendarManager.getEventsForDate(date);
        if (events.isEmpty()) {
            return;
        }

        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i));
        }

        System.out.print("Select event to delete: ");
        int index = readInt() - 1;

        calendarManager.deleteEvent(date, index);
        System.out.println("Event deleted successfully.");
    }

    // ================= DAILY OVERVIEW =================
    private void dailyOverview() {
        LocalDate today = LocalDate.now();
        System.out.println("\n--- DAILY OVERVIEW (" + today + ") ---");

        for (int i = 0; i < habitManager.getHabits().size(); i++) {
            boolean done = habitManager.isCompletedOnDate(i, today);
            System.out.println((done ? "[X] " : "[ ] ")
                    + habitManager.getHabits().get(i));
        }

        for (var e : calendarManager.getEventsForDate(today)) {
            System.out.println("- " + e);
        }
    }

    // ================= INPUT =================
    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a number: ");
            }
        }
    }
}
