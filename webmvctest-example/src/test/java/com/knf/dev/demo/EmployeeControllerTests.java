package com.knf.dev.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knf.dev.demo.controller.EmployeeController;
import com.knf.dev.demo.entity.Employee;
import com.knf.dev.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private EmployeeRepository employeeRepository;

  @Test
  void shouldUpdateEmployee() throws Exception {

    long id = 1L;

    Employee employee = new Employee(1, "Alpha", "alpha@tmail.com");
    Employee updatedEmployee = new Employee(id, "Beta", "alpha@tmail.com");

    when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
    when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

    mockMvc.perform(put("/api/v1/employees/{id}", id).
                    contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedEmployee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(updatedEmployee.getName()))
            .andExpect(jsonPath("$.email").value(updatedEmployee.getEmail()))
            .andDo(print());
  }

  @Test
  void shouldReturnListOfEmployees() throws Exception {

    List<Employee> employees = new ArrayList<>(
            Arrays.asList(new Employee(1, "Alpha", "alpha@tmail.com"),
                    new Employee(2, "Beta", "beta@tmail.com"),
                    new Employee(3, "Gama", "gama@tmail.com")));

    when(employeeRepository.findAll()).thenReturn(employees);
    mockMvc.perform(get("/api/v1/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(employees.size()))
            .andDo(print());
  }

  @Test
  void shouldReturnEmployee() throws Exception {

    long id = 1L;
    Employee employee = new Employee(1, "Alpha", "alpha@tmail.com");

    when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
    mockMvc.perform(get("/api/v1/employees/{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(employee.getName()))
            .andExpect(jsonPath("$.email").value(employee.getEmail()))
            .andDo(print());
  }

  @Test
  void shouldCreateEmployee() throws Exception {

    Employee employee = new Employee(1, "Alpha", "alpha@tmail.com");

    mockMvc.perform(post("/api/v1/employees").
                    contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)))
            .andExpect(status().isCreated())
            .andDo(print());
  }

  @Test
  void shouldDeleteEmployee() throws Exception {

    long id = 1L;

    doNothing().when(employeeRepository).deleteById(id);
    mockMvc.perform(delete("/api/v1/employees/{id}", id))
            .andExpect(status().isNoContent())
            .andDo(print());
  }
}