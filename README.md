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
├── service/          # Service interfaces
│   ├── CourseService.java
│   ├── StudentService.java
│   └── impl/         # Service implementations
│       ├── CourseServiceImpl.java
│       └── StudentServiceImpl.java
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
- **BaseRepository**: Interface cơ bản thay thế JpaRepository
- **CourseRepository**: CRUD + tìm kiếm theo tên, mô tả
- **StudentRepository**: CRUD + tìm kiếm theo tên, email

### Service
- **CourseService/StudentService**: Interface định nghĩa business logic
- **CourseServiceImpl/StudentServiceImpl**: Implementation với validation và transaction

### DTO
- **CourseDTO**: Transfer object cho Course
- **StudentDTO**: Transfer object cho Student

## Cấu hình Database
- Tạo file .env từ .env.example
- Cấu hình tên DB, tài khoản mật khẩu postgreSQL ở file .env

### Cấu hình application.properties
- Copy từ template sang application.properties

## Cách chạy

### 1. Sử dụng Docker Compose (Khuyến nghị)
```bash
# Khởi động PostgreSQL với Docker Compose
docker-compose up -d

# Kiểm tra trạng thái container
docker-compose ps

### 2. Chạy ứng dụng
```bash
# Sử dụng Maven Wrapper
./mvnw clean compile exec:java -Dexec.mainClass="com.example.jpa.JpaApplication"

# Hoặc sử dụng Maven
mvn clean compile exec:java -Dexec.mainClass="com.example.jpa.JpaApplication"
```
