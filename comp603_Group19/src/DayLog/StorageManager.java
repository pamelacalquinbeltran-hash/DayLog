package DayLog;

import java.io.*;
import java.util.List;

public class StorageManager {

    private static final String HABITS_FILE = "habits.txt";
    private static final String EVENTS_FILE = "events.txt";

    // ================= HABITS =================
    public static void saveHabits(List<Habit> habits) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HABITS_FILE))) {
            for (Habit habit : habits) {
                writer.println(habit.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving habits.");
        }
    }

    public static void loadHabits(HabitManager habitManager) {
        File file = new File(HABITS_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Habit habit = Habit.fromFileString(line);
                habitManager.addHabitObject(habit);
            }
        } catch (IOException e) {
            System.out.println("Error loading habits.");
        }
    }

    // ================= EVENTS =================
    public static void saveEvents(List<CalendarEvent> events) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (CalendarEvent event : events) {
                writer.println(event.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving events.");
        }
    }

    public static void loadEvents(CalendarManager calendarManager) {
        File file = new File(EVENTS_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CalendarEvent event = CalendarEvent.fromFileString(line);
                calendarManager.addEventObject(event);
            }
        } catch (IOException e) {
            System.out.println("Error loading events.");
        }
    }
}
