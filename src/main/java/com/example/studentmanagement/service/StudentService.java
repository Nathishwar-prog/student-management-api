package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.dto.StudentResponse;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {

        return toResponse(findStudent(id));
    }

    public StudentResponse create(StudentRequest request) {

        if (repository.existsByEmail(request.email())) {

            throw new BadRequestException(
                    "Email already exists"
            );
        }

        Student student = new Student(
                request.name(),
                request.email(),
                request.phone(),
                request.department()
        );

        return toResponse(repository.save(student));
    }

    public StudentResponse update(
            Long id,
            StudentRequest request) {

        Student student = findStudent(id);

        repository.findByEmail(request.email())
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {
                        throw new BadRequestException(
                                "Email already exists"
                        );
                    }
                });

        student.setName(request.name());
        student.setEmail(request.email());
        student.setPhone(request.phone());
        student.setDepartment(request.department());

        return toResponse(repository.save(student));
    }

    public void delete(Long id) {

        Student student = findStudent(id);

        repository.delete(student);
    }

    private Student findStudent(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        ));
    }

    private StudentResponse toResponse(Student student) {

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getDepartment()
        );
    }
}