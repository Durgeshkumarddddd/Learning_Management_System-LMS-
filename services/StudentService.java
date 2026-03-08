package services;


import data.DataStore;
import models.Student;
import utils.InputValidator;

import java.util.Optional;

/**
 * Student service: register, login, enroll.
 */
public class StudentService {

    public Optional<Student> registerStudent(String username, String password, String fullName) {
        if (!InputValidator.isValidUsername(username)) {
            System.out.println("Invalid username. Use 3-20 chars (letters, digits, _ . -).");
            return Optional.empty();
        }
        if (!InputValidator.isValidPassword(password)) {
            System.out.println("Invalid password. Minimum 4 characters.");
            return Optional.empty();
        }
        if (DataStore.students.containsKey(username) || DataStore.instructors.containsKey(username)) {
            System.out.println("Username already exists.");
            return Optional.empty();
        }
        String id = DataStore.nextStudentId();
        Student s = new Student(id, username, password, fullName);
        DataStore.students.put(username, s);
        return Optional.of(s);
    }

    public Optional<Student> login(String username, String password) {
        Student s = DataStore.students.get(username);
        if (s == null) return Optional.empty();
        if (!s.getPassword().equals(password)) return Optional.empty();
        return Optional.of(s);
    }

    public boolean enroll(Student student, String courseId) {
        if (student.isEnrolledIn(courseId)) {
            System.out.println("You are already enrolled in this course.");
            return false;
        }
        // delegate to CourseService to add student to course
        CourseService courseService = new CourseService();
        boolean addedToCourse = courseService.enrollStudentInCourse(student.getUsername(), courseId);
        if (addedToCourse) {
            student.enrollCourse(courseId);
            System.out.println("Enrolled successfully.");
            return true;
        } else {
            System.out.println("Enrollment failed (course may not exist).");
            return false;
        }
    }
}

