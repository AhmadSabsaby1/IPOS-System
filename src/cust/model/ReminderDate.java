package cust.model;

import java.time.LocalDate;

public class ReminderDate {
    private LocalDate date;
    private boolean now = false;
    public ReminderDate() {

    }

    public ReminderDate setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isNow() {
        return now;
    }

    public void setIsNow() {
        now = true;
    }

    @Override
    public String toString() {
        if (now)
            return "now";
        else
            return date.toString();
    }
}
