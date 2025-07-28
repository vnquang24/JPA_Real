# Spring Core JPA Application

Ứng dụng Spring Core + Spring Data JPA không sử dụng Spring Boot.

## Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/example/jpa/
│   │   ├── config/           # Cấu hình Spring Core
│   │   │   ├── DatabaseConfig.java
│   │   │   ├── JpaConfig.java
│   │   │   ├── WebConfig.java
│   │   │   └── WebAppInitializer.java
│   │   ├── demo/             # Demo CRUD operations
│   │   │   └── CrudDemo.java
│   │   ├── Course.java       # Entity Course
│   │   ├── CourseController.java
│   │   ├── CourseRepository.java
│   │   ├── CourseService.java
│   │   ├── Student.java      # Entity Student
│   │   ├── StudentController.java
│   │   ├── StudentRepository.java
│   │   ├── StudentService.java
│   │   └── JpaApplication.java
│   ├── resources/
│   │   └── application.properties
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml
│       └── index.html
└── test/
    └── java/com/example/jpa/
        └── JpaApplicationTests.java
```

## Yêu cầu hệ thống

- Java 21
- Maven 3.6+
- PostgreSQL 12+

## Cấu hình Database

1. Tạo database PostgreSQL:
```sql
CREATE DATABASE spring_core;
```

2. Cập nhật thông tin kết nối trong `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/spring_core
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Chạy ứng dụng

### 1. Chạy ứng dụng standalone:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.jpa.JpaApplication"
```

### 2. Chạy trên web server (Tomcat):
```bash
mvn clean package
# Copy file WAR vào thư mục webapps của Tomcat
```

### 3. Chạy test:
```bash
mvn test
```

## API Endpoints

### Students
- `GET /students` - Lấy danh sách tất cả students
- `POST /students` - Tạo student mới
- `GET /students/{id}` - Lấy student theo ID
- `PUT /students/{id}` - Cập nhật student
- `DELETE /students/{id}` - Xóa student
- `POST /students/{studentId}/courses/{courseId}` - Thêm course cho student

### Courses
- `GET /courses` - Lấy danh sách tất cả courses
- `POST /courses` - Tạo course mới
- `GET /courses/{id}` - Lấy course theo ID
- `PUT /courses/{id}` - Cập nhật course
- `DELETE /courses/{id}` - Xóa course

## Các chức năng CRUD

Ứng dụng có đầy đủ 5 method CRUD cơ bản:

1. **CREATE** - Tạo mới Student và Course
2. **READ** - Đọc danh sách và chi tiết Student/Course
3. **UPDATE** - Cập nhật thông tin Student/Course
4. **DELETE** - Xóa Student/Course
5. **RELATIONSHIP** - Quản lý quan hệ Many-to-Many giữa Student và Course

## Cấu hình Spring Core

### DatabaseConfig.java
- Cấu hình DataSource
- Cấu hình EntityManagerFactory
- Cấu hình TransactionManager

### JpaConfig.java
- Enable JPA repositories
- Enable transaction management

### WebConfig.java
- Cấu hình Spring Web MVC
- Component scanning

### WebAppInitializer.java
- Cấu hình DispatcherServlet
- Mapping URL patterns

## Entity Configuration

### Student Entity
```java
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_course", ...)
    private Set<Course> courses;
}
```

### Course Entity
```java
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students;
}
```

## Lưu ý

- Ứng dụng sử dụng constructor injection thay vì field injection
- Tất cả service đều có annotation `@Transactional`
- Sử dụng `@JsonIgnore` để tránh circular reference trong JSON
- Cấu hình lazy loading cho các relationship 