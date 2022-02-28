package com.example.etiqatest.dto;

import com.example.etiqatest.entity.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("standard")
    private String standard;
    @JsonProperty("subjectName")
    private String subjectName;
    @JsonProperty("venue")
    private String venue;
    @JsonProperty("isOnlineClass")
    private Boolean isOnlineClass;
    @JsonProperty("isF2FClass")
    private Boolean isF2FClass;
    @JsonProperty("students")
    private Set<Student> students;
    @JsonProperty("date")
    private LocalDateTime date;

}
