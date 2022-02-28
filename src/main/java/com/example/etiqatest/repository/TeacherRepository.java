package com.example.etiqatest.repository;

import com.example.etiqatest.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    @Query( "select o from Teacher o where ids=:getUserId AND DATE( courses in date) =:date" )
    Teacher getByDateAndTeacherID(long getUserId, LocalDateTime date);
}
