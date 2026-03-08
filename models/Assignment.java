package models;


import java.time.LocalDate;

/**
 * Assignment representation: title, description, dueDate (optional).
 */
public class Assignment {
    private final String title;
    private final String description;
    private final LocalDate dueDate;

    public Assignment(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return String.format("%s - %s (Due: %s)",
                title, description, dueDate == null ? "N/A" : dueDate.toString());
    }
}

