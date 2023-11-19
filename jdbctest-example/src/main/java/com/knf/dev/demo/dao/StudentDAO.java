package com.knf.dev.demo.dao;

import com.knf.dev.demo.model.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> findByAgeGreaterThan(Integer age);

    Student findByName(String name);

    List<Student> findByAgeLessThan(Integer age);

}
