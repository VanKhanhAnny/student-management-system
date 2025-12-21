package com.codewithanny.studentservice.service;

import com.codewithanny.studentservice.dto.StudentRequestDTO;
import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.exception.EmailAlreadyExistsException;
import com.codewithanny.studentservice.exception.StudentNotFoundException;
import com.codewithanny.studentservice.mapper.StudentMapper;
import com.codewithanny.studentservice.model.Student;
import com.codewithanny.studentservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A student with this email " + "already exists" + studentRequestDTO.getEmail());
        }

        Student newStudent = studentRepository.save(
                StudentMapper.toModel(studentRequestDTO));

        return StudentMapper.toDTO(newStudent);
    }
    public StudentResponseDTO updateStudent(UUID id, StudentRequestDTO studentRequestDTO) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException("Student not found with ID: " + id));

        if (studentRepository.existsByEmailAndIdNot(studentRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A student with this email " + "already exists" + studentRequestDTO.getEmail());
        }

        student.setName(studentRequestDTO.getName());
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());
        student.setDateOfBirth(LocalDate.parse(studentRequestDTO.getDateOfBirth()));

        Student updatedStudent = studentRepository.save(student);
        return StudentMapper.toDTO(updatedStudent);
    }
}
