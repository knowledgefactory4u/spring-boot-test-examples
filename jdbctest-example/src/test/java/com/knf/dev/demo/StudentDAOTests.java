package com.knf.dev.demo;

import com.knf.dev.demo.model.Student;
import com.knf.dev.demo.dao.StudentDAO;
import com.knf.dev.demo.dao.StudentDAOImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JdbcTest
public class StudentDAOTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Sql({"/test-student-data.sql"})
    void findByName_ReturnsTheStudent() {

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        Student student = studentDAO.findByName("Alpha");

        assertThat(student).isNotNull();
        assertThat(student.getEmail()).isEqualTo("alpha@knf.com");
        assertThat(student.getName()).isEqualTo("Alpha");
        assertThat(student.getId()).isEqualTo(101);
        assertThat(student.getAge()).isEqualTo(50);
    }

    @Test
    @Sql({"/test-student-data.sql"})
    void findByAgeGreaterThan_ReturnsTheListStudents() {

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        List<Student> students = studentDAO.findByAgeGreaterThan(29);

        //Convert list of students to list of id(Integer)
        List<Integer> ids = students.stream()
                .map(o -> o.getId().intValue())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(3);
        assertThat(ids).hasSameElementsAs(Arrays.asList(103,102,101));
    }

    @Test
    @Sql({"/test-student-data.sql"})
    void findByAgeLessThan_ReturnsTheListStudents() {

        StudentDAO studentDAO = new StudentDAOImpl(jdbcTemplate);
        List<Student> students = studentDAO.findByAgeLessThan(31);

        //Convert list of students to list of id(Integer)
        List<Integer> ids = students.stream()
                .map(o -> o.getId().intValue())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(2);
        assertThat(ids).hasSameElementsAs(Arrays.asList(104,103));
    }
}