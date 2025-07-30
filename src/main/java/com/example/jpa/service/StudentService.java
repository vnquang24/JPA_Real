package com.example.jpa.service;

import com.example.jpa.entity.Student;
import java.util.List;
import java.util.Optional;

/**
 * Student Service interface
 * Định nghĩa các method business logic cho Student
 */
public interface StudentService {
    
    // Basic CRUD operations
    Student saveStudent(Student student);
    Optional<Student> getStudentById(Long id);
    List<Student> getAllStudents();
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
    
    // Search operations
    List<Student> searchStudents(String keyword);
    List<Student> findByNameContaining(String keyword);
    List<Student> findByEmailContaining(String keyword);
    List<Student> findStudentsByCourseId(Long courseId);
    
    // Validation operations
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    
    // Relationship operations
    Student addCourseToStudent(Long studentId, Long courseId);
    Student removeCourseFromStudent(Long studentId, Long courseId);
    void removeAllCoursesFromStudent(Long studentId);
    
    // Business logic
    Student createStudentIfNotExists(String name, String email);
    void deleteStudentWithRelations(Long id);
} 