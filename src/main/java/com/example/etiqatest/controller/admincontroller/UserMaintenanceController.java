package com.example.etiqatest.controller.admincontroller;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.UserDTO;
import com.example.etiqatest.service.UserCreationService;
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
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins ="http://localhost:3000")
@Secured({ "ADMIN"})
public class UserMaintenanceController {

    @Autowired
    private UserCreationService adminServiceForUserCreation;

    @GetMapping
    public List<UserDTO> getAllUser(@RequestParam(defaultValue = CommonConstant.PAGE_NUMBER) Integer pageNumber,
                                    @RequestParam(defaultValue = CommonConstant.PAGE_SIZE) Integer pageSizes,
                                    @RequestParam(defaultValue = CommonConstant.NAME) String sortBy,
                                    @RequestParam(defaultValue = CommonConstant.ASC) String sortOrder) throws ExecutionException, InterruptedException {
        CompletableFuture<List<UserDTO>> getListOfUsersAsynchronously=adminServiceForUserCreation.getaLL(pageNumber,pageSizes,sortBy,sortOrder);
        return getListOfUsersAsynchronously.get();
    }

    @GetMapping("/{user_id}")
    public UserDTO findUserById(@PathVariable("user_id") int id) throws ExecutionException, InterruptedException {
        CompletableFuture<UserDTO> getUserByIdAsynchronously=adminServiceForUserCreation.getByID(id);
        return getUserByIdAsynchronously.get();
    }

    @PostMapping
    public UserDTO saveNewUserIntoDB(@RequestBody UserDTO userDTO) throws Exception {
        CompletableFuture<UserDTO> postUserToDB=adminServiceForUserCreation.post(userDTO);
        return postUserToDB.get();
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<String> updateUserFullyByPut(@PathVariable("user_id") int id, @RequestBody UserDTO userDTO) throws Exception {
        CompletableFuture<Boolean> updateUserInTheDB=adminServiceForUserCreation.updateByID(id,userDTO);
        boolean isUpdated=updateUserInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated User...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating User...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<String> updateUserPartiallyByPatch(@PathVariable("user_id") int id, @RequestBody UserDTO userDTO) throws Exception {
        CompletableFuture<Boolean> updateUserInTheDB=adminServiceForUserCreation.updateByID(id,userDTO);
        boolean isUpdated=updateUserInTheDB.get();
        if(isUpdated){
            return new ResponseEntity<String>("Successfully Updated User...", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong when Updating User...", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUserByID(@PathVariable("user_id") int id) throws Exception {
        CompletableFuture<Boolean> isDeleted=adminServiceForUserCreation.deleteByID(id);
        boolean isUserDeleted=isDeleted.get();
        if(!isUserDeleted){
            return new ResponseEntity<String>("User not deleted Successfully", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("User successfully deleted", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
