package com.example.etiqatest.service;

import com.example.etiqatest.dto.StudentDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StudentMaintainanceService {
    CompletableFuture<List<StudentDTO>> getAll(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder);

    CompletableFuture<StudentDTO> getById(int id);

    CompletableFuture<StudentDTO> post(StudentDTO studentDTO);

    CompletableFuture<Boolean> updateByID(int id, StudentDTO studentDTO);

    CompletableFuture<Boolean> deleteByID(int id);
}
