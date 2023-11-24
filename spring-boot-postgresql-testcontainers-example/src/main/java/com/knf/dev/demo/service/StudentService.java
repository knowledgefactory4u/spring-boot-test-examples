package com.knf.dev.demo.service;

import com.knf.dev.demo.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findStudentByAgeGreaterThan(Integer age);
    Student findByName(String name);
    List<Student> findByAgeLessThan(Integer age);
}
