package DayLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {

    private final List<Habit> habits = new ArrayList<>();

    public void addHabit(String name, String frequency) {
        habits.add(new Habit(name, frequency));
    }

    public void editHabit(int index, String name, String frequency) {
        if (valid(index)) {
            habits.get(index).setName(name);
            habits.get(index).setFrequency(frequency);
        }
    }

    public void deleteHabit(int index) {
        if (valid(index)) {
            habits.remove(index);
        }
    }

    public void toggleCompletion(int index, LocalDate date) {
        if (valid(index)) {
            habits.get(index).toggleCompletion(date);
        }
    }

    public boolean isCompletedOnDate(int index, LocalDate date) {
        return valid(index) && habits.get(index).isCompletedOn(date);
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public boolean hasHabits() {
        return !habits.isEmpty();
    }

    private boolean valid(int i) {
        return i >= 0 && i < habits.size();
    }
}
