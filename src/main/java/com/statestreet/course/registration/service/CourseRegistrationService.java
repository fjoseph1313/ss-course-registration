package com.statestreet.course.registration.service;

import com.statestreet.course.registration.entity.Course;
import com.statestreet.course.registration.entity.Student;
import com.statestreet.course.registration.entity.StudentCourse;
import com.statestreet.course.registration.repository.CourseRepository;
import com.statestreet.course.registration.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseRegistrationService implements CourseRegistration {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    //HAPPY PATH : Assuming student does not exist. so NO collision while inserting a new entity.
    //WORST CASE -> Duplicate of name in the DB since the ID is studentID.
    @Override
    public Student registerStudent(String studentName) {
        log.info("Registering a student with name: {}", studentName);
        Student newStudent = new Student();
        //assuming studentId would be properly generated using
        // a proper school business rule. for now, just a random UUID string is enough
        newStudent.setStudentId(UUID.randomUUID().toString());
        newStudent.setStudentName(studentName.toUpperCase(Locale.ROOT));
        Student savedStudent = studentRepository.save(newStudent);
        log.info("Successfully registered a new student with name: {}", studentName);
        return savedStudent;
    }

    // Assumption, HAPPY PATH is when the delete operation happens and JPA commits transaction, otherwise
    // exception will be thrown for SAD PATH, for now i assume internal sever error
    // (worst case Student still exists in the DB) - not a bad thing, client just repeats a process.
    @Override
    public String deleteStudent(String studentId) {
        log.info("Deleting a Student with ID: {}", studentId);
        studentRepository.deleteById(studentId);
        String response = String.format("Successfully deleted a student with ID: %s", studentId);
        log.info(response);
        return response;
    }

    //1. find is course with specified courseName exist.
    //2. Make sure there is one and only one course with this name.
    //3. Get all students associated with this course from the association table.
    //4. Sort student by their name. In this case - Default sort (Ascending order)
    // Worst case scenario/running time complexity is O(n*m) where n = total students, m = total courses.
    //TODO: improvement - add pagination and return a paginated list of students. - would require a different type of
    // sorting mechanism
    @Override
    public List<Student> studentsRegisteredInCourse(String courseName) {
        log.info("Aggregating all students registered in course: {}", courseName);
        List<Course> existingCourses = courseRepository.findByCourseNameContaining(courseName);
        if (CollectionUtils.isEmpty(existingCourses) || existingCourses.size() > 1) {
            log.error("Ambiguous Course name or course does not exist yet: {}", existingCourses.size());
            throw new RuntimeException("Ambiguous Course name or course does not exist yet");
        } else {
            //found exactly one course. now get the list of students registered in this course.
            Comparator<Student> compareByName = Comparator.comparing( Student::getStudentName );
            Course existingCourse = existingCourses.get(0);
            List<Student> registeredStudents = new ArrayList<>();
            existingCourse.getStudents().stream().forEach(studentCourse -> {
                registeredStudents.add(studentCourse.getStudent());
            });
            log.info("Successfully fetched {} students registered in course: {}", registeredStudents.size(), courseName);
            return registeredStudents.stream().sorted(compareByName).collect(Collectors.toList());
        }
    }


    // HAPPY PATH: Assuming the course exist in the DB, and some students exist in the db with some students already
    // registered for this specified course.
    //1. Find all students available.
    //2. filter out all student not having 'courseName' as part of their courses registered.
    //TODO: improvement - add pagination and return a paginated list of students.
    @Override
    public List<Student> studentsUnRegisteredInCourse(String courseName) {
        log.info("Aggregating all students not registered in course: {}", courseName);
        List<Student> students = studentRepository.findAll();
        List<Student> studentsNotRegister = new ArrayList<>();
        students.forEach(student -> {
            List<StudentCourse> registeredCourses = student.getCourses().stream()
                    .filter(studentCourse -> studentCourse.getCourse().getCourseName().equalsIgnoreCase(courseName))
                    .collect(Collectors.toList()); //By default should be ZERO records.
            if (CollectionUtils.isEmpty(registeredCourses)) {
                //Collect this student, not registered in this course
                studentsNotRegister.add(student);
            }
        });
        log.info("Finished aggregating all students not registered in course: {}", courseName);
        return studentsNotRegister;
    }


}
