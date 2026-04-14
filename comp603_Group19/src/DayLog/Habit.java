package DayLog;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Habit {

    private String name;
    private String frequency;
    private final Set<LocalDate> completedDates = new HashSet<>();

    public Habit(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public void toggleCompletion(LocalDate date) {
        if (!completedDates.add(date)) {
            completedDates.remove(date);
        }
    }

    public boolean isCompletedOn(LocalDate date) {
        return completedDates.contains(date);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return name + " [" + frequency + "]";
    }
}
