package com.knf.dev.demo;

import com.knf.dev.demo.document.Student;
import com.knf.dev.demo.repository.StudentRepository;
import com.knf.dev.demo.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class StudentApplicationIntegrationTests {

    @Autowired
    private StudentService studentService;

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:latest"));


    //Method should be executed before all tests in the current test class
    //Load initial data
    @BeforeAll
    static void setup(@Autowired StudentRepository studentRepository) {

        Student student1 = new Student("101","Alpha","alpha@knf.com",50);
        Student student2 = new Student("102","Beta","beta@knf.com",40);
        Student student3 = new Student("103","Gama","gama@knf.com",30);
        Student student4 = new Student("104","Pekka","pekka@knf.com",20);

        List<Student> students = Arrays.asList(student1,student2,student3,student4);

        studentRepository.saveAll(students);

    }

    @Test
    void findByName_ReturnsTheStudent() {

        final String name ="Alpha";

        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl= "http://localhost:"+port+"/api/v1/students/{name}";

        // Fetch response as List wrapped in ResponseEntity
        ResponseEntity<Student> findByName = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Student>(){},name);

        Student student = findByName.getBody();


        assertThat(student).isNotNull();
        assertThat(student.getEmail()).isEqualTo("alpha@knf.com");
        assertThat(student.getName()).isEqualTo("Alpha");
        assertThat(student.getId()).isEqualTo("101");
        assertThat(student.getAge()).isEqualTo(50);
    }

    @Test
    void findByAgeGreaterThan_ReturnsTheListStudents() {

        final Integer age = 29;

        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl= "http://localhost:"+port+"/api/v1/students/agt/{age}";

        // Fetch response as List wrapped in ResponseEntity
        ResponseEntity<List<Student>> findByAgeGreaterThan = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){},age);
        List<Student> students = findByAgeGreaterThan.getBody();

        //Convert list of students to list of id(String)
        List<String> ids = students.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());


        assertThat(students.size()).isEqualTo(3);
        assertThat(ids).hasSameElementsAs(Arrays.asList("103","102","101"));

    }

    @Test
    void findByAgeLessThan_ReturnsTheListStudents() {

        final Integer age = 31;

        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl= "http://localhost:"+port+"/api/v1/students/alt/{age}";

        // Fetch response as List wrapped in ResponseEntity
        ResponseEntity<List<Student>> findByAgeLessThan = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){},age);
        List<Student> students = findByAgeLessThan.getBody();

        //Convert list of students to list of id(Integer)
        List<String> ids = students.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(2);
        assertThat(ids).hasSameElementsAs(Arrays.asList("104","103"));
    }
}