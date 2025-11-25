package com.example.demo;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    // Setup MockMvc
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    void shouldSaveStudent() throws Exception {
        Student student = new Student();
        student.setName("Charlie");
        student.setAddress("Algeria");

        // save the student in the DB
        studentRepository.save(student);

        // test if we can access the student using GET /student/getAll
        mockMvc.perform(get("/student/getAll"))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$[0].name").value("Charlie")) // Check if student name is correct
                .andExpect(jsonPath("$[0].address").value("Algeria")); // Check if student address is correct
    }
}
