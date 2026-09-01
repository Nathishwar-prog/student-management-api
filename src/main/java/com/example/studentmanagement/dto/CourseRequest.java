package com.example.studentmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(

        @NotBlank(message = "Course code is required")
        @Size(max = 20)
        String code,

        @NotBlank(message = "Course name is required")
        @Size(max = 150)
        String name,

        @NotNull(message = "Credits are required")
        @Min(value = 1, message = "Credits must be at least 1")
        Integer credits

) {
}