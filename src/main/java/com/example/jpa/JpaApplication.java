package com.example.jpa;

import com.example.jpa.config.StandaloneConfig;
import com.example.jpa.demo.CrudDemo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class JpaApplication {
    
    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
            StandaloneConfig.class
        );
        
        System.out.println("Spring Core JPA Application started successfully!");
        System.out.println("Available beans:");
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("- " + beanName);
        }
        
        // Run CRUD demo
        try {
            CrudDemo crudDemo = context.getBean(CrudDemo.class);
            crudDemo.demonstrateCrudOperations();
        } catch (Exception e) {
            System.err.println("Error running CRUD demo: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Keep the application running
        context.registerShutdownHook();
    }
} 