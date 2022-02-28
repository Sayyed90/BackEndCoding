package com.example.etiqatest.controller.teachercontroller;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.CourseDTO;
import com.example.etiqatest.service.CourseMaintainenceService;
import com.example.etiqatest.sessionfetch.FetchUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/teachermanagement")
@PreAuthorize("hasAuthority('TEACHER')")
@CrossOrigin
@Secured({ "TEACHER"})
public class TeacherController {

    @Autowired
    CourseMaintainenceService courseMaintainenceService;

    @GetMapping
    public List<CourseDTO> getAllCoursesAssignedToTeacher(@RequestParam(defaultValue = CommonConstant.PAGE_NUMBER) Integer pageNumber,
                                                          @RequestParam(defaultValue = CommonConstant.PAGE_SIZE) Integer pageSizes,
                                                          @RequestParam(defaultValue = CommonConstant.NAME) String sortBy,
                                                          @RequestParam(defaultValue = CommonConstant.ASC) String sortOrder) throws ExecutionException, InterruptedException {
        FetchUserDetails currentUser=new FetchUserDetails();
        String currentUsername=currentUser.getNames();
        CompletableFuture<List<CourseDTO>> getListOfUsersAsynchronously=courseMaintainenceService.getAllCourseAssignedToTeacher(pageNumber,pageSizes,sortBy,sortOrder, currentUsername);
        return getListOfUsersAsynchronously.get();
    }
    @GetMapping("/{course_id}")
    public CourseDTO studentGetClassDetailsByID(@PathVariable("course_id") int id) throws ExecutionException, InterruptedException {
        FetchUserDetails currentUser=new FetchUserDetails();
        String currentUsername=currentUser.getNames();
        CompletableFuture<CourseDTO> getUserByIdAsynchronously=courseMaintainenceService.validateTeacherGetById(id,currentUsername);
        return getUserByIdAsynchronously.get();
    }

    @GetMapping("/{date}")
    public List<CourseDTO> studentSearchDateByDate(@PathVariable("date") LocalDateTime date) throws ExecutionException, InterruptedException {
        FetchUserDetails currentUser=new FetchUserDetails();
        String currentUsername=currentUser.getNames();
        CompletableFuture<List<CourseDTO>> getUserByIdAsynchronously=courseMaintainenceService.searchCoursesByDate(date,currentUsername);
        return getUserByIdAsynchronously.get();
    }

    @PatchMapping("/{course_id}")
    public ResponseEntity<String> updateCoursePartiallyByPatch(@PathVariable("course_id") int id, @RequestBody CourseDTO courseDTO) throws Exception {

        CompletableFuture<Boolean> updateClassRoomInTheDB=courseMaintainenceService.updateByID(id,courseDTO);
        boolean isUpdated=updateClassRoomInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated Class Venue/Date...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating Class Venue/Date...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
