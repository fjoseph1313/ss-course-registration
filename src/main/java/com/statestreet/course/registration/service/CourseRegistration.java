package com.statestreet.course.registration.service;

import com.statestreet.course.registration.entity.Student;

import java.util.List;

public interface CourseRegistration {
    Student registerStudent(String studentName);

    String deleteStudent(String studentId);

    List<Student> studentsRegisteredInCourse(String courseName);

    List<Student> studentsUnRegisteredInCourse(String courseName);
}
