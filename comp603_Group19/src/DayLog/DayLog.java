
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
        System.out.println("             DAYLOG               ");
        System.out.println("     Habit Tracker & Calendar     ");
        System.out.println("=================================");
        System.out.println();
    }

    private void mainMenuLoop() {
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("[Habit menu will be implemented next]");
                    pause();
                    break;

                case 2:
                    System.out.println("[Calendar menu will be implemented next]");
                    pause();
                    break;

                case 3:
                    System.out.println("[Daily overview will be implemented next]");
                    pause();
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
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

    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void pause() {
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private void exitApplication() {
        System.out.println();
        System.out.println("Saving data...");
        System.out.println("Data saved successfully.");
        System.out.println("Goodbye!");
    }
}