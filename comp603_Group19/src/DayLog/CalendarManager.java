package DayLog;

import static DayLog.DayLog.DATE_FORMAT;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CalendarManager {

    private final List<CalendarEvent> events = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addEventObject(CalendarEvent e) {
        events.add(e);
    }

    public List<CalendarEvent> getAllEvents() {
        events.sort(Comparator.comparing(CalendarEvent::getDate));
        return events;
    }

    public void addEventPrompt() {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Date (DD-MM-YYYY): ");
            LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);

            System.out.print("Description: ");
            String desc = scanner.nextLine();

            events.add(new CalendarEvent(title, date, desc));
            System.out.println("Event added successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
        }
    }

    public void viewEventsMenu() {
        System.out.println("1. View All Events");
        System.out.println("2. View by Date");

        int c = Integer.parseInt(scanner.nextLine());
        if (c == 1) {
            printAllEvents(); 
        }else if (c == 2) {
            viewByDateWithBack();
        }
    }

    public void viewByDateWithBack() {
        try {
            System.out.print("Enter date (DD-MM-YYYY) or 0 to back: ");
            String in = scanner.nextLine();
            if (in.equals("0")) {
                return;
            }

            LocalDate date = LocalDate.parse(in, DATE_FORMAT);
            printEventsForDate(date);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
        }
    }

    public void editEventWithBack() {
        printAllEvents();
        if (events.isEmpty()) {
            return;
        }

        System.out.print("Select event number to edit (0 to back): ");
        int idx = Integer.parseInt(scanner.nextLine());
        if (idx == 0) {
            return;
        }

        CalendarEvent e = getAllEvents().get(idx - 1);
        System.out.print("New title: ");
        e.setTitle(scanner.nextLine());
        System.out.print("New description: ");
        e.setDescription(scanner.nextLine());

        System.out.println("Event updated successfully.");
    }

    public void deleteEventWithBack() {
        printAllEvents();
        if (events.isEmpty()) {
            return;
        }

        System.out.print("Select event number to delete (0 to back): ");
        int idx = Integer.parseInt(scanner.nextLine());
        if (idx == 0) {
            return;
        }

        events.remove(idx - 1);
        System.out.println("Event deleted successfully.");
    }

    public void printAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }
        getAllEvents().forEach(e
                -> System.out.println(e.getDate().format(DATE_FORMAT)
                        + " (" + e.getDate().getDayOfWeek() + ") - " + e));
    }

    public void printEventsForDate(LocalDate date) {
        var list = events.stream()
                .filter(e -> e.getDate().equals(date))
                .toList();

        if (list.isEmpty()) {
            System.out.println("No events found for "
                    + date.format(DATE_FORMAT) + ".");
        } else {
            list.forEach(e -> System.out.println("- " + e));
        }
    }

    public void monthlyOverview() {
        System.out.println("(Monthly overview unchanged)");
    }

    public void yearlyOverview() {
        System.out.println("(Yearly overview unchanged)");
    }
}
