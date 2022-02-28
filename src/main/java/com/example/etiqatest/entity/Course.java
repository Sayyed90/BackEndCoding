package com.example.etiqatest.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "course")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
public class Course{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Standard")
    private String standard;

    @Column(name = "SubjectName")
    private String subjectName;

    @Column(name = "venue")
    private String venue;

    @Column(name = "isOnlineClass")
    private Boolean isOnlineClass;

    @Column(name = "isFacetoFaceClass")
    private Boolean isF2FClass;

    @Column(name = "dateTime")
    private LocalDateTime date;

    @ManyToMany(mappedBy = "courses")
    @NotNull
    private Set<Student> students;

}
