package com.statestreet.course.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    private String courseId;
    private String courseName;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<StudentCourse> students = new ArrayList<>();
}
