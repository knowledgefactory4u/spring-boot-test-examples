package com.knf.dev.demo;

import com.knf.dev.demo.repository.StudentRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    ConnectionFactory connectionFactory;


    @BeforeEach
    private void setUp(@Value("classpath:/test-student-data.sql")
                       Resource script1,
                       @Value("classpath:/test-student-schema.sql")
                       Resource script2) {
        executeScriptBlocking(script2);
        executeScriptBlocking(script1);
    }


    @Test
    void findStudentByEmail_ReturnsTheStudent() {

        this.studentRepository.findStudentByEmail("alpha@knf.com")
                .as(StepVerifier::create)
                .consumeNextWith(p ->
                        assertEquals("Alpha", p.getName()))
                .verifyComplete();
    }

    private void executeScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils
                        .executeSqlScript(connection, sqlScript))
                .block();
    }
}