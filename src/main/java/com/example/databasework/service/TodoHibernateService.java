package com.example.databasework.service;

import com.example.databasework.Role;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.TodoEntity;
import com.example.databasework.repository.TodoRepository;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import org.springframework.context.event.EventListener;

import java.util.List;

///**
// * todo to read about @Transactional and apply that to all (mb) methods
// */


@Service
public class TodoHibernateService implements TodoService{
    private final TodoRepository todoRepository;
    private final RestClient restClient;
    private final JWTService jwtService;
    private final RestTemplate restTemplate = new RestTemplate();
    //private final JwtFilter jwtFilter;

    @Value("${external-api.base-url}")
    private String baseUrl;

    public TodoHibernateService(TodoRepository todoRepository,
                                RestClient restClient,
                                JWTService jwtService) {

        this.todoRepository = todoRepository;
        this.restClient = restClient;
        this.jwtService = jwtService;
    }



    //jbc и создать два сервиса для интерфейса  для имплимитации и транзакции
//    @PostConstruct // to replace with application ready event and to read we we need that
    @EventListener(ApplicationReadyEvent.class)
    public void loadTodosFromApi() {

        String token = jwtService.generateToken("Bogdan@icloud.com", "ADMIN");
        System.out.println("Токен: " + token);

        MainDto[] response = restClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/todos")
                .retrieve()
                .body(MainDto[].class);

        for (MainDto dto : response) {
            TodoEntity entity = new TodoEntity();
            entity.setCreated_at(Instant.now());
            entity.setUpdated_at(Instant.now());
            entity.setText(dto.title);
            entity.setStatus(dto.completed);
            entity.setIs_visible(true);
            entity.setAuthor("Bogdan");


            todoRepository.save(entity);
        }
    }
//какие проблемы бывают с транзакциями, какой уровень изоляции в транзакциях используют
    //

    public List<TodoEntity> getAllTodos() {
        return todoRepository.findAll();
    }

    public TodoEntity save(TodoEntity todoEntity) {
        return todoRepository.save(todoEntity);
    }

    public void deleteById(int id) {
        todoRepository.deleteById(id);
    }

    //get
    public List<TodoEntity> getAllTodos(Role role) {

        if (role == Role.ADMIN) {
            return todoRepository.findAll();
        }
        if (role == Role.USER) {
            return todoRepository.findAll();

        }
        return List.of();
    }

    //post
    public ResponseEntity<?> addinTodos(Role role, MainDto newTodo) {

        if (role == Role.ADMIN) {
            System.out.println(baseUrl);
            System.out.println("Отправляем запрос во внешний сервис");
            MainDto created = restTemplate.postForObject(
                    baseUrl + "/todos",
                    newTodo,
                    MainDto.class);
            System.out.println("Получили ответ");
            TodoEntity entity = new TodoEntity();
            entity.setId(created.id);
            entity.setCreated_at(Instant.now());
            entity.setUpdated_at(Instant.now());
            entity.setText(created.title);
            entity.setStatus(created.completed);
            entity.setIs_visible(true);
            entity.setAuthor("Bogdan");

            todoRepository.save(entity);

            return ResponseEntity.ok(created);
        }
        if (role == Role.USER) {
            return ResponseEntity.status(403).body("Ты что тут делаешь юзер, только админ может добавлять пользователей");
        }
        return ResponseEntity.status(403).build();
    }
    //delete
    public ResponseEntity<?> deleteTodo(Role role, int id) {
        if (role == Role.ADMIN) {//to move to enum
            restTemplate.delete(baseUrl + "/todos/" + id);
            todoRepository.deleteById(id);
            return ResponseEntity.ok(id);
        }
        if (role == Role.USER) {
            return ResponseEntity.status(403).body("Ты что тут делаешь юзер, только админ может удалять пользователей");

        }
        return ResponseEntity.status(403).build();
    }
    //patch
    public ResponseEntity<?> updateTodo(Role role, MainDto updateData, int id){
     if(role == Role.ADMIN){
        MainDto updatedFromExternal = restTemplate.postForObject(
                baseUrl + "/todos",
                updateData,
                MainDto.class
        );

        TodoEntity entity = new TodoEntity();
        entity.setId(id);
        entity.setCreated_at(Instant.now());
        entity.setUpdated_at(Instant.now());
        entity.setText(updatedFromExternal.title);
        entity.setStatus(updatedFromExternal.completed);
        entity.setIs_visible(true);
        entity.setAuthor("Bogdan");


        todoRepository.save(entity);
    }
            if (role == Role.USER){
        return ResponseEntity.status(403).body("Незя тебе быть здесь пользователь, это дело Админов");
    }
        return ResponseEntity.ok("Обновлено todo с id: " +id );
}
}
