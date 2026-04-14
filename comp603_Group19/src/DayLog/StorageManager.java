package DayLog;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class StorageManager {

    private static final String HABITS_FILE = "habits.txt";
    private static final String EVENTS_FILE = "events.txt";

    // ================= HABITS =================
    public static void saveHabits(List<Habit> habits) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(HABITS_FILE))) {
            for (Habit h : habits) {
                pw.println(h.toFileString());
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

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Habit habit = Habit.fromFileString(line);
                habitManager.addHabitObject(habit);
            }
        } catch (IOException e) {
            System.out.println("Error loading habits.");
        }
    }

    // ================= EVENTS =================
    public static void saveEvents(List<CalendarEvent> events) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (CalendarEvent e : events) {
                pw.println(e.toFileString());
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

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                CalendarEvent event = CalendarEvent.fromFileString(line);
                calendarManager.addEventObject(event);
            }
        } catch (IOException e) {
            System.out.println("Error loading events.");
        }
    }
}
