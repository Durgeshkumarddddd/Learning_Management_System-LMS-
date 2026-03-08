import data.DataStore;
import models.*;
import services.*;
import utils.ConsoleHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * CLI entry point. Provides simple menus for Students and Instructors.
 */
public class Main {
    private static final StudentService studentService = new StudentService();
    private static final InstructorService instructorService = new InstructorService();
    private static final CourseService courseService = new CourseService();

    public static void main(String[] args) {
        seedDemoData(); // optional demo data
        loopMainMenu();
    }

    private static void loopMainMenu() {
        while (true) {
            System.out.println("\n=== MINI LMS ===");
            System.out.println("1) Student - Register");
            System.out.println("2) Student - Login");
            System.out.println("3) Instructor - Register");
            System.out.println("4) Instructor - Login");
            System.out.println("5) List all courses");
            System.out.println("0) Exit");
            int choice = ConsoleHelper.readInt("Choose: ", -1);
            switch (choice) {
                case 1: studentRegisterFlow(); break;
                case 2: studentLoginFlow(); break;
                case 3: instructorRegisterFlow(); break;
                case 4: instructorLoginFlow(); break;
                case 5: listCourses(); break;
                case 0: System.out.println("Bye!"); return;
                default: System.out.println("Invalid choice."); break;
            }
        }
    }

    private static void studentRegisterFlow() {
        String username = ConsoleHelper.readLine("Choose username: ");
        String password = ConsoleHelper.readLine("Choose password: ");
        String fullName = ConsoleHelper.readLine("Full name: ");
        Optional<Student> s = studentService.registerStudent(username, password, fullName);
        s.ifPresent(student -> System.out.println("Registered: " + student));
    }

    private static void studentLoginFlow() {
        String username = ConsoleHelper.readLine("Username: ");
        String password = ConsoleHelper.readLine("Password: ");
        Optional<Student> maybe = studentService.login(username, password);
        if (maybe.isEmpty()) {
            System.out.println("Login failed.");
            return;
        }
        Student s = maybe.get();
        System.out.println("Welcome " + s.getFullName());
        studentMenu(s);
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1) List available courses");
            System.out.println("2) Enroll in course (by id)");
            System.out.println("3) My enrollments");
            System.out.println("0) Logout");
            int ch = ConsoleHelper.readInt("Choice: ", -1);
            if (ch == 1) listCourses();
            else if (ch == 2) {
                String courseId = ConsoleHelper.readLine("Course ID: ");
                studentService.enroll(student, courseId);
            }
            else if (ch == 3) {
                if (student.getEnrolledCourseIds().isEmpty()) {
                    System.out.println("No enrollments.");
                } else {
                    student.getEnrolledCourseIds().forEach(id -> {
                        courseService.getCourseById(id).ifPresent(System.out::println);
                    });
                }
            }
            else if (ch == 0) break;
            else System.out.println("Invalid.");
        }
    }

    private static void instructorRegisterFlow() {
        String username = ConsoleHelper.readLine("Username: ");
        String password = ConsoleHelper.readLine("Password: ");
        String fullName = ConsoleHelper.readLine("Full name: ");
        Optional<Instructor> i = instructorService.registerInstructor(username, password, fullName);
        i.ifPresent(instr -> System.out.println("Registered: " + instr));
    }

    private static void instructorLoginFlow() {
        String username = ConsoleHelper.readLine("Username: ");
        String password = ConsoleHelper.readLine("Password: ");
        Optional<Instructor> maybe = instructorService.login(username, password);
        if (maybe.isEmpty()) {
            System.out.println("Login failed.");
            return;
        }
        Instructor inst = maybe.get();
        System.out.println("Welcome " + inst.getFullName());
        instructorMenu(inst);
    }

    private static void instructorMenu(Instructor instructor) {
        while (true) {
            System.out.println("\n--- Instructor Menu ---");
            System.out.println("1) Create course");
            System.out.println("2) Add assignment to course");
            System.out.println("3) View my courses");
            System.out.println("0) Logout");
            int ch = ConsoleHelper.readInt("Choice: ", -1);
            if (ch == 1) {
                String title = ConsoleHelper.readLine("Course title: ");
                instructorService.createCourse(instructor, title);
            } else if (ch == 2) {
                String courseId = ConsoleHelper.readLine("Course ID: ");
                String title = ConsoleHelper.readLine("Assignment title: ");
                String desc = ConsoleHelper.readLine("Description: ");
                String due = ConsoleHelper.readLine("Due date (YYYY-MM-DD) or blank: ");
                LocalDate dueDate = null;
                if (!due.trim().isEmpty()) {
                    try { dueDate = LocalDate.parse(due.trim()); } catch (Exception e) { System.out.println("Invalid date format."); }
                }
                instructorService.addAssignment(instructor, courseId, title, desc, dueDate);
            } else if (ch == 3) {
                if (instructor.getCreatedCourseIds().isEmpty()) {
                    System.out.println("You have not created any courses.");
                } else {
                    instructor.getCreatedCourseIds().forEach(cid -> courseService.getCourseById(cid).ifPresent(System.out::println));
                }
            } else if (ch == 0) break;
            else System.out.println("Invalid.");
        }
    }

    private static void listCourses() {
        List<Course> courses = courseService.listAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("--- All Courses ---");
        courses.forEach(System.out::println);
    }

    private static void seedDemoData() {
        if (DataStore.instructors.isEmpty()) {
            InstructorService ins = new InstructorService();
            var i1 = ins.registerInstructor("alice", "pass", "Alice Instructor").orElse(null);
            var i2 = ins.registerInstructor("bob", "pass", "Bob Instructor").orElse(null);
            if (i1 != null) ins.createCourse(i1, "Introduction to Java");
            if (i1 != null) ins.createCourse(i1, "Data Structures");
            if (i2 != null) ins.createCourse(i2, "Web Development");
        }
    }
}

