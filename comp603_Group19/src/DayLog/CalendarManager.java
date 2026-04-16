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
        } else if (c == 2) {
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
        if (idx < 1 || idx > events.size()) {
            System.out.println("Invalid event number.");
            return;
        }

        CalendarEvent e = events.get(idx - 1);

        System.out.print("New title: ");
        e.setTitle(scanner.nextLine());

        System.out.print("New description: ");
        e.setDescription(scanner.nextLine());

        System.out.println("Event updated successfully.");
        printAllEvents();
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
        if (idx < 1 || idx > events.size()) {
            System.out.println("Invalid event number.");
            return;
        }

        events.remove(idx - 1);
        System.out.println("Event deleted successfully.");
        printAllEvents();
    }

    public void printAllEvents() {
        events.sort(Comparator.comparing(CalendarEvent::getDate));
        printEventTable(events);
    }

    public void printEventsForDate(LocalDate date) {
        List<CalendarEvent> filtered = events.stream()
                .filter(e -> e.getDate().equals(date))
                .sorted(Comparator.comparing(CalendarEvent::getDate))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("No events found for "
                    + date.format(DayLog.DATE_FORMAT)
                    + " (" + date.getDayOfWeek() + ").");
        } else {
            printEventTable(filtered);
        }
    }

    private void printEventTable(List<CalendarEvent> events) {
        if (events.isEmpty()) {
            System.out.println("No events to display.");
            return;
        }

        System.out.printf("%-4s %-12s %-10s %-20s %-30s%n",
                "No", "Date", "Day", "Title", "Description");
        System.out.println("--------------------------------------------------------------------------");

        for (int i = 0; i < events.size(); i++) {
            CalendarEvent e = events.get(i);

            System.out.printf("%-4d %-12s %-10s %-20s %-30s%n",
                    i + 1,
                    e.getDate().format(DayLog.DATE_FORMAT),
                    e.getDate().getDayOfWeek(),
                    e.getTitle(),
                    e.getDescription());
        }
    }

    public void monthlyOverview() {
        System.out.print("Enter month and year (MM-YYYY): ");
        String[] parts = scanner.nextLine().split("-");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        List<CalendarEvent> filtered = events.stream()
                .filter(e -> e.getDate().getMonthValue() == month
                && e.getDate().getYear() == year)
                .sorted(Comparator.comparing(CalendarEvent::getDate))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("No events found for "
                    + String.format("%02d-%d", month, year) + ".");
        } else {
            printEventTable(filtered);
        }
    }

    public void yearlyOverview() {
        System.out.print("Enter year (YYYY): ");
        int year = Integer.parseInt(scanner.nextLine());

        List<CalendarEvent> filtered = events.stream()
                .filter(e -> e.getDate().getYear() == year)
                .sorted(Comparator.comparing(CalendarEvent::getDate))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("No events found for year " + year + ".");
        } else {
            printEventTable(filtered);
        }
    }
}
