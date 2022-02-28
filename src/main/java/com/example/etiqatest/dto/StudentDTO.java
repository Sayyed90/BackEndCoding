package com.example.etiqatest.dto;

import com.example.etiqatest.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("studentName")
    private String studentName;
    @JsonProperty("yearGroup")
    private String yearGroup;
    @JsonProperty("dateOfBirth")
    private LocalDateTime dateOfBirth;
    @JsonProperty("studentImage")
    private String studentImage;
    @JsonProperty("courses")
    private Set<Course> courses;

}
