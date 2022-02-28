package com.example.etiqatest.controller.admincontroller;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.CourseDTO;
import com.example.etiqatest.service.CourseMaintainenceService;
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
@RequestMapping("/api/v1/course")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
@Secured({ "ADMIN"})
public class CourseMaintainanceController {

    @Autowired
    private CourseMaintainenceService courseMaintainenceService;

    @GetMapping
    public List<CourseDTO> getAllCourse(@RequestParam(defaultValue = CommonConstant.PAGE_NUMBER) Integer pageNumber,
                                        @RequestParam(defaultValue = CommonConstant.PAGE_SIZE) Integer pageSizes,
                                        @RequestParam(defaultValue = CommonConstant.NAME) String sortBy,
                                        @RequestParam(defaultValue = CommonConstant.ASC) String sortOrder) throws ExecutionException, InterruptedException {
        CompletableFuture<List<CourseDTO>> getListOfUsersAsynchronously=courseMaintainenceService.getAll(pageNumber,pageSizes,sortBy,sortOrder);
        return getListOfUsersAsynchronously.get();
    }

    @GetMapping("/{course_id}")
    public CourseDTO findCourseById(@PathVariable("course_id") int id) throws ExecutionException, InterruptedException {
        CompletableFuture<CourseDTO> getUserByIdAsynchronously=courseMaintainenceService.getById(id);
        return getUserByIdAsynchronously.get();
    }

    @PostMapping
    public CourseDTO saveNewCourseIntoDB(@RequestBody CourseDTO courseDTO) throws Exception {
        CompletableFuture<CourseDTO> postUserToDB=courseMaintainenceService.post(courseDTO);
        return postUserToDB.get();
    }

    @PutMapping("/{course_id}")
    public ResponseEntity<String> updateCourseFullyByPut(@PathVariable("course_id") int id, @RequestBody CourseDTO courseDTO) throws Exception {
        CompletableFuture<Boolean> updateClassRoomInTheDB=courseMaintainenceService.updateByID(id,courseDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Room...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Room...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{course_id}")
    public ResponseEntity<String> updateCoursePartiallyByPatch(@PathVariable("course_id") int id, @RequestBody CourseDTO courseDTO) throws Exception {
        CompletableFuture<Boolean> updateClassRoomInTheDB=courseMaintainenceService.updateByID(id,courseDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Room...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Room...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{course_id}")
    public ResponseEntity<String> deleteCourseByID(@PathVariable("course_id") int id) throws Exception {
        CompletableFuture<Boolean> isDeleted=courseMaintainenceService.deleteByID(id);
        boolean isUserDeleted=isDeleted.get();
        if(!isUserDeleted){
            return new ResponseEntity<String>("User not deleted Successfully", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("User successfully deleted", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
