package DayLog;

import java.time.DayOfWeek;
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

    public String toFileString() {
        StringBuilder sb = new StringBuilder(name + "|" + frequency);
        completedDates.forEach(d -> sb.append("|").append(d));
        return sb.toString();
    }

    public static Habit fromFileString(String line) {
        String[] p = line.split("\\|");
        Habit h = new Habit(p[0], p[1]);
        for (int i = 2; i < p.length; i++) {
            h.markDone(LocalDate.parse(p[i]));
        }
        return h;
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
