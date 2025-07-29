package com.example.jpa.service;

import com.example.jpa.entity.Course;
import com.example.jpa.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> searchCourses(String keyword) {
        return courseRepository.searchCourses(keyword);
    }

    public List<Course> findByNameContaining(String keyword) {
        return courseRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Course> findByDescriptionContaining(String keyword) {
        return courseRepository.findByDescriptionContainingIgnoreCase(keyword);
    }

    public long countCoursesByName(String name) {
        return courseRepository.countByName(name);
    }

    public boolean existsByName(String name) {
        List<Course> courses = courseRepository.findByNameContainingIgnoreCase(name);
        return !courses.isEmpty();
    }

    @Transactional
    public void removeAllStudentsFromCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.getStudents().clear();
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourseWithRelations(Long courseId) {
        removeAllStudentsFromCourse(courseId);
        courseRepository.deleteById(courseId);
    }
} 