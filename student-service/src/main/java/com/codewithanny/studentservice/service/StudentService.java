package com.codewithanny.studentservice.service;

import com.codewithanny.studentservice.dto.PagedStudentResponseDTO;
import com.codewithanny.studentservice.dto.StudentRequestDTO;
import com.codewithanny.studentservice.dto.StudentResponseDTO;
import com.codewithanny.studentservice.exception.EmailAlreadyExistsException;
import com.codewithanny.studentservice.exception.StudentNotFoundException;
import com.codewithanny.studentservice.grpc.BillingServiceGrpcClient;
import com.codewithanny.studentservice.kafka.KafkaProducer;
import com.codewithanny.studentservice.mapper.StudentMapper;
import com.codewithanny.studentservice.model.Student;
import com.codewithanny.studentservice.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public StudentService(StudentRepository studentRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.studentRepository = studentRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Cacheable(
            value = "students",
            key = "#page + '-' + #size + '-' + #sort + '-' + #sortField",
            condition = "#searchValue == ''"
    )
    public PagedStudentResponseDTO getStudents(int page, int size, String sort, String sortField, String searchValue) {

        log.info("[REDIS]: Cache miss - fetching from DB");
        // ... logic to capture metric...

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        // request -> page = 1
        // pageable -> page = 0
        Pageable pageable = PageRequest.of(page - 1, size,
                sort.equals("desc")
                        ? Sort.by(sortField).descending()
                        : Sort.by(sortField).ascending());

        Page<Student> studentPage;

        if (searchValue == null || searchValue.isEmpty()) {
            studentPage = studentRepository.findAll(pageable);
        } else {
            studentPage = studentRepository.findByNameContainingIgnoreCase(searchValue, pageable);
        }

        List<StudentResponseDTO> studentResponseDTOs = studentPage.getContent()
                .stream()
                .map(StudentMapper::toDTO)
                .toList();

        return new PagedStudentResponseDTO(
                studentResponseDTOs,
                studentPage.getNumber() + 1,
                studentPage.getSize(),
                studentPage.getTotalPages(),
                (int)studentPage.getTotalElements()
        );
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        if (studentRepository.existsByEmail(studentRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A student with this email " + "already exists" + studentRequestDTO.getEmail());
        }

        Student newStudent = studentRepository.save(
                StudentMapper.toModel(studentRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newStudent.getId().toString(), newStudent.getName(), newStudent.getEmail());

        kafkaProducer.sendStudentCreatedEvent(newStudent);

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

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }
}
