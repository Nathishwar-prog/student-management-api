package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnrollmentRequest(

        @NotNull(message = "Student ID is required")
        Long studentId,

        @NotNull(message = "Course ID is required")
        Long courseId,

        @Size(max = 5)
        String grade

) {
}