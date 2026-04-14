/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DayLog;

import java.time.LocalDate;

public class CalendarEvent {

    private String title;
    private LocalDate date;
    private String description;

    public CalendarEvent(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return title + " (" + description + ")";
    }
}
