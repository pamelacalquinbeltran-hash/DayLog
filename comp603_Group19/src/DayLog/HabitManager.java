package DayLog;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {

    private final List<Habit> habits = new ArrayList<>();

    public boolean existsByName(String name) {
        return habits.stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(name));
    }

    public void addHabit(String name, String frequency) {
        habits.add(new Habit(name, frequency));
    }

    public void addHabitObject(Habit habit) {
        habits.add(habit);
    }

    public void editHabit(int index, String newName, String newFrequency) {
        if (index >= 0 && index < habits.size()) {
            habits.get(index).setName(newName);
            habits.get(index).setFrequency(newFrequency);
        }
    }

    public void deleteHabit(int index) {
        if (index >= 0 && index < habits.size()) {
            habits.remove(index);
        }
    }

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
