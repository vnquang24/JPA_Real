package com.example.jpa.repository;

import com.example.jpa.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends BaseRepository<Student, Long> {
    
    // Tìm kiếm theo email
    Optional<Student> findByEmail(String email);
    
    // Tìm kiếm theo tên chứa từ khóa
    List<Student> findByNameContainingIgnoreCase(String keyword);
    
    // Tìm kiếm theo email chứa từ khóa
    List<Student> findByEmailContainingIgnoreCase(String keyword);
    
    // Custom query với JPQL
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.email LIKE %:keyword%")
    List<Student> searchStudents(@Param("keyword") String keyword);
    
    // Tìm học sinh theo khóa học
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);
    
    // Kiểm tra tồn tại theo email
    boolean existsByEmail(String email);
} 