package com.codewithanny.studentservice.controller;

import com.codewithanny.studentservice.dto.StudentRequestDTO;
import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);
        return ResponseEntity.ok().body(studentResponseDTO);
    }
}
