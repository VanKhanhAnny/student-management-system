package com.codewithanny.studentservice.mapper;

import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.model.Student;

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
}
