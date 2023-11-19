package com.knf.dev.demo.dao;


import com.knf.dev.demo.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@Repository
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;

    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> findByAgeGreaterThan(Integer age) {

        String sqlQuery = "select id, name, email, age from students where age > ?";

        return jdbcTemplate.
                query(sqlQuery, this::mapRowToUser,age);
    }

    @Override
    public Student findByName(String name) {

        String sqlQuery = "select id, name, email, age from students where name = ?";

        return jdbcTemplate.
                    queryForObject(sqlQuery,
                            this::mapRowToUser, name);

    }

    @Override
    public List<Student> findByAgeLessThan(Integer age) {

        String sqlQuery = "select id, name, email, age from students where age < ?";

        return jdbcTemplate.
                query(sqlQuery, this::mapRowToUser,age);
    }

    private Student mapRowToUser(ResultSet resultSet, int rowNum)
            throws SQLException {

        Student student = new Student(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getInt("age"));

        return student;
    }
}
