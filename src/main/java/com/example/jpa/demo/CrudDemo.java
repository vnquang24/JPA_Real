package com.example.jpa.demo;

import com.example.jpa.Course;
import com.example.jpa.CourseService;
import com.example.jpa.Student;
import com.example.jpa.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CrudDemo {
    
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public CrudDemo(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public void demonstrateCrudOperations() {
        System.out.println("=== DEMO CRUD OPERATIONS ===");
        
        // CREATE - Tạo mới
        System.out.println("\n1. CREATE Operations:");
        
        // Tạo courses
        Course course1 = new Course("Java Programming", "Học lập trình Java cơ bản");
        Course course2 = new Course("Spring Framework", "Học Spring Framework");
        Course savedCourse1 = courseService.saveCourse(course1);
        Course savedCourse2 = courseService.saveCourse(course2);
        System.out.println("Created Course 1: " + savedCourse1.getName());
        System.out.println("Created Course 2: " + savedCourse2.getName());
        
        // Tạo students
        Student student1 = new Student("Nguyễn Văn A", "nguyenvana@email.com");
        Student student2 = new Student("Trần Thị B", "tranthib@email.com");
        Student savedStudent1 = studentService.saveStudent(student1);
        Student savedStudent2 = studentService.saveStudent(student2);
        System.out.println("Created Student 1: " + savedStudent1.getName());
        System.out.println("Created Student 2: " + savedStudent2.getName());
        
        // READ - Đọc dữ liệu
        System.out.println("\n2. READ Operations:");
        List<Course> allCourses = courseService.getAllCourses();
        List<Student> allStudents = studentService.getAllStudents();
        System.out.println("All Courses: " + allCourses.size());
        System.out.println("All Students: " + allStudents.size());
        
        Optional<Course> foundCourse = courseService.getCourseById(savedCourse1.getId());
        Optional<Student> foundStudent = studentService.getStudentById(savedStudent1.getId());
        System.out.println("Found Course: " + foundCourse.map(Course::getName).orElse("Not found"));
        System.out.println("Found Student: " + foundStudent.map(Student::getName).orElse("Not found"));
        
        // UPDATE - Cập nhật
        System.out.println("\n3. UPDATE Operations:");
        savedCourse1.setDescription("Học lập trình Java nâng cao");
        Course updatedCourse = courseService.saveCourse(savedCourse1);
        System.out.println("Updated Course Description: " + updatedCourse.getDescription());
        
        savedStudent1.setEmail("nguyenvana.new@email.com");
        Student updatedStudent = studentService.updateStudent(savedStudent1.getId(), savedStudent1);
        System.out.println("Updated Student Email: " + updatedStudent.getEmail());
        
        // RELATIONSHIP - Quan hệ
        System.out.println("\n4. RELATIONSHIP Operations:");
        Student studentWithCourse = studentService.addCourseToStudent(savedStudent1.getId(), savedCourse1.getId());
        System.out.println("Student " + studentWithCourse.getName() + " enrolled in course: " + savedCourse1.getName());
        
        // DELETE - Xóa
        System.out.println("\n5. DELETE Operations:");
        courseService.deleteCourse(savedCourse2.getId());
        System.out.println("Deleted Course: " + savedCourse2.getName());
        
        studentService.deleteStudent(savedStudent2.getId());
        System.out.println("Deleted Student: " + savedStudent2.getName());
        
        // Final state
        System.out.println("\n6. Final State:");
        List<Course> remainingCourses = courseService.getAllCourses();
        List<Student> remainingStudents = studentService.getAllStudents();
        System.out.println("Remaining Courses: " + remainingCourses.size());
        System.out.println("Remaining Students: " + remainingStudents.size());
        
        System.out.println("\n=== CRUD DEMO COMPLETED ===");
    }
} 