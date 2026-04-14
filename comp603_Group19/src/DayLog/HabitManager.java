package DayLog;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {

    private final List<Habit> habits = new ArrayList<>();

    // ===== ADD HABITS =====
    public void addHabit(String name, String frequency) {
        habits.add(new Habit(name, frequency));
    }

    // ✅ REQUIRED for StorageManager
    public void addHabitObject(Habit habit) {
        habits.add(habit);
    }

    // ===== CHECK EXISTENCE =====
    public boolean existsByName(String name) {
        return habits.stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(name));
    }

    public int indexOf(String name) {
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    // ===== EDIT / DELETE =====
    public void editHabit(int index, String name, String frequency) {
        if (index >= 0 && index < habits.size()) {
            habits.get(index).setName(name);
            habits.get(index).setFrequency(frequency);
        }
    }

    public void deleteHabit(int index) {
        if (index >= 0 && index < habits.size()) {
            habits.remove(index);
        }
    }

    // ===== QUERY =====
    public List<Habit> getHabits() {
        return habits;
    }

    public List<Habit> getHabitsForDay(DayOfWeek day) {
        List<Habit> result = new ArrayList<>();
        for (Habit h : habits) {
            if (h.appliesToDay(day)) {
                result.add(h);
            }
        }
        return result;
    }
}
