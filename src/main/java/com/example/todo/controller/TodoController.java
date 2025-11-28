package com.example.todo.controller;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    // 할일 전체 조회
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        List<Todo> todos = todoRepository.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(todos);
    }

    // 특정 id 할일 조회 /api/todos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        Optional<Todo> todo = todoRepository.findById(id); // id로 할일 항목 찾기
        return todo.map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); // 없으면 404 Not Found 반환
    }

    // 할일 생성
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        Todo savedTodo = todoRepository.save(todo); // 새로운 할일 항목 저장
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED); // 저장된 항목 반환
    }

    // 특정 id 할일 수정
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails){
        Optional<Todo> todoOptional = todoRepository.findById(id); // id로 항목 찾기

        if(todoOptional.isPresent()){
            Todo existingTodo = todoOptional.get(); // 항목의 데이터를 existingTodo 변수에 대입
            existingTodo.setTitle(todoDetails.getTitle());
            existingTodo.setDescription(todoDetails.getDescription());
            existingTodo.setCompleted(todoDetails.isCompleted());
            Todo updatedTodo = todoRepository.save(existingTodo); // 수정한 항목을 저장하여 updatedTodo 변수에 대입
            return ResponseEntity.ok(updatedTodo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 특정 id 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long id){
        if(todoRepository.existsById(id)){
            todoRepository.deleteById(id); // 존재 확인 후 삭제
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
