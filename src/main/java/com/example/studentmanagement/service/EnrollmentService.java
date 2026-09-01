package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.EnrollmentRequest;
import com.example.studentmanagement.dto.EnrollmentResponse;
import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.entity.Enrollment;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.CourseRepository;
import com.example.studentmanagement.repository.EnrollmentRepository;
import com.example.studentmanagement.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getAll() {

        return enrollmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EnrollmentResponse getById(Long id) {

        return toResponse(findEnrollment(id));
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getByStudent(
            Long studentId) {

        ensureStudent(studentId);

        return enrollmentRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getByCourse(
            Long courseId) {

        ensureCourse(courseId);

        return enrollmentRepository
                .findByCourseId(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EnrollmentResponse create(
            EnrollmentRequest request) {

        Student student =
                ensureStudent(request.studentId());

        Course course =
                ensureCourse(request.courseId());

        if (enrollmentRepository
                .existsByStudentIdAndCourseId(
                        request.studentId(),
                        request.courseId())) {

            throw new BadRequestException(
                    "Student is already enrolled in this course"
            );
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(request.grade());

        return toResponse(
                enrollmentRepository.save(enrollment)
        );
    }

    public EnrollmentResponse update(
            Long id,
            EnrollmentRequest request) {

        Enrollment enrollment =
                findEnrollment(id);

        Student student =
                ensureStudent(request.studentId());

        Course course =
                ensureCourse(request.courseId());

        boolean changedPair =
                !enrollment.getStudent()
                        .getId()
                        .equals(request.studentId())
                        ||
                        !enrollment.getCourse()
                                .getId()
                                .equals(request.courseId());

        if (changedPair
                && enrollmentRepository
                .existsByStudentIdAndCourseId(
                        request.studentId(),
                        request.courseId())) {

            throw new BadRequestException(
                    "Student is already enrolled in this course"
            );
        }

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(request.grade());

        return toResponse(
                enrollmentRepository.save(enrollment)
        );
    }

    public void delete(Long id) {

        enrollmentRepository.delete(
                findEnrollment(id)
        );
    }

    private Enrollment findEnrollment(Long id) {

        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found: " + id
                        ));
    }

    private Student ensureStudent(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id
                        ));
    }

    private Course ensureCourse(Long id) {

        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found: " + id
                        ));
    }

    private EnrollmentResponse toResponse(
            Enrollment enrollment) {

        return new EnrollmentResponse(
                enrollment.getId(),

                enrollment.getStudent().getId(),
                enrollment.getStudent().getName(),

                enrollment.getCourse().getId(),
                enrollment.getCourse().getCode(),
                enrollment.getCourse().getName(),

                enrollment.getGrade()
        );
    }
}