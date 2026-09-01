package com.example.studentmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StudentRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100)
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Pattern(
                regexp = "^$|[0-9]{10}",
                message = "Phone must contain 10 digits"
        )
        String phone,

        @NotBlank(message = "Department is required")
        @Size(max = 100)
        String department
) {
}