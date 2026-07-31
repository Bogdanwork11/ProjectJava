package com.example.databasework.repository;


import com.example.databasework.entity.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoCriteriaRepository {
    @Autowired
    private EntityManager entityManager;

    public List<Todo> findByAuthorIdOrderByCreatedAt(Integer authorId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Todo> query = criteriaBuilder.createQuery(Todo.class);

        Root<Todo> root = query.from(Todo.class);

        Predicate authorPredicate = criteriaBuilder.equal(root.get("author").get("id"), authorId);

        query.select(root).where(authorPredicate);

        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

}
