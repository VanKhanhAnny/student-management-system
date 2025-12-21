package com.codewithanny.studentservice.controller;

import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students") //http://localhost:4000/students
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getStudents() {
        List<StudentResponseDTO> students = studentService.getStudent();
        return ResponseEntity.ok().body(students);
    }
}
