package com.example.databasework.service;

import com.example.databasework.Role;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.AuthorEntity;
import com.example.databasework.entity.StatusEntity;
import com.example.databasework.entity.TodoEntity;
import com.example.databasework.filter.JwtFilter;
import com.example.databasework.repository.AuthorRepository;
import com.example.databasework.repository.StatusRepository;
import com.example.databasework.repository.TodoCriteriaRepository;
import com.example.databasework.repository.TodoRepository;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;


import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Random;

///**
// * todo to read about @Transactional and apply that to all (mb) methods
// */

@Primary
@Service
public class TodoHibernateService implements TodoService{
    private final TodoRepository todoRepository;
    private final RestClient restClient;
    private final JWTService jwtService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final TodoCriteriaRepository todoCriteriaRepository;
    private final JwtFilter jwtFilter;


    @Value("${external-api.base-url}")
    private String baseUrl;

    public TodoHibernateService(TodoRepository todoRepository,
                                RestClient restClient,
                                JWTService jwtService,
                                AuthorRepository authorRepository,
                                StatusRepository statusRepository,
                                TodoCriteriaRepository todoCriteriaRepository,
                                JwtFilter jwtFilter,
                                UserService userService) {

        this.todoRepository = todoRepository;
        this.restClient = restClient;
        this.jwtService = jwtService;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
        this.todoCriteriaRepository = todoCriteriaRepository;
        this.jwtFilter = jwtFilter;


    }



    //jbc и создать два сервиса для интерфейса  для имплимитации и транзакции
//    @PostConstruct // to replace with application ready event and to read we we need that
    @EventListener(ApplicationReadyEvent.class)
    public void loadTodosFromApi() {

//        String token = jwtService.generateToken(user.getlogin(), user.getRole);
//        System.out.println("Токен: " + token);

        MainDto[] response = restClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/todos")
                .retrieve()
                .body(MainDto[].class);

        for (MainDto dto : response) {
            Random random = new Random();
            int randomAuthorId = random.nextInt(2) + 1;
            AuthorEntity author = authorRepository
                    .findById(randomAuthorId)
                    .orElseThrow();

            StatusEntity status = statusRepository
                    .findById(dto.completed() ? 1 : 2)
                    .orElseThrow();


            TodoEntity entity = new TodoEntity();
            entity.setCreatedAt(Instant.now());
            entity.setUpdated_at(Instant.now());
            entity.setText(dto.title());
            entity.setAuthor(author);
            entity.setStatus(status);
            entity.setIs_visible(true);



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

        if (role != Role.ADMIN) {
            return ResponseEntity.status(403).body("Недостаточно прав");
        }

        AuthorEntity author = authorRepository
                .findById(newTodo.authorId())
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        StatusEntity status = statusRepository
                .findById(newTodo.statusId())
                .orElseThrow(() -> new RuntimeException("Статус не найден"));

        TodoEntity entity = new TodoEntity();

        entity.setCreatedAt(Instant.now());
        entity.setUpdated_at(Instant.now());
        entity.setText(newTodo.title());
        entity.setAuthor(author);
        entity.setStatus(status);
        entity.setIs_visible(true);

        todoRepository.save(entity);

        return ResponseEntity.ok(entity);
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
    public ResponseEntity<?> updateTodo(Role role, MainDto updateData, int id) {

        if (role != Role.ADMIN) {
            return ResponseEntity.status(403).body("Недостаточно прав");
        }

        TodoEntity entity = todoRepository
                .findById(id)
                .orElseThrow();

        AuthorEntity author = authorRepository
                .findById(updateData.authorId())
                .orElseThrow();

        StatusEntity status = statusRepository
                .findById(updateData.statusId())
                .orElseThrow();

        entity.setUpdated_at(Instant.now());
        entity.setText(updateData.title());
        entity.setAuthor(author);
        entity.setStatus(status);
        entity.setIs_visible(true);

        todoRepository.save(entity);

        return    ResponseEntity.ok(entity);
    }


    //get author id
    public List<TodoEntity> getIdTodo(Integer authorId, Role role) {
        if (role == Role.ADMIN){

            return todoRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);

        }
        if (role == Role.USER){

            return todoRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);
        }
        return List.of();
    }

    //getcreteria
    public List<TodoEntity> getIdTodoCriteria(Integer authorId, Role role) {
        if (role == Role.ADMIN) {
            return todoCriteriaRepository.findByAuthorIdOrderByCreatedAt(authorId);

        }
        if (role == Role.USER){
            return todoCriteriaRepository.findByAuthorIdOrderByCreatedAt(authorId);
        }
        return List.of();
    }
}
