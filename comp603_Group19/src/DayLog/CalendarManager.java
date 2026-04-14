/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DayLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {

    private final List<CalendarEvent> events = new ArrayList<>();

    public void addEvent(String title, LocalDate date, String description) {
        events.add(new CalendarEvent(title, date, description));
    }

    public List<CalendarEvent> getEventsForDate(LocalDate date) {
        List<CalendarEvent> result = new ArrayList<>();
        for (CalendarEvent event : events) {
            if (event.getDate().equals(date)) {
                result.add(event);
            }
        }
        return result;
    }

    public void deleteEvent(LocalDate date, int index) {
        List<CalendarEvent> sameDayEvents = getEventsForDate(date);
        if (index >= 0 && index < sameDayEvents.size()) {
            events.remove(sameDayEvents.get(index));
        }
    }
}
