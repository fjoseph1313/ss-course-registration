package com.statestreet.course.registration.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class StudentCourse {
    @EmbeddedId
    private StudentCourseId id;

    // Student course is an association entity between Student and Course Many-to-Many relationship
    // This entity facilitates a complex manyToMany association by providing a two oneToMany association between the
    // two entities (Student & Course) especially when 'course score' attribute needs to be added.

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    // For Requirement 2.4: Embeddable class providing complex EmbeddedId for the association class, does the trick
    // to keep track of course score for each student attending a certain course.
    private Double courseScore;
}
