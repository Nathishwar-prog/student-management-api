package com.example.studentmanagement.dto;

public record EnrollmentResponse(

        Long id,

        Long studentId,
        String studentName,

        Long courseId,
        String courseCode,
        String courseName,

        String grade

) {
}