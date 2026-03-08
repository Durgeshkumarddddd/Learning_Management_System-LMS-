package models;

import java.util.HashSet;
import java.util.Set;

/**
 * Student model with basic fields and enrolled courses (by courseId).
 */
public class Student {
    private final String id;
    private final String username;
    private String password; // NOTE: plain text for demo only
    private String fullName;
    private final Set<String> enrolledCourseIds = new HashSet<>();

    public Student(String id, String username, String password, String fullName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Set<String> getEnrolledCourseIds() { return enrolledCourseIds; }

    public boolean enrollCourse(String courseId) {
        return enrolledCourseIds.add(courseId);
    }

    public boolean isEnrolledIn(String courseId) {
        return enrolledCourseIds.contains(courseId);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", fullName, username, id);
    }
}
