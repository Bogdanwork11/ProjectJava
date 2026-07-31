package com.example.databasework.service;

import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.Authors;
import com.example.databasework.entity.Status;
import com.example.databasework.entity.Todo;
import com.example.databasework.repository.AuthorRepository;
import com.example.databasework.repository.StatusRepository;
import com.example.databasework.repository.TodoCriteriaRepository;
import com.example.databasework.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Random;


@Primary
@Service
public class TodoHibernateService implements TodoService {
    private final TodoRepository todoRepository;
    private final RestClient restClient;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final TodoCriteriaRepository todoCriteriaRepository;


    @Value("${external-api.base-url}")
    private String baseUrl;

    public TodoHibernateService(TodoRepository todoRepository,
                                RestClient restClient,
                                AuthorRepository authorRepository,
                                StatusRepository statusRepository,
                                TodoCriteriaRepository todoCriteriaRepository) {

        this.todoRepository = todoRepository;
        this.restClient = restClient;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
        this.todoCriteriaRepository = todoCriteriaRepository;


    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTodosFromApi() {


        MainDto[] response = restClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/todos")
                .retrieve()
                .body(MainDto[].class);

        for (MainDto dto : response) {
            Random random = new Random();
            int randomAuthorId = random.nextInt(2) + 1;
            Authors author = authorRepository
                    .findById(randomAuthorId)
                    .orElseThrow();

            Status status = statusRepository
                    .findById(dto.completed() ? 1 : 2)
                    .orElseThrow();


            Todo entity = new Todo();
            entity.setCreatedAt(Instant.now());
            entity.setUpdated_at(Instant.now());
            entity.setText(dto.title());
            entity.setAuthor(author);
            entity.setStatus(status);
            entity.setIs_visible(true);


            todoRepository.save(entity);
        }
    }


    //get
    public List<Todo> getAllTodos() {


        return todoRepository.findAll();
    }

    //post
    public ResponseEntity<?> addinTodos(MainDto newTodo) {

        Authors author = authorRepository
                .findById(newTodo.authorId())
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        Status status = statusRepository
                .findById(newTodo.statusId())
                .orElseThrow(() -> new RuntimeException("Статус не найден"));

        Todo entity = new Todo();

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
    public ResponseEntity<?> deleteTodo(int id) {
        restTemplate.delete(baseUrl + "/todos/" + id);
        todoRepository.deleteById(id);
        return ResponseEntity.ok(id);

    }

    //patch
    public ResponseEntity<?> updateTodo(MainDto updateData, int id) {


        Todo entity = todoRepository
                .findById(id)
                .orElseThrow();

        Authors author = authorRepository
                .findById(updateData.authorId())
                .orElseThrow();

        Status status = statusRepository
                .findById(updateData.statusId())
                .orElseThrow();

        entity.setUpdated_at(Instant.now());
        entity.setText(updateData.title());
        entity.setAuthor(author);
        entity.setStatus(status);
        entity.setIs_visible(true);

        todoRepository.save(entity);

        return ResponseEntity.ok(entity);
    }


    //get author id
    public List<Todo> getIdTodo(Integer authorId) {

        return todoRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);
    }


    //getcreteria
    public List<Todo> getIdTodoCriteria(Integer authorId) {

        return todoCriteriaRepository.findByAuthorIdOrderByCreatedAt(authorId);
    }


}
