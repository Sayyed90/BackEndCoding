package com.example.etiqatest.service;

import com.example.etiqatest.dto.TeacherDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TeacherMaintainanceService {
    CompletableFuture<Boolean> deleteByID(int id);

    CompletableFuture<Boolean> updateByID(int id, TeacherDTO teacherDTO);

    CompletableFuture<TeacherDTO> post(TeacherDTO teacherDTO);

    CompletableFuture<TeacherDTO> getById(int id);

    CompletableFuture<List<TeacherDTO>> getAll(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder);


}
