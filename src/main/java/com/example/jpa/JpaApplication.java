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
        
        System.out.println("DANH SÁCH BEANS ĐÃ ĐĂNG KÝ: \n");
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("   - " + beanName);
        }
        
        try {
            CrudDemo crudDemo = context.getBean(CrudDemo.class);
            crudDemo.demonstrateCrudOperations();

        } catch (Exception e) {
            System.err.println("Lỗi khi chạy demo: " + e.getMessage());
            e.printStackTrace();
        }    
        context.registerShutdownHook();
    }
} 