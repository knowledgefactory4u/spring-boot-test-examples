package com.knf.dev.demo.repository;

import com.knf.dev.demo.document.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {

    @Query("{name : ?0}")
    Optional<Student> findByName(String name);

    @Query("{ age : { $gte: ?0 } }")
    List<Student> findByAgeGreaterThan(Integer age);

    //Derived Query Method
    List<Student> findByAgeLessThan(Integer age);
}