package com.example.etiqatest.service;

import com.example.etiqatest.dto.UserDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserCreationService {
    CompletableFuture<List<UserDTO>> getaLL(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder);
    CompletableFuture<UserDTO> getByID(int id);
    CompletableFuture<UserDTO> post(UserDTO userDTO) throws Exception;
    CompletableFuture<Boolean> updateByID(int id,UserDTO userDTO) throws Exception;
    CompletableFuture<Boolean> deleteByID(int id);
}
