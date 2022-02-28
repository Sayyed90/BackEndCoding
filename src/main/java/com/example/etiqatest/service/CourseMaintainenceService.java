package com.example.etiqatest.service;

import com.example.etiqatest.dto.CourseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CourseMaintainenceService {
    CompletableFuture<List<CourseDTO>> getAll(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder);

    CompletableFuture<CourseDTO> getById(int id);

    CompletableFuture<CourseDTO> validateStudentGetById(int id, String currentUsername);

    CompletableFuture<CourseDTO> post(CourseDTO courseDTO);

    CompletableFuture<Boolean> updateByID(int id, CourseDTO courseDTO);

    CompletableFuture<Boolean> deleteByID(int id);

    CompletableFuture<List<CourseDTO>> getAllCourseAssignedToUser(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder, String currentUsername);

    CompletableFuture<List<CourseDTO>> getAllCourseAssignedToTeacher(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder, String currentUsername);

    CompletableFuture<CourseDTO> validateTeacherGetById(int id, String currentUsername);

    CompletableFuture<List<CourseDTO>> searchCoursesByDate(LocalDateTime date, String currentUsername);
}
