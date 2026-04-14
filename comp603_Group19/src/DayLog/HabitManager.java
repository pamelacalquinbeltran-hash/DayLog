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
}
