package DayLog;

import java.util.Scanner;

public class DayLog {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DayLog app = new DayLog();
        app.start();
    }

    private void start() {
        displayWelcome();
        mainMenuLoop();
        exitApplication();
    }

    private void displayWelcome() {
        System.out.println("=================================");
        System.out.println("              DAYLOG              ");
        System.out.println("      Habit Tracker & Calendar    ");
        System.out.println("=================================");
        System.out.println();
    }

    // ================= MAIN MENU =================
    private void mainMenuLoop() {
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    habitMenu();
                    break;
                case 2:
                    System.out.println("[Calendar menu will be implemented later]");
                    pause();
                    break;
                case 3:
                    System.out.println("[Daily overview will be implemented later]");
                    pause();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    pause();
            }
        }
    }

    private void printMainMenu() {
        System.out.println("----------- MAIN MENU -----------");
        System.out.println("1. Habits");
        System.out.println("2. Calendar");
        System.out.println("3. Daily Overview");
        System.out.println("4. Save and Exit");
        System.out.println("--------------------------------");
    }

    // ================= HABIT MENU =================
    private void habitMenu() {
        boolean inHabitMenu = true;

        while (inHabitMenu) {
            printHabitMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    habitChecklist();
                    break;
                case 2:
                    manageHabitsMenu();
                    break;
                case 3:
                    inHabitMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    pause();
            }
        }
    }

    private void printHabitMenu() {
        System.out.println("------- HABIT MENU -------");
        System.out.println("1. Habit Checklist (Today)");
        System.out.println("2. Manage Habits");
        System.out.println("3. Back to Main Menu");
        System.out.println("--------------------------");
    }

    // ================= HABIT CHECKLIST =================
    private void habitChecklist() {
        System.out.println();
        System.out.println("===== TODAY'S HABIT CHECKLIST =====");
        System.out.println("1. [ ] Study Java");
        System.out.println("2. [X] Morning Exercise");
        System.out.println("3. [ ] Read 10 Pages");
        System.out.println("----------------------------------");
        System.out.println("Completion logic will be implemented later.");
        pause();
    }

    // ================= MANAGE HABITS =================
    private void manageHabitsMenu() {
        boolean managing = true;

        while (managing) {
            printManageHabitsMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("[Add Habit – not implemented yet]");
                    pause();
                    break;
                case 2:
                    System.out.println("[Edit Habit – not implemented yet]");
                    pause();
                    break;
                case 3:
                    System.out.println("[Delete Habit – not implemented yet]");
                    pause();
                    break;
                case 4:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    pause();
            }
        }
    }

    private void printManageHabitsMenu() {
        System.out.println("------- MANAGE HABITS -------");
        System.out.println("1. Add New Habit");
        System.out.println("2. Edit Habit");
        System.out.println("3. Delete Habit");
        System.out.println("4. Back");
        System.out.println("-----------------------------");
    }

    // ================= INPUT HELPERS =================
    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        System.out.println();
    }

    private void exitApplication() {
        System.out.println();
        System.out.println("Saving data...");
        System.out.println("Data saved successfully.");
        System.out.println("Goodbye!");
    }
}
