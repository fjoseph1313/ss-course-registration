package com.statestreet.course.registration.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class StudentCourseId implements Serializable {
    private String studentId;
    private String courseId;
}
