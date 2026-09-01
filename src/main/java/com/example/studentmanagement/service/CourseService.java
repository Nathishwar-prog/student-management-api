package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.CourseRequest;
import com.example.studentmanagement.dto.CourseResponse;
import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.CourseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {

        return toResponse(findCourse(id));
    }

    public CourseResponse create(CourseRequest request) {

        if (repository.existsByCode(request.code())) {

            throw new BadRequestException(
                    "Course code already exists"
            );
        }

        Course course = new Course(
                request.code(),
                request.name(),
                request.credits()
        );

        return toResponse(repository.save(course));
    }

    public CourseResponse update(
            Long id,
            CourseRequest request) {

        Course course = findCourse(id);

        if (!course.getCode().equals(request.code())
                && repository.existsByCode(request.code())) {

            throw new BadRequestException(
                    "Course code already exists"
            );
        }

        course.setCode(request.code());
        course.setName(request.name());
        course.setCredits(request.credits());

        return toResponse(repository.save(course));
    }

    public void delete(Long id) {

        repository.delete(findCourse(id));
    }

    private Course findCourse(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found: " + id
                        ));
    }

    private CourseResponse toResponse(Course course) {

        return new CourseResponse(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getCredits()
        );
    }
}