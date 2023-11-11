package com.knf.dev.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.knf.dev.demo.controller.StudentController;
import com.knf.dev.demo.entity.Student;
import com.knf.dev.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StudentService studentService;

    //Unit test Save Student REST API
    @Test
    void shouldCreateStudent() throws Exception {

        // Setup
        Student student = new Student(1,"Alpha","alpha@tmail.com");

        given(studentService.createStudent(any(Student.class)))
                .willReturn(Mono.just(student));

        // Action that we are going test
        WebTestClient.ResponseSpec response = webTestClient.post()
                .uri("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(student), Student.class)
                .exchange();

        // Verify the result using assert statements
        response.expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(student.getName())
                .jsonPath("$.email").isEqualTo(student.getEmail());
    }

    // Unit test Get Student by Id REST API
    @Test
    public void shouldReturnStudent() {

        // Setup
        Integer id = 1;
        Student student = new Student("Alpha","alpha@tmail.com");

        given(studentService.getStudentById(id)).willReturn(Mono.just(student));

        // Action that we are going test
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri("/api/v1/students/{id}", Collections.singletonMap("id", id))
                .exchange();

        // Verify the result using assert statements
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(student.getName())
                .jsonPath("$.email").isEqualTo(student.getEmail());
    }

    // Unit test Get All Student REST API
    @Test
    public void shouldReturnListOfStudents() {

        // Setup
        List<Student> students = new ArrayList<>(
                Arrays.asList(new Student(1, "Alpha", "alpha@tmail.com"),
                        new Student(2, "Beta", "beta@tmail.com"),
                        new Student(3, "Gama", "gama@tmail.com")));
        Flux<Student> studentFlux = Flux.fromIterable(students);
        given(studentService.getAllStudents()).willReturn(studentFlux);

        // Action that we are going test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/v1/students")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // Verify the result using assert statements
        response.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Student.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void shouldUpdateStudent() throws Exception {

        // Setup
        Integer id = 1;
        Student student = new Student("Alpha","alpha@tmail.com");

        // Action that we are going test
        given(studentService.updateStudent(any(Integer.class),any(Mono.class)))
                .willReturn(Mono.just(student));

        WebTestClient.ResponseSpec response = webTestClient.put()
                .uri("/api/v1/students/{id}", Collections.singletonMap("id", id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(student), Student.class)
                .exchange();

        // Verify the result using assert statements
        response.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo(student.getName())
                .jsonPath("$.email").isEqualTo(student.getEmail());
    }

    // Unit Test Delete Student REST API
    @Test
    public void shouldDeleteStudent() {

        // Setup
        Integer id = 1;
        Mono<Void> voidReturn  = Mono.empty();
        given(studentService.deleteStudent(id)).willReturn(voidReturn);

        // Action that we are going test
        WebTestClient.ResponseSpec response = webTestClient.delete()
                .uri("/api/v1/students/{id}", Collections.singletonMap("id",  id))
                .exchange();

        // Verify the result using assert statements
        response.expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
