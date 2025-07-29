package com.example.jpa.service;

import com.example.jpa.entity.Course;
import com.example.jpa.entity.Student;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student updateStudent(Long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchStudents(keyword);
    }

    public List<Student> findByNameContaining(String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Student> findByEmailContaining(String keyword) {
        return studentRepository.findByEmailContainingIgnoreCase(keyword);
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    public List<Student> findStudentsByCourseId(Long courseId) {
        return studentRepository.findStudentsByCourseId(courseId);
    }

    @Transactional
    public Student addCourseToStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        student.getCourses().add(course);
        return studentRepository.save(student);
    }

    @Transactional
    public Student removeCourseFromStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        student.getCourses().remove(course);
        return studentRepository.save(student);
    }

    @Transactional
    public void removeAllCoursesFromStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        student.getCourses().clear();
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudentWithRelations(Long studentId) {
        // Xóa tất cả quan hệ trước
        removeAllCoursesFromStudent(studentId);
        // Sau đó xóa học sinh
        studentRepository.deleteById(studentId);
    }
} 