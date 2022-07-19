package com.statestreet.course.registration.controller;

import com.statestreet.course.registration.service.CourseRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/state-street")
public class CourseRegistrationController {
    @Autowired
    private CourseRegistration courseRegistration;

    // Request No: 2.1
    // opted for a simple path variable, instead of student request object since its only one field required
    @PostMapping("/student/register/{studentName}")
    private ResponseEntity<?> registerStudent(@PathVariable String studentName) {
        try {
            return ResponseEntity.ok(courseRegistration.registerStudent(studentName));
        } catch (Exception ex) {
            log.error("Failure during student registration", ex);
            //TODO: handle generic exception here & alert, provide a proper response to the client
            //For now i assume all failures are Internal Server errors. including validation errors.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    // Request No: 2.2
    @DeleteMapping("/student/{studentId}/delete")
    private ResponseEntity<?> deleteStudent(@PathVariable String studentId) {
        try {
            return ResponseEntity.ok(courseRegistration.deleteStudent(studentId));
        } catch (Exception ex) {
            String errorResponse = "Failure while deleting a student, Please try again later";
            log.error(errorResponse, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Request No: 2.3
    @GetMapping("/student/registered/in/course/{courseName}")
    private ResponseEntity<?> getAllStudentRegistered(@PathVariable String courseName) {
        try {
            return ResponseEntity.ok(courseRegistration.studentsRegisteredInCourse(courseName));
        } catch (Exception ex) {
            String errorResponse = "Failure fetching students registered in a course";
            log.error(errorResponse, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Request No: 2.5
    @GetMapping("/student/unregistered/in/course/{courseName}")
    private ResponseEntity<?> getAllStudentNotRegistered(@PathVariable String courseName) {
        try {
            return ResponseEntity.ok(courseRegistration.studentsUnRegisteredInCourse(courseName));
        } catch (Exception ex) {
            String errorResponse = String.format("Failure fetching unregistered students in a course: %s",
                    ex.getMessage());
            log.error(errorResponse, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
