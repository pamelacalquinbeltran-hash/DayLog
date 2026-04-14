
package DayLog;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Habit {

    private String name;
    private String frequency; // e.g. Daily, Mon/Wed/Fri
    private Set<LocalDate> completedDates;

    public Habit(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
        this.completedDates = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isCompletedOn(LocalDate date) {
        return completedDates.contains(date);
    }

    public void toggleCompletion(LocalDate date) {
        if (completedDates.contains(date)) {
            completedDates.remove(date);
        } else {
            completedDates.add(date);
        }
    }

    @Override
    public String toString() {
        return name + " [" + frequency + "]";
    }
}
