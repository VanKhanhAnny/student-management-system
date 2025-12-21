package com.codewithanny.studentservice.service;

import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.mapper.StudentMapper;
import com.codewithanny.studentservice.model.Student;
import com.codewithanny.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponseDTO> getStudent() {
        List<Student> students = studentRepository.findAll();

        return students.stream().map(StudentMapper::toDTO).toList();
    }
}
