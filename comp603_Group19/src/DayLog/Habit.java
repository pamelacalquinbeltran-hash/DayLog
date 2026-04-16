package DayLog;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Habit {

    private String name;
    private String frequency; // Daily or Mon,Tue,Wed etc
    private final Set<LocalDate> completedDates = new HashSet<>();

    public Habit(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public boolean appliesToDay(DayOfWeek day) {
        if (frequency.equalsIgnoreCase("daily")) {
            return true;
        }
        return frequency.toLowerCase()
                .contains(day.name().substring(0, 3).toLowerCase());
    }

    public void markDone(LocalDate date) {
        completedDates.add(date);
    }

    public boolean isCompletedOn(LocalDate date) {
        return completedDates.contains(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder(name + "|" + frequency);
        for (LocalDate d : completedDates) {
            sb.append("|").append(d);
        }
        return sb.toString();
    }

    public static Habit fromFileString(String line) {
        String[] parts = line.split("\\|");
        Habit h = new Habit(parts[0], parts[1]);
        for (int i = 2; i < parts.length; i++) {
            h.markDone(LocalDate.parse(parts[i]));
        }
        return h;
    }

    @Override
    public String toString() {
        return name + " [" + frequency + "]";
    }
}
