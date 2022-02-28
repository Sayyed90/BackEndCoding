package com.example.etiqatest.controller.studentcontroller;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.CourseDTO;
import com.example.etiqatest.service.CourseMaintainenceService;
import com.example.etiqatest.sessionfetch.FetchUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/userstudent")
@PreAuthorize("hasAuthority('STUDENT')")
@CrossOrigin
@Secured({ "STUDENT"})
public class StudentController {
    @Autowired
    CourseMaintainenceService courseMaintainenceService;

    @GetMapping
    public List<CourseDTO> getAllCoursesAssignedToStudent(@RequestParam(defaultValue = CommonConstant.PAGE_NUMBER) Integer pageNumber,
                                                          @RequestParam(defaultValue = CommonConstant.PAGE_SIZE) Integer pageSizes,
                                                          @RequestParam(defaultValue = CommonConstant.NAME) String sortBy,
                                                          @RequestParam(defaultValue = CommonConstant.ASC) String sortOrder) throws ExecutionException, InterruptedException {
        FetchUserDetails currentUser=new FetchUserDetails();
        String currentUsername=currentUser.getNames();
        CompletableFuture<List<CourseDTO>> getListOfUsersAsynchronously=courseMaintainenceService.getAllCourseAssignedToUser(pageNumber,pageSizes,sortBy,sortOrder, currentUsername);
        return getListOfUsersAsynchronously.get();
    }

    @GetMapping("/{course_id}")
    public CourseDTO studentGetClassDetailsByID(@PathVariable("course_id") int id) throws ExecutionException, InterruptedException {
        FetchUserDetails currentUser=new FetchUserDetails();
        String currentUsername=currentUser.getNames();
        CompletableFuture<CourseDTO> getUserByIdAsynchronously=courseMaintainenceService.validateStudentGetById(id,currentUsername);
        return getUserByIdAsynchronously.get();
    }

    @GetMapping("/{date}")
    public List<CourseDTO> studentSearchDateByDate(@PathVariable("date") LocalDateTime date) throws ExecutionException, InterruptedException {
        FetchUserDetails currentUser=new FetchUserDetails();
        String currentUsername=currentUser.getNames();
        CompletableFuture<List<CourseDTO>> getUserByIdAsynchronously=courseMaintainenceService.searchCoursesByDate(date,currentUsername);
        return getUserByIdAsynchronously.get();
    }

}
