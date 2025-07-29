package com.example.jpa.repository;

import com.example.jpa.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    // Tìm kiếm theo tên
    Optional<Course> findByName(String name);
    
    // Tìm kiếm theo tên chứa từ khóa
    List<Course> findByNameContainingIgnoreCase(String keyword);
    
    // Tìm kiếm theo mô tả chứa từ khóa
    List<Course> findByDescriptionContainingIgnoreCase(String keyword);
    
    // Custom query với JPQL
    @Query("SELECT c FROM Course c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Course> searchCourses(@Param("keyword") String keyword);
    
    // Đếm số khóa học
    long countByName(String name);
} 