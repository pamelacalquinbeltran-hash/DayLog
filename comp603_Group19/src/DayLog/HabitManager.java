package DayLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {

    private final List<Habit> habits = new ArrayList<>();

    public void addHabit(String name, String frequency) {
        habits.add(new Habit(name, frequency));
    }

    public void addHabitObject(Habit habit) {
        habits.add(habit);
    }

    public void editHabit(int index, String name, String freq) {
        if (valid(index)) {
            habits.get(index).setName(name);
            habits.get(index).setFrequency(freq);
        }
    }

    public void deleteHabit(int index) {
        if (valid(index)) {
            habits.remove(index);
        }
    }

    public void markHabitDone(int index, LocalDate date) {
        if (valid(index)) {
            habits.get(index).markDone(date);
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
