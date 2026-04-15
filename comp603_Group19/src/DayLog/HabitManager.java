package DayLog;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {

    private final List<Habit> habits = new ArrayList<>();

    public void addHabit(String name, String freq) {
        habits.add(new Habit(name, freq));
    }

    public void addHabitObject(Habit h) {
        habits.add(h);
    }

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

    public void editHabit(int index, String name, String freq) {
        if (index >= 0 && index < habits.size()) {
            habits.get(index).setName(name);
            habits.get(index).setFrequency(freq);
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
