package models;

import java.util.HashSet;
import java.util.Set;

/**
 * Instructor model with ability to manage created courses list.
 */
public class Instructor {
    private final String id;
    private final String username;
    private String password;
    private String fullName;
    private final Set<String> createdCourseIds = new HashSet<>();

    public Instructor(String id, String username, String password, String fullName) {
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

    public Set<String> getCreatedCourseIds() { return createdCourseIds; }

    public void addCreatedCourse(String courseId) { createdCourseIds.add(courseId); }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", fullName, username, id);
    }
}

