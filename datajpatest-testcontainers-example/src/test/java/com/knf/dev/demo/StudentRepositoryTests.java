package com.knf.dev.demo;

import com.knf.dev.demo.entity.Student;
import com.knf.dev.demo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/test-student-data.sql"})
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Test
    void findByName_ReturnsTheStudent() {

        Student student = studentRepository.findByName("Alpha").get();
        assertThat(student).isNotNull();
        assertThat(student.getEmail()).isEqualTo("alpha@knf.com");
        assertThat(student.getName()).isEqualTo("Alpha");
        assertThat(student.getId()).isEqualTo(101);
        assertThat(student.getAge()).isEqualTo(50);
    }

    @Test
    void findByAgeGreaterThan_ReturnsTheListStudents() {

        List<Student> students = studentRepository.findStudentByAgeGreaterThan(29);

        //Convert list of students to list of id(Integer)
        List<Integer> ids = students.stream()
                .map(o -> o.getId().intValue())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(3);
        assertThat(ids).hasSameElementsAs(Arrays.asList(103,102,101));
    }

    @Test
    void findByAgeLessThan_ReturnsTheListStudents() {

        List<Student> students = studentRepository.findByAgeLessThan(31);

        //Convert list of students to list of id(Integer)
        List<Integer> ids = students.stream()
                .map(o -> o.getId().intValue())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(2);
        assertThat(ids).hasSameElementsAs(Arrays.asList(104,103));
    }
}
