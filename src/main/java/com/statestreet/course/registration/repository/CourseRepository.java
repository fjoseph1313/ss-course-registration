package com.statestreet.course.registration.repository;

import com.statestreet.course.registration.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

    // JPA equivalent to "SELECT * FROM course WHERE course_name LIKE '%courseName%'";
    // Spring JPA Query @Query("SELECT c FROM Course c WHERE c.courseName LIKE ?1%")
    List<Course> findByCourseNameContaining(String courseName);
}
