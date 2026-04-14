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
        StorageManager.loadHabits(habitManager);
        StorageManager.loadEvents(calendarManager);
        mainMenu();
        StorageManager.saveHabits(habitManager.getHabits());
        StorageManager.saveEvents(calendarManager.getAllEvents());
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
        LocalDate today = LocalDate.now();
        var todayHabits = habitManager.getHabitsForDay(today.getDayOfWeek());

        if (todayHabits.isEmpty()) {
            System.out.println("No habits scheduled for today.");
            return;
        }

        boolean inChecklist = true;
        while (inChecklist) {
            System.out.println("\n===== HABIT CHECKLIST =====");
            System.out.println("Date: " + today + " (" + today.getDayOfWeek() + ")");
            System.out.println("---------------------------");

            for (int i = 0; i < todayHabits.size(); i++) {
                Habit h = todayHabits.get(i);
                boolean done = h.isCompletedOn(today);
                System.out.println((i + 1) + ". " + (done ? "[X] " : "[ ] ") + h);
            }

            System.out.print("Enter habit number to mark done (0 to back): ");
            int choice = readInt();

            if (choice == 0) {
                inChecklist = false;
            } else if (choice > 0 && choice <= todayHabits.size()) {
                todayHabits.get(choice - 1).markDone(today);
                System.out.println("Habit marked as done.");
            } else {
                System.out.println("Invalid habit number.");
            }
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
        var habits = habitManager.getHabits();
        if (habits.isEmpty()) {
            System.out.println("No habits added yet.");
            return;
        }
        for (int i = 0; i < habits.size(); i++) {
            System.out.println((i + 1) + ". " + habits.get(i));
        }
    }

    private void addHabit() {
        System.out.print("Habit name: ");
        String name = scanner.nextLine();
        System.out.print("Frequency (Daily / Mon,Tue,etc): ");
        String freq = scanner.nextLine();
        habitManager.addHabit(name, freq);
        System.out.println("Habit \"" + name + "\" with frequency \"" + freq + "\" added successfully.");
    }

    private void editHabit() {
        viewHabits();
        System.out.print("Select habit to edit: ");
        int index = readInt() - 1;
        System.out.print("New name: ");
        String name = scanner.nextLine();
        System.out.print("New frequency: ");
        String freq = scanner.nextLine();
        habitManager.editHabit(index, name, freq);
        System.out.println("Habit updated successfully.");
    }

    private void deleteHabit() {
        viewHabits();
        System.out.print("Select habit to delete: ");
        int index = readInt() - 1;
        habitManager.deleteHabit(index);
        System.out.println("Habit deleted successfully.");
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
        System.out.println("Event added successfully.");
    }

    private void viewEventsByDate() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        var events = calendarManager.getEventsForDate(date);
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        events.forEach(e -> System.out.println("- " + e));
    }

    private void deleteEvent() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        var events = calendarManager.getEventsForDate(date);
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

        habitManager.getHabitsForDay(today.getDayOfWeek()).forEach(h -> {
            System.out.println((h.isCompletedOn(today) ? "[X] " : "[ ] ") + h);
        });

        calendarManager.getEventsForDate(today).forEach(e -> System.out.println("- " + e));
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Enter a number: ");
            }
        }
    }
}
