package com.example.studentmanagement.dto;

public record CourseResponse(

        Long id,
        String code,
        String name,
        Integer credits

) {
}