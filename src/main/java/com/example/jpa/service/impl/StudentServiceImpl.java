package com.example.jpa.service.impl;

import com.example.jpa.entity.Course;
import com.example.jpa.entity.Student;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.repository.StudentRepository;
import com.example.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của StudentService
 * Chứa business logic cho Student entity và quản lý quan hệ với Course
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    
    @Override
    public Student saveStudent(Student student) {
        // Validation: Kiểm tra tên học sinh không được trống
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên học sinh không được để trống");
        }
        
        // Validation: Kiểm tra email không được trống
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        
        // Validation: Kiểm tra email có đúng format không
        if (!isValidEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email không đúng định dạng");
        }
        
        // Validation: Kiểm tra email không được trùng lặp
        if (student.getId() == null && studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Học sinh với email '" + student.getEmail() + "' đã tồn tại");
        }
        
        return studentRepository.save(student);
    }
    
    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    @Override
    public Student updateStudent(Long id, Student student) {
        // Kiểm tra student có tồn tại không
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy học sinh với ID: " + id);
        }
        
        // Validation: Kiểm tra email mới không trùng với học sinh khác
        if (student.getEmail() != null && !student.getEmail().trim().isEmpty()) {
            Optional<Student> existingStudent = studentRepository.findByEmail(student.getEmail());
            if (existingStudent.isPresent() && !existingStudent.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email '" + student.getEmail() + "' đã được sử dụng bởi học sinh khác");
            }
        }
        
        student.setId(id);
        return studentRepository.save(student);
    }
    
    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy học sinh với ID: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    @Override
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.searchStudents(keyword.trim());
    }
    
    @Override
    public List<Student> findByNameContaining(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.findByNameContainingIgnoreCase(keyword.trim());
    }
    
    @Override
    public List<Student> findByEmailContaining(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.findByEmailContainingIgnoreCase(keyword.trim());
    }
    
    @Override
    public List<Student> findStudentsByCourseId(Long courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("Course ID không được null");
        }
        return studentRepository.findStudentsByCourseId(courseId);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }
    
    @Override
    @Transactional
    public Student addCourseToStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh với ID: " + studentId));
        
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + courseId));
        
        // Kiểm tra xem học sinh đã đăng ký khóa học này chưa
        if (student.getCourses().contains(course)) {
            throw new IllegalArgumentException("Học sinh đã đăng ký khóa học này");
        }
        
        student.getCourses().add(course);
        return studentRepository.save(student);
    }
    
    @Override
    @Transactional
    public Student removeCourseFromStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh với ID: " + studentId));
        
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + courseId));
        
        // Kiểm tra xem học sinh có đăng ký khóa học này không
        if (!student.getCourses().contains(course)) {
            throw new IllegalArgumentException("Học sinh chưa đăng ký khóa học này");
        }
        
        student.getCourses().remove(course);
        return studentRepository.save(student);
    }
    
    @Override
    @Transactional
    public void removeAllCoursesFromStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh với ID: " + studentId));
        
        student.getCourses().clear();
        studentRepository.save(student);
    }
    
    @Override
    public Student createStudentIfNotExists(String name, String email) {
        Optional<Student> existingStudent = studentRepository.findByEmail(email);
        if (existingStudent.isPresent()) {
            return existingStudent.get();
        }
        
        Student newStudent = new Student(name, email);
        return studentRepository.save(newStudent);
    }
    
    @Override
    @Transactional
    public void deleteStudentWithRelations(Long id) {
        // Xóa tất cả quan hệ với khóa học trước
        removeAllCoursesFromStudent(id);
        
        // Sau đó xóa học sinh
        studentRepository.deleteById(id);
    }
    
    /**
     * Kiểm tra email có đúng định dạng không
     */
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
} 