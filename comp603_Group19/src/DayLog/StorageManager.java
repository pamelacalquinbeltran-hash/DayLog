package DayLog;

import java.io.*;
import java.util.List;

public class StorageManager {

    private static final String EVENTS_FILE = "events.txt";
    private static final String HABITS_FILE = "habits.txt";

    public static void saveEvents(List<CalendarEvent> events) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (CalendarEvent e : events) {
                pw.println(e.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving events.");
        }
    }

    public static void loadEvents(CalendarManager manager) {
        File f = new File(EVENTS_FILE);
        if (!f.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                manager.addEventObject(CalendarEvent.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading events.");
        }
    }

    public static void saveHabits(List<Habit> habits) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(HABITS_FILE))) {
            for (Habit h : habits) {
                pw.println(h.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving habits.");
        }
    }

    public static void loadHabits(HabitManager manager) {
        File f = new File(HABITS_FILE);
        if (!f.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                manager.addHabitObject(Habit.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading habits.");
        }
    }
}
