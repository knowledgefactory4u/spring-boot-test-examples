package com.knf.dev.demo.service;

import com.knf.dev.demo.document.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findStudentByAgeGreaterThan(Integer age);
    Optional<Student> findByName(String name);
    List<Student> findByAgeLessThan(Integer age);
    void saveAllStudent(List<Student> students);
}