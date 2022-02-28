package com.example.etiqatest.repository;

import com.example.etiqatest.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query( "select o from Course o where students in :ids AND DATE(date) =:date" )
    List<Course> getByDateAndStudentID(@Param("ids") long getUserId, LocalDateTime date);
}
