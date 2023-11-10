package com.knf.dev.demo.controller;

import com.knf.dev.demo.entity.Employee;
import com.knf.dev.demo.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;


    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {

            List<Employee> employees = employeeRepository.findAll();
            return new ResponseEntity<>(employees, HttpStatus.OK);

    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {

            Optional<Employee> employee = employeeRepository.findById(id);
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

            Employee _employee = employeeRepository.
                    save(new Employee(employee.getName(), employee.getEmail()));
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);

    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id,
                                                   @RequestBody Employee employee) {

        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
             Employee _employee = employeeData.get();
            _employee.setName(employee.getName());
            _employee.setEmail(employee.getEmail());

            return new ResponseEntity<>(employeeRepository.save(_employee),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") long id) {

            employeeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
