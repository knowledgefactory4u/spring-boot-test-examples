package com.knf.dev.demo.repository;

import com.knf.dev.demo.model.Student;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student,Long> {

    @Query(value = "select * from students as u where u.name = :name")
    Optional<Student> findByName(String name);

    @Query(value = "select * from students as u where u.age > :age")
    List<Student> findByAgeGreaterThan(@Param("age")Integer age);

    @Query(value = "select * from students as u where u.age < :age")
    List<Student> findByAgeLessThan(@Param("age")Integer age);
}
