package com.knf.dev.demo.service;

import com.knf.dev.demo.document.Student;
import com.knf.dev.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findStudentByAgeGreaterThan(Integer age) {
        return studentRepository.findByAgeGreaterThan(age);
    }

    @Override
    public Optional<Student> findByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> findByAgeLessThan(Integer age) {
        return studentRepository.findByAgeLessThan(age);
    }

    @Override
    public void saveAllStudent(List<Student> students) {
          studentRepository.saveAll(students);
    }
}