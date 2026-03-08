package services;


import data.DataStore;
import models.Course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Course-level operations: listing, enrolling student into course (by username).
 */
public class CourseService {

    public List<Course> listAllCourses() {
        return DataStore.courses.values().stream().collect(Collectors.toList());
    }

    public Optional<Course> getCourseById(String courseId) {
        return Optional.ofNullable(DataStore.courses.get(courseId));
    }

    public boolean enrollStudentInCourse(String studentUsername, String courseId) {
        Course c = DataStore.courses.get(courseId);
        if (c == null) return false;
        if (c.isStudentEnrolled(studentUsername)) {
            return false;
        }
        return c.enrollStudent(studentUsername);
    }
}

