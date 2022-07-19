package com.statestreet.course.registration.repository;

import com.statestreet.course.registration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
