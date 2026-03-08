package data;

import models.Student;
import models.Instructor;
import models.Course;

import java.util.*;

/**
 * Simple in-memory datastore. Acts like a tiny DB. All collections are static
 * so they persist for the app lifetime.
 */
public class DataStore {
    public static final Map<String, Student> students = new HashMap<>(); // key: username
    public static final Map<String, Instructor> instructors = new HashMap<>(); // key: username
    public static final Map<String, Course> courses = new HashMap<>(); // key: courseId
    private static long studentIdCounter = 1000;
    private static long instructorIdCounter = 5000;
    private static long courseIdCounter = 2000;

    public static synchronized String nextStudentId() {
        return "S" + (studentIdCounter++);
    }

    public static synchronized String nextInstructorId() {
        return "I" + (instructorIdCounter++);
    }

    public static synchronized String nextCourseId() {
        return "C" + (courseIdCounter++);
    }

    // utility helper to find course by title (case-insensitive)
    public static Optional<Course> findCourseByTitle(String title) {
        String t = title == null ? "" : title.trim().toLowerCase();
        return courses.values().stream()
                .filter(c -> c.getTitle().toLowerCase().equals(t))
                .findFirst();
    }
}

