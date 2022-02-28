package com.example.etiqatest.controller.admincontroller;

import com.example.etiqatest.entity.Roles;
import com.example.etiqatest.payload.ResponsePayload;
import com.example.etiqatest.principle.UserPrinciple;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@CrossOrigin
@RestController
//@Secured({ "STUDENT", "ADMIN","TEACHER" })
public class LoginController {

    @RequestMapping("/getUserIdRole")
    //@PostAuthorize("returnObject.username == authentication.principal.nickName")
    public ResponseEntity<?> login(Authentication authentication){
        ResponsePayload payload=new ResponsePayload();
        UserPrinciple prinple= (UserPrinciple) authentication.getPrincipal();
        payload.setId(prinple.getId());
        List<String> rolesInList=new ArrayList<>();
        Set<Roles> getRole=prinple.getRoles();
        getRole.stream().forEach(eachObject->rolesInList.add(eachObject.getRole()));
        payload.setRoles(rolesInList);
        return new ResponseEntity<>(payload, new HttpHeaders(), HttpStatus.OK);
    }
    @RequestMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session2) {
        session2.invalidate();
        return new ResponseEntity<String>("Successfully Logged out", new HttpHeaders(), HttpStatus.OK);
    }

}
