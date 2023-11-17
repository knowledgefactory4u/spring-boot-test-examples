package com.knf.dev.demo;

import com.knf.dev.demo.dto.Todo;
import com.knf.dev.demo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.nio.charset.Charset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest(TodoService.class)
public class TodoServiceTest {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private TodoService todoService;

    @Value("classpath:todo.json")
    private Resource resource;

    @Test
    void findById_ReturnsTheTodo() throws IOException {

        final Integer id = 1;

        mockRestServiceServer
                .expect(requestTo("/todos/" + id))
                .andRespond(withSuccess(resource.getContentAsString(Charset.defaultCharset()),
                        MediaType.APPLICATION_JSON));

        Todo todo = todoService.findById(id);

        assertEquals(1, todo.getUserId());
        assertEquals(1, todo.getId());
        assertEquals("delectus aut autem", todo.getTitle());
        assertEquals(false, todo.getCompleted());
    }
}
