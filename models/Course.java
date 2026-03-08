package models;

import java.util.*;

/**
 * Course model: title, id, instructor username, enrolled students list, assignments.
 */
public class Course {
    private final String id;
    private final String title;
    private final String instructorUsername;
    private final Set<String> enrolledStudentUsernames = new LinkedHashSet<>(); // order preserved
    private final List<Assignment> assignments = new ArrayList<>();

    public Course(String id, String title, String instructorUsername) {
        this.id = id;
        this.title = title;
        this.instructorUsername = instructorUsername;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructorUsername() { return instructorUsername; }
    public Set<String> getEnrolledStudentUsernames() { return enrolledStudentUsernames; }
    public List<Assignment> getAssignments() { return assignments; }

    public boolean enrollStudent(String username) {
        return enrolledStudentUsernames.add(username);
    }

    public boolean isStudentEnrolled(String username) {
        return enrolledStudentUsernames.contains(username);
    }

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Instructor: %s) Students: %d Assignments: %d",
                id, title, instructorUsername, enrolledStudentUsernames.size(), assignments.size());
    }
}

