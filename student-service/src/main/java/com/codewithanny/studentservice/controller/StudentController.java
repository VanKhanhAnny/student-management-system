package com.codewithanny.studentservice.controller;

import com.codewithanny.studentservice.dto.CreateStudentValidationGroup;
import com.codewithanny.studentservice.dto.StudentRequestDTO;
import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students") //http://localhost:4000/students
@Tag(name="Student", description = "API for managing Students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Operation(summary = "Get Students")
    public ResponseEntity<List<StudentResponseDTO>> getStudents() {
        List<StudentResponseDTO> students = studentService.getStudent();
        return ResponseEntity.ok().body(students);
    }

    @PostMapping
    @Operation(summary = "Create a new Student")
    public ResponseEntity<StudentResponseDTO> createStudent(@Validated({Default.class, CreateStudentValidationGroup.class}) @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);
        return ResponseEntity.ok().body(studentResponseDTO);
    }

    //localhost:4000/students/123445-1223455-12345
    @PutMapping("/{id}")
    @Operation(summary = "Update a new Student")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable UUID id, @Validated({Default.class}) @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.updateStudent(id, studentRequestDTO);

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Student")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
