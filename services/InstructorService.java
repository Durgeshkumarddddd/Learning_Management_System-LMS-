package services;

import data.DataStore;
import models.Instructor;
import models.Course;
import models.Assignment;
import utils.InputValidator;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Instructor service: register/login/create course/add assignment.
 */
public class InstructorService {

    public Optional<Instructor> registerInstructor(String username, String password, String fullName) {
        if (!InputValidator.isValidUsername(username)) {
            System.out.println("Invalid username.");
            return Optional.empty();
        }
        if (!InputValidator.isValidPassword(password)) {
            System.out.println("Invalid password.");
            return Optional.empty();
        }
        if (DataStore.instructors.containsKey(username) || DataStore.students.containsKey(username)) {
            System.out.println("Username already taken.");
            return Optional.empty();
        }
        String id = DataStore.nextInstructorId();
        Instructor i = new Instructor(id, username, password, fullName);
        DataStore.instructors.put(username, i);
        return Optional.of(i);
    }

    public Optional<Instructor> login(String username, String password) {
        Instructor i = DataStore.instructors.get(username);
        if (i == null) return Optional.empty();
        if (!i.getPassword().equals(password)) return Optional.empty();
        return Optional.of(i);
    }

    public Optional<Course> createCourse(Instructor instructor, String title) {
        if (InputValidator.isBlank(title)) {
            System.out.println("Title required.");
            return Optional.empty();
        }
        // Avoid duplicate course titles
        if (DataStore.findCourseByTitle(title).isPresent()) {
            System.out.println("Course with same title already exists.");
            return Optional.empty();
        }
        String courseId = DataStore.nextCourseId();
        Course c = new Course(courseId, title, instructor.getUsername());
        DataStore.courses.put(courseId, c);
        instructor.addCreatedCourse(courseId);
        System.out.println("Course created: " + c);
        return Optional.of(c);
    }

    public boolean addAssignment(Instructor instructor, String courseId, String title, String description, LocalDate dueDate) {
        Course c = DataStore.courses.get(courseId);
        if (c == null) {
            System.out.println("Course not found.");
            return false;
        }
        if (!c.getInstructorUsername().equals(instructor.getUsername())) {
            System.out.println("You are not the instructor of this course.");
            return false;
        }
        Assignment a = new Assignment(title, description, dueDate);
        c.addAssignment(a);
        System.out.println("Assignment added.");
        return true;
    }
}
