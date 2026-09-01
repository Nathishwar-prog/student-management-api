package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.EnrollmentRequest;
import com.example.studentmanagement.dto.EnrollmentResponse;
import com.example.studentmanagement.service.EnrollmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponse>>
    getByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(
                service.getByStudent(studentId)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentResponse>>
    getByCourse(@PathVariable Long courseId) {

        return ResponseEntity.ok(
                service.getByCourse(courseId)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentResponse> create(
            @Valid @RequestBody EnrollmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentRequest request) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}