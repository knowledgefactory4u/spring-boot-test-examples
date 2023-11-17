package com.knf.dev.demo.service;

import com.knf.dev.demo.dto.Todo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TodoService {

    private final RestTemplate restTemplate;

    public TodoService(RestTemplateBuilder restTemplateBuilder,
                       @Value("${todo.baseUrl}") String baseUrl) {

        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    public Todo findById(Integer id) {

        ResponseEntity<Todo> response
                = restTemplate.getForEntity("/todos/{id}", Todo.class, id);
        return response.getBody();
    }
}
