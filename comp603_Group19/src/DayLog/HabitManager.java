/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DayLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitManager {

    private final List<Habit> habits;

    public HabitManager() {
        this.habits = new ArrayList<>();
    }

    // -------- Habit CRUD --------
    public void addHabit(String name, String frequency) {
        habits.add(new Habit(name, frequency));
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

    // -------- Checklist Logic --------
    public void toggleCompletion(int index, LocalDate date) {
        if (index >= 0 && index < habits.size()) {
            habits.get(index).toggleCompletion(date);
        }
    }

    public boolean isCompletedOnDate(int index, LocalDate date) {
        if (index >= 0 && index < habits.size()) {
            return habits.get(index).isCompletedOn(date);
        }
        return false;
    }

    public boolean hasHabits() {
        return !habits.isEmpty();
    }
}
