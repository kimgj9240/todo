package com.example.todo.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="todos") // DB의 todos 테이블과 매핑
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    private String description;
    
    @Column(name = "completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed;
    
    @Column(name = "created_at", updatable = false) // 업데이트 불가
    private LocalDateTime createdAt;
    
    @PrePersist // 엔티티가 저장되기 전에 실행
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
