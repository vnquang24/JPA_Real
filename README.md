# JPA Spring Core Project

## Mô tả
Project JPA sử dụng Spring Core với cấu hình standalone, không phụ thuộc vào Spring Boot auto-configuration.

## Cấu trúc Project

```
src/main/java/com/example/jpa/
├── config/           # Cấu hình Spring
│   ├── DatabaseConfig.java
│   ├── JpaConfig.java
│   └── StandaloneConfig.java
├── entity/           # Entity classes
│   ├── Course.java
│   └── Student.java
├── repository/       # Repository interfaces
│   ├── CourseRepository.java
│   └── StudentRepository.java
├── service/          # Service classes
│   ├── CourseService.java
│   └── StudentService.java
├── dto/              # Data Transfer Objects
│   ├── CourseDTO.java
│   └── StudentDTO.java
├── exception/        # Custom exceptions
│   └── ResourceNotFoundException.java
├── demo/             # Demo classes
│   └── CrudDemo.java
└── JpaApplication.java
```

## Tính năng

### Entity
- **Course**: Khóa học với thông tin cơ bản
- **Student**: Học sinh với email unique
- **Many-to-Many**: Quan hệ giữa Student và Course

### Repository
- **CourseRepository**: CRUD + tìm kiếm theo tên, mô tả
- **StudentRepository**: CRUD + tìm kiếm theo tên, email

### Service
- **CourseService**: Business logic cho Course
- **StudentService**: Business logic cho Student + quản lý quan hệ

### DTO
- **CourseDTO**: Transfer object cho Course
- **StudentDTO**: Transfer object cho Student

## Cấu hình Database

### PostgreSQL
- Host: localhost:5434
- Database: spring_core
- Username: postgres
- Password: 123456

### Cấu hình trong application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/spring_core
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Cách chạy

### 1. Khởi động PostgreSQL
```bash
# Kiểm tra status
sudo systemctl status postgresql

# Khởi động
sudo systemctl start postgresql

# Hoặc sử dụng Docker
docker run --name postgres-jpa -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=spring_core -p 5434:5432 -d postgres
```

### 2. Tạo database
```sql
CREATE DATABASE spring_core;
```

### 3. Chạy ứng dụng
```bash
# Sử dụng Maven Wrapper
./mvnw clean compile exec:java -Dexec.mainClass="com.example.jpa.JpaApplication"

# Hoặc sử dụng Maven
mvn clean compile exec:java -Dexec.mainClass="com.example.jpa.JpaApplication"
```

## Cải tiến so với phiên bản cũ

1. **Cấu trúc package rõ ràng**: Tách biệt entity, repository, service, dto
2. **Repository mở rộng**: Thêm các method tìm kiếm và custom query
3. **Service layer cải tiến**: Thêm validation và business logic
4. **DTO pattern**: Tách biệt data transfer và entity
5. **Exception handling**: Custom exception cho resource not found
6. **Demo mở rộng**: Thêm demo tìm kiếm

## Dependencies

- Spring Core 5.3.30
- Spring Data JPA 2.7.18
- Hibernate 5.6.15.Final
- PostgreSQL Driver 42.7.0
- Jackson 2.15.2
- JUnit 5.10.0 