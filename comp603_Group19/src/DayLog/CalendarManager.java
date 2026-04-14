package DayLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {

    private final List<CalendarEvent> events = new ArrayList<>();

    public void addEvent(String title, LocalDate date, String desc) {
        events.add(new CalendarEvent(title, date, desc));
    }

    public void addEventObject(CalendarEvent event) {
        events.add(event);
    }

    public List<CalendarEvent> getEventsForDate(LocalDate date) {
        List<CalendarEvent> result = new ArrayList<>();
        for (CalendarEvent e : events) {
            if (e.getDate().equals(date)) {
                result.add(e);
            }
        }
        return result;
    }

    public void deleteEvent(LocalDate date, int index) {
        List<CalendarEvent> sameDay = getEventsForDate(date);
        if (index >= 0 && index < sameDay.size()) {
            events.remove(sameDay.get(index));
        }
    }

    public List<CalendarEvent> getAllEvents() {
        return events;
    }
}
