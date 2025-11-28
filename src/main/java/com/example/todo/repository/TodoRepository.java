package com.example.todo.repository;

import com.example.todo.domain.Todo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository를 상속받으면 기본적인 CRUD 메소드들이 자동으로 제공됨
    // ex) findAll(), findById(), save(), delete() 등

    // 최신순으로 정렬하여 보이기
    List<Todo> findAllByOrderByCreatedAtDesc();

}
