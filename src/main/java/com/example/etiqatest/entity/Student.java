package com.example.etiqatest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "student")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "StudentName")
    private String studentName;

    @Column(name = "YearGroup")
    private String yearGroup;

    @Column(name = "DateOfBirth")
    private LocalDateTime dateOfBirth;

    @Lob
    @Column(name="Image")
    private String studentImage;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name="student_course",
            joinColumns = {@JoinColumn(name="STUDENT_ID")},
            inverseJoinColumns = {@JoinColumn(name="COURSE_ID")}
    )
    private Set<Course> courses;

}
