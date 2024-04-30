package com.knf.dev.demo.repository;

import com.knf.dev.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {


    //Using JPQL query
    @Query("FROM Student WHERE age > ?1")
    List<Student> findStudentByAgeGreaterThan(Integer age);

    //Using native sql query
    @Query(value = "select * from student as u where u.name = :name",
            nativeQuery = true)
    Optional<Student> findByName(@Param("name") String name);

    //Derived Query Method
    List<Student> findByAgeLessThan(Integer age);
    
}