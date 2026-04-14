package DayLog;

import java.time.LocalDate;

public class CalendarEvent {

    private final String title;
    private final LocalDate date;
    private final String description;

    public CalendarEvent(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toFileString() {
        return title + "|" + date + "|" + description;
    }

    public static CalendarEvent fromFileString(String line) {
        String[] p = line.split("\\|");
        return new CalendarEvent(p[0], LocalDate.parse(p[1]), p[2]);
    }

    @Override
    public String toString() {
        return title + " - " + description;
    }
}
