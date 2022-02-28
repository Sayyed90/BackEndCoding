package com.example.etiqatest.dto;

import com.example.etiqatest.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lasName")
    private String lasName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("major")
    private String major;
    @JsonProperty("courses")
    private Set<Course> courses;

}
