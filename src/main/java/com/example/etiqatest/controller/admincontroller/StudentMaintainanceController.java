package com.example.etiqatest.controller.admincontroller;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.StudentDTO;
import com.example.etiqatest.service.StudentMaintainanceService;
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
@RequestMapping("/api/v1/student")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
@Secured({ "ADMIN"})
public class StudentMaintainanceController {
    @Autowired
    private StudentMaintainanceService studentMaintainenceService;

    @GetMapping
    public List<StudentDTO> getAllStudent(@RequestParam(defaultValue = CommonConstant.PAGE_NUMBER) Integer pageNumber,
                                          @RequestParam(defaultValue = CommonConstant.PAGE_SIZE) Integer pageSizes,
                                          @RequestParam(defaultValue = CommonConstant.NAME) String sortBy,
                                          @RequestParam(defaultValue = CommonConstant.ASC) String sortOrder) throws ExecutionException, InterruptedException {
        CompletableFuture<List<StudentDTO>> getListOfUsersAsynchronously=studentMaintainenceService.getAll(pageNumber,pageSizes,sortBy,sortOrder);
        return getListOfUsersAsynchronously.get();
    }

    @GetMapping("/{student_id}")
    public StudentDTO findStudentById(@PathVariable("student_id") int id) throws ExecutionException, InterruptedException {
        CompletableFuture<StudentDTO> getUserByIdAsynchronously=studentMaintainenceService.getById(id);
        return getUserByIdAsynchronously.get();
    }

    @PostMapping
    public StudentDTO saveNewStudentIntoDB(@RequestBody StudentDTO studentDTO) throws Exception {
        CompletableFuture<StudentDTO> postUserToDB=studentMaintainenceService.post(studentDTO);
        return postUserToDB.get();
    }

    @PutMapping("/{student_id}")
    public ResponseEntity<String> updateStudentFullyByPut(@PathVariable("student_id") int id, @RequestBody StudentDTO studentDTO) throws Exception {
        CompletableFuture<Boolean> updateClassRoomInTheDB=studentMaintainenceService.updateByID(id,studentDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Room...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Room...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{student_id}")
    public ResponseEntity<String> updateStudentPartiallyByPatch(@PathVariable("student_id") int id, @RequestBody StudentDTO studentDTO) throws Exception {
        CompletableFuture<Boolean> updateClassRoomInTheDB=studentMaintainenceService.updateByID(id,studentDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Room...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Room...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{student_id}")
    public ResponseEntity<String> deleteStudentByID(@PathVariable("student_id") int id) throws Exception {
        CompletableFuture<Boolean> isDeleted=studentMaintainenceService.deleteByID(id);
        boolean isUserDeleted=isDeleted.get();
        if(!isUserDeleted){
            return new ResponseEntity<String>("User not deleted Successfully", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("User successfully deleted", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
