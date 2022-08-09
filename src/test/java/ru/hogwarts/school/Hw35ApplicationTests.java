package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Hw35ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void testFindStudent() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/19", Student.class)).isEqualTo(testStudent());
    }

    @Test
    void testFindStudentFaculty() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/19/faculty", Faculty.class)).isEqualTo(testFaculty());

    }

    @Test
    void testAddStudent() throws Exception {
        assertThat(restTemplate.postForObject("http://localhost:" + port + "/student", testStudent(), Student.class)).isEqualTo(testStudent());

    }

    @Test
    void testEditStudent() throws Exception {
        restTemplate.put("http://localhost:" + port + "/student", testStudent2(), Student.class);
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/19", Student.class)).isEqualTo(testStudent2());
    }

    @Test
    void testFindNotExistedStudent() throws Exception {
        assertThat(restTemplate.getForEntity("http://localhost:" + port + "/student/34", Student.class)
                .getStatusCode()).isEqualTo(NOT_FOUND);

    }

    private Faculty testFaculty() {
        Faculty testFaculty = new Faculty();
        testFaculty.setId(12L);
        testFaculty.setName("Petyr3");
        testFaculty.setColor("black");
        return testFaculty;
    }

    private Student testStudent() {
        Student testStudent = new Student();
        testStudent.setId(19L);
        testStudent.setAge(25);
        testStudent.setName("Trevor");
        testStudent.setFaculty(testFaculty());
        return testStudent;
    }

    private Student testStudent2() {
        Student testStudent = new Student();
        testStudent.setId(19L);
        testStudent.setAge(27);
        testStudent.setName("Trevor");
        testStudent.setFaculty(testFaculty());
        return testStudent;
    }
}
