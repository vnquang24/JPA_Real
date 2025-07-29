package com.example.jpa.demo;

import com.example.jpa.entity.Course;
import com.example.jpa.entity.Student;
import com.example.jpa.service.CourseService;
import com.example.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        // CREATE - Tạo dữ liệu
        System.out.println("\n1. CREATE OPERATIONS");
        
        Course course1 = new Course("Java Programming", "Học lập trình Java cơ bản đến nâng cao");
        Course course2 = new Course("Spring Framework", "Học Spring Framework và Spring Boot");
        Course course3 = new Course("Database Design", "Thiết kế cơ sở dữ liệu và SQL");
        Course course4 = new Course("Web Development", "Phát triển web với HTML, CSS, JavaScript");
        
        Course savedCourse1 = courseService.saveCourse(course1);
        Course savedCourse2 = courseService.saveCourse(course2);
        Course savedCourse3 = courseService.saveCourse(course3);
        Course savedCourse4 = courseService.saveCourse(course4);
        
        System.out.println("   - ID: " + savedCourse1.getId() + " | " + savedCourse1.getName());
        System.out.println("   - ID: " + savedCourse2.getId() + " | " + savedCourse2.getName());
        System.out.println("   - ID: " + savedCourse3.getId() + " | " + savedCourse3.getName());
        System.out.println("   - ID: " + savedCourse4.getId() + " | " + savedCourse4.getName());
        
        // Tạo students
        Student student1 = new Student("Nguyễn Văn An", "nguyenvana@email.com");
        Student student2 = new Student("Trần Thị Bình", "tranthib@email.com");
        Student student3 = new Student("Lê Văn Cường", "levanc@email.com");
        
        Student savedStudent1 = studentService.saveStudent(student1);
        Student savedStudent2 = studentService.saveStudent(student2);
        Student savedStudent3 = studentService.saveStudent(student3);
        
        System.out.println("Đã tạo " + 3 + " học sinh:");
        System.out.println("   - ID: " + savedStudent1.getId() + " | " + savedStudent1.getName() + " | " + savedStudent1.getEmail());
        System.out.println("   - ID: " + savedStudent2.getId() + " | " + savedStudent2.getName() + " | " + savedStudent2.getEmail());
        System.out.println("   - ID: " + savedStudent3.getId() + " | " + savedStudent3.getName() + " | " + savedStudent3.getEmail());
        
        // READ - Đọc dữ liệu
        System.out.println("\n2. READ OPERATIONS");
        
        List<Course> allCourses = courseService.getAllCourses();
        List<Student> allStudents = studentService.getAllStudents();
        System.out.println("Tổng số khóa học: " + allCourses.size());
        System.out.println("Tổng số học sinh: " + allStudents.size());
        
        System.out.println("\nTìm kiếm theo ID:");
        Optional<Course> foundCourse = courseService.getCourseById(savedCourse1.getId());
        Optional<Student> foundStudent = studentService.getStudentById(savedStudent1.getId());
        
        if (foundCourse.isPresent()) {
            Course course = foundCourse.get();
            System.out.println("   Tìm thấy khóa học: ID=" + course.getId() + " | Tên=" + course.getName() + " | Mô tả=" + course.getDescription());
        } else {
            System.out.println("   Không tìm thấy khóa học");
        }
        
        if (foundStudent.isPresent()) {
            Student student = foundStudent.get();
            System.out.println("   Tìm thấy học sinh: ID=" + student.getId() + " | Tên=" + student.getName() + " | Email=" + student.getEmail());
        } else {
            System.out.println("   Không tìm thấy học sinh");
        }
        
        // SEARCH - Tìm kiếm
        System.out.println("\n3. SEARCH OPERATIONS");
        
        System.out.println("Tìm kiếm khóa học chứa từ 'Java':");
        List<Course> javaCourses = courseService.searchCourses("Java");
        System.out.println("   Tìm thấy " + javaCourses.size() + " khóa học:");
        javaCourses.forEach(course -> 
            System.out.println("      - " + course.getName() + " | " + course.getDescription())
        );
        
        System.out.println("\nTìm kiếm học sinh chứa từ 'Nguyễn':");
        List<Student> nguyenStudents = studentService.searchStudents("Nguyễn");
        System.out.println("   Tìm thấy " + nguyenStudents.size() + " học sinh:");
        nguyenStudents.forEach(student -> 
            System.out.println("      - " + student.getName() + " | " + student.getEmail())
        );
        
        // UPDATE - Cập nhật
        System.out.println("\n4. UPDATE OPERATIONS");
        
        System.out.println("Cập nhật mô tả khóa học Java:");
        savedCourse1.setDescription("Học lập trình Java từ cơ bản đến nâng cao, bao gồm OOP, Collections, Streams");
        Course updatedCourse = courseService.saveCourse(savedCourse1);
        System.out.println("   Đã cập nhật: " + updatedCourse.getName() + " | " + updatedCourse.getDescription());
        
        System.out.println("\nCập nhật email học sinh:");
        savedStudent1.setEmail("nguyenvana.updated@email.com");
        Student updatedStudent = studentService.updateStudent(savedStudent1.getId(), savedStudent1);
        System.out.println("   Đã cập nhật: " + updatedStudent.getName() + " | " + updatedStudent.getEmail());
        
        // RELATIONSHIP - Quan hệ
        System.out.println("\n5. RELATIONSHIP OPERATIONS");
        
        System.out.println("Đăng ký khóa học cho học sinh:");
        
        // Học sinh 1 đăng ký nhiều khóa học
        Student studentWithCourses = studentService.addCourseToStudent(savedStudent1.getId(), savedCourse1.getId());
        studentWithCourses = studentService.addCourseToStudent(savedStudent1.getId(), savedCourse2.getId());
        System.out.println("   " + studentWithCourses.getName() + " đã đăng ký " + studentWithCourses.getCourses().size() + " khóa học");
        
        // Học sinh 2 đăng ký khóa học
        Student student2WithCourse = studentService.addCourseToStudent(savedStudent2.getId(), savedCourse1.getId());
        student2WithCourse = studentService.addCourseToStudent(savedStudent2.getId(), savedCourse3.getId());
        System.out.println("   " + student2WithCourse.getName() + " đã đăng ký " + student2WithCourse.getCourses().size() + " khóa học");
        
        // Hiển thị danh sách học sinh theo khóa học
        System.out.println("\nDanh sách học sinh theo khóa học:");
        List<Student> studentsInJava = studentService.findStudentsByCourseId(savedCourse1.getId());
        System.out.println("   Khóa học '" + savedCourse1.getName() + "' có " + studentsInJava.size() + " học sinh:");
        studentsInJava.forEach(student -> 
            System.out.println("      - " + student.getName() + " | " + student.getEmail())
        );
        
        // VALIDATION - Kiểm tra
        System.out.println("\n6. VALIDATION OPERATIONS");
        
        System.out.println("Kiểm tra email đã tồn tại:");
        boolean existsEmail = studentService.existsByEmail("nguyenvana.updated@email.com");
        System.out.println("   Email 'nguyenvana.updated@email.com' tồn tại: " + existsEmail);
        
        boolean existsNewEmail = studentService.existsByEmail("newemail@test.com");
        System.out.println("   Email 'newemail@test.com' tồn tại: " + existsNewEmail);
        
        System.out.println("\nKiểm tra tên khóa học đã tồn tại:");
        boolean existsCourse = courseService.existsByName("Java Programming");
        System.out.println("   Khóa học 'Java Programming' tồn tại: " + existsCourse);
        
        boolean existsNewCourse = courseService.existsByName("New Course");
        System.out.println("   Khóa học 'New Course' tồn tại: " + existsNewCourse);
        
        // DELETE - Xóa
        System.out.println("\n7. DELETE OPERATIONS");
        
        try {
            courseService.deleteCourseWithRelations(savedCourse3.getId());
            System.out.println("   Đã xóa khóa học: " + savedCourse3.getName());
        } catch (Exception e) {
            System.out.println("   Lỗi khi xóa khóa học: " + e.getMessage());
        }
        
        // Final state
        System.out.println("\n8. Cuối cùng");
        
        List<Course> remainingCourses = courseService.getAllCourses();
        List<Student> remainingStudents = studentService.getAllStudents();
        
        System.out.println("Tổng kết:");
        System.out.println("   Khóa học còn lại: " + remainingCourses.size() + "/4");
        System.out.println("   Học sinh còn lại: " + remainingStudents.size() + "/3");
        
        System.out.println("\nDanh sách khóa học còn lại:");
        remainingCourses.forEach(course -> 
            System.out.println("   - " + course.getName() + " | " + course.getDescription())
        );
        
        System.out.println("\nDanh sách học sinh còn lại:");
        remainingStudents.forEach(student -> 
            System.out.println("   - " + student.getName() + " | " + student.getEmail())
        );
        
    }
} 