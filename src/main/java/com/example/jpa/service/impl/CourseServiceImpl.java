package com.example.jpa.service.impl;

import com.example.jpa.entity.Course;
import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của CourseService
 * Chứa business logic cho Course entity
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @Override
    public Course saveCourse(Course course) {
        // Validation: Kiểm tra tên khóa học không được trống
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khóa học không được để trống");
        }
        
        // Validation: Kiểm tra tên khóa học không được trùng lặp
        if (course.getId() == null && courseRepository.existsByName(course.getName())) {
            throw new IllegalArgumentException("Khóa học với tên '" + course.getName() + "' đã tồn tại");
        }
        
        return courseRepository.save(course);
    }
    
    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    @Override
    public Optional<Course> getCourseByName(String name) {
        return courseRepository.findByName(name);
    }
    
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    @Override
    public Course updateCourse(Long id, Course course) {
        // Kiểm tra course có tồn tại không
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id);
        }
        
        course.setId(id);
        return courseRepository.save(course);
    }
    
    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id);
        }
        courseRepository.deleteById(id);
    }
    
    @Override
    public List<Course> searchCourses(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCourses();
        }
        return courseRepository.searchCourses(keyword.trim());
    }
    
    @Override
    public List<Course> findByNameContaining(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCourses();
        }
        return courseRepository.findByNameContainingIgnoreCase(keyword.trim());
    }
    
    @Override
    public List<Course> findByDescriptionContaining(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCourses();
        }
        return courseRepository.findByDescriptionContainingIgnoreCase(keyword.trim());
    }
    
    @Override
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }
    
    @Override
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }
    
    @Override
    public Course createCourseIfNotExists(String name, String description) {
        Optional<Course> existingCourse = courseRepository.findByName(name);
        if (existingCourse.isPresent()) {
            return existingCourse.get();
        }
        
        Course newCourse = new Course(name, description);
        return courseRepository.save(newCourse);
    }
    
    @Override
    @Transactional
    public void deleteCourseWithRelations(Long id) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + id));
        
        // Xóa tất cả quan hệ với học sinh trước
        course.getStudents().clear();
        courseRepository.save(course);
        
        // Sau đó xóa khóa học
        courseRepository.deleteById(id);
    }
} 