package com.example.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(
        mappedBy = "courses",
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    public Course() {}
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }
    
    // Helper methods
    public void addStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }
    
    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
    }
} 