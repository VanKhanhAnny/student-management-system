package com.codewithanny.studentservice.mapper;

import com.codewithanny.studentservice.dto.StudentRequestDTO;
import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.model.Student;

import java.time.LocalDate;

public class StudentMapper {
    public static StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO studentDTO = new StudentResponseDTO();
        studentDTO.setId(student.getId().toString());
        studentDTO.setName(student.getName());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setDateOfBirth(student.getDateOfBirth().toString());

        return studentDTO;
    }
    public static Student toModel(StudentRequestDTO studentRequestDTO) {
        Student student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());
        student.setDateOfBirth(LocalDate.parse(studentRequestDTO.getDateOfBirth()));
        student.setRegisteredDate(LocalDate.parse(studentRequestDTO.getRegisteredDate()));
        return student;
    }
}
