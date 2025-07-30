package com.example.jpa.service;

import com.example.jpa.entity.Course;
import java.util.List;
import java.util.Optional;

/**
 * Course Service interface
 * Định nghĩa các method business logic cho Course
 */
public interface CourseService {
    
    // Basic CRUD operations
    Course saveCourse(Course course);
    Optional<Course> getCourseById(Long id);
    Optional<Course> getCourseByName(String name);
    List<Course> getAllCourses();
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
    
    // Search operations
    List<Course> searchCourses(String keyword);
    List<Course> findByNameContaining(String keyword);
    List<Course> findByDescriptionContaining(String keyword);
    
    // Validation operations
    boolean existsByName(String name);
    boolean existsById(Long id);
    
    // Business logic
    Course createCourseIfNotExists(String name, String description);
    void deleteCourseWithRelations(Long id);
} 