package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    
    // Basic CRUD operations
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
    long count();
    
    List<T> saveAll(Iterable<T> entities);
    void deleteAll(Iterable<T> entities);
    void deleteAll();
} 