package com.knf.dev.demo;

import com.knf.dev.demo.entity.Student;
import com.knf.dev.demo.service.StudentService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentApplicationIntegrationTest {

	@Autowired
	private StudentService studentService;

	@LocalServerPort
	private Integer port;

	@Container
	@ServiceConnection
	public static PostgreSQLContainer postgreSQLContainer =
			new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

	@BeforeAll
	static void beforeAll(){
		postgreSQLContainer.start();
	}

	@Test
	@Sql({"/clear-data.sql","/test-student-data.sql"})
	void findByAgeGreaterThan_ReturnsTheListStudents() {

		final Integer age = 29;

		RestTemplate restTemplate = new RestTemplate();

		String resourceUrl= "http://localhost:"+port+"/api/v1/students/agt/{age}";

		// Fetch response as List wrapped in ResponseEntity
		ResponseEntity<List<Student>> findByAgeGreaterThan = restTemplate.exchange(
				resourceUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Student>>(){},age);
		List<Student> students = findByAgeGreaterThan.getBody();

		//Convert list of students to list of id(Integer)
		List<Integer> ids = students.stream()
				.map(o -> o.getId().intValue())
				.collect(Collectors.toList());

		assertThat(students.size()).isEqualTo(3);
		assertThat(ids).hasSameElementsAs(Arrays.asList(103,102,101));

	}

	@Test
	@Sql({"/clear-data.sql","/test-student-data.sql"})
	void findByName_ReturnsTheStudent() {

		final String name ="Alpha";

		RestTemplate restTemplate = new RestTemplate();

		String resourceUrl= "http://localhost:"+port+"/api/v1/students/{name}";

		// Fetch response as List wrapped in ResponseEntity
		ResponseEntity<Student> findByName = restTemplate.exchange(
				resourceUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Student>(){},name);

		Student student = findByName.getBody();


		assertThat(student).isNotNull();
		assertThat(student.getEmail()).isEqualTo("alpha@knf.com");
		assertThat(student.getName()).isEqualTo("Alpha");
		assertThat(student.getId()).isEqualTo(101);
		assertThat(student.getAge()).isEqualTo(50);
	}
	@Test
	@Sql({"/clear-data.sql","/test-student-data.sql"})
	void findByAgeLessThan_ReturnsTheListStudents() {

		final Integer age = 31;

		RestTemplate restTemplate = new RestTemplate();

		String resourceUrl= "http://localhost:"+port+"/api/v1/students/alt/{age}";

		// Fetch response as List wrapped in ResponseEntity
		ResponseEntity<List<Student>> findByAgeLessThan = restTemplate.exchange(
				resourceUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Student>>(){},age);
		List<Student> students = findByAgeLessThan.getBody();

		//Convert list of students to list of id(Integer)
		List<Integer> ids = students.stream()
				.map(o -> o.getId().intValue())
				.collect(Collectors.toList());

		assertThat(students.size()).isEqualTo(2);
		assertThat(ids).hasSameElementsAs(Arrays.asList(104,103));
	}

	@AfterAll
	static void afterAll(){
		postgreSQLContainer.stop();
	}
}

