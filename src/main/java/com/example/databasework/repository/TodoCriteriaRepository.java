package com.example.databasework.repository;


import com.example.databasework.entity.TodoEntity;
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

    public List<TodoEntity> findByAuthorIdOrderByCreatedAt(Integer authorId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<TodoEntity> query = criteriaBuilder.createQuery(TodoEntity.class);

        Root<TodoEntity> root = query.from(TodoEntity.class);

        Predicate authorPredicate = criteriaBuilder.equal(root.get("author").get("id"), authorId);

        query.select(root).where(authorPredicate);

        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        return entityManager.createQuery(query).getResultList();
    }

}
