package com.example.etiqatest.controller.admincontroller;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.TeacherDTO;
import com.example.etiqatest.service.TeacherMaintainanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/teacher")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
@Secured({ "ADMIN"})
public class TeacherMaintainanceController {

    @Autowired
    private TeacherMaintainanceService teacherMaintainenceService;

    @GetMapping
    public List<TeacherDTO> getAllTeachers(@RequestParam(defaultValue = CommonConstant.PAGE_NUMBER) Integer pageNumber,
                                           @RequestParam(defaultValue = CommonConstant.PAGE_SIZE) Integer pageSizes,
                                           @RequestParam(defaultValue = CommonConstant.NAME) String sortBy,
                                           @RequestParam(defaultValue = CommonConstant.ASC) String sortOrder) throws ExecutionException, InterruptedException {
        CompletableFuture<List<TeacherDTO>> getListOfUsersAsynchronously=teacherMaintainenceService.getAll(pageNumber,pageSizes,sortBy,sortOrder);
        return getListOfUsersAsynchronously.get();
    }

    @GetMapping("/{teacher_id}")
    public TeacherDTO findTeacherById(@PathVariable("teacher_id") int id) throws ExecutionException, InterruptedException {
        CompletableFuture<TeacherDTO> getUserByIdAsynchronously=teacherMaintainenceService.getById(id);
        return getUserByIdAsynchronously.get();
    }

    @PostMapping
    public TeacherDTO saveNewTeacherIntoDB(@RequestBody TeacherDTO teacherDTO) throws Exception {
        CompletableFuture<TeacherDTO> postUserToDB=teacherMaintainenceService.post(teacherDTO);
        return postUserToDB.get();
    }

    @PutMapping("/{teacher_id}")
    public ResponseEntity<String> updateTeacherFullyByPut(@PathVariable("teacher_id") int id, @RequestBody TeacherDTO teacherDTO) throws Exception {
        CompletableFuture<Boolean> updateClassRoomInTheDB=teacherMaintainenceService.updateByID(id,teacherDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Room...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Room...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{teacher_id}")
    public ResponseEntity<String> updateTeacherPartiallyByPatch(@PathVariable("teacher_id") int id, @RequestBody TeacherDTO teacherDTO) throws Exception {
        CompletableFuture<Boolean> updateClassRoomInTheDB=teacherMaintainenceService.updateByID(id,teacherDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Room...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Room...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{teacher_id}")
    public ResponseEntity<String> deleteTeacherByID(@PathVariable("teacher_id") int id) throws Exception {
        CompletableFuture<Boolean> isDeleted=teacherMaintainenceService.deleteByID(id);
        boolean isUserDeleted=isDeleted.get();
        if(!isUserDeleted){
            return new ResponseEntity<String>("User not deleted Successfully", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("User successfully deleted", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
