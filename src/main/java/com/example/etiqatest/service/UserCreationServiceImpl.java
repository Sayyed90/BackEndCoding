package com.example.etiqatest.service;


import com.example.etiqatest.ErrorHandler.EntityWithIDNotFoundException;
import com.example.etiqatest.ErrorHandler.FieldErrorException;
import com.example.etiqatest.ErrorHandler.NoDataFoundException;
import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.RolesDTO;
import com.example.etiqatest.dto.UserDTO;
import com.example.etiqatest.entity.Roles;
import com.example.etiqatest.entity.User;
import com.example.etiqatest.repository.RoleRepository;
import com.example.etiqatest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
public class UserCreationServiceImpl implements UserCreationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Async
    @Override
    public CompletableFuture<List<UserDTO>> getaLL(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder) {

        Pageable paging = null;
        if(sortOrder.equalsIgnoreCase(CommonConstant.ASC)){
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).ascending());

        }else{
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).descending());
        }

        if (null==paging) {
            throw new NoDataFoundException();
        }
        Page<User> pagedResult = userRepository.findAll(paging);
        return CompletableFuture.completedFuture(getListOfStudent(pagedResult));

    }
    @Async
    @Override
    public CompletableFuture<UserDTO> getByID(int id) {
        long ids=id;
        User getUser= userRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.USER));

        return  CompletableFuture.completedFuture(getUser(getUser));
    }
    @Async
    @Override
    public CompletableFuture<UserDTO> post(UserDTO userDTO) throws Exception {
        String username=userDTO.getUsername();
        String password=userDTO.getPassword();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if(null==username || username.isEmpty()){
            throw new FieldErrorException(CommonConstant.Username);
        }if(null==password || password.isEmpty()){
            throw new FieldErrorException(CommonConstant.Password);
        }if(null!=userDTO.getRoles()){
            if(userDTO.getRoles().size()<1){
                throw new Exception(CommonConstant.Roles);
            }
        }

        User user=userRepository.saveAndFlush(mapUser(userDTO));
        UserDTO userDTOs = modelMapper.map(user, UserDTO.class);
        return CompletableFuture.completedFuture(userDTOs);
    }

    @Override
    public CompletableFuture<Boolean> updateByID(int id,UserDTO userDTO) throws Exception {
        long ids=id;

        User users= userRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.USER));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if(null!=users && null!=userDTO){
            UserDTO userDTOs = modelMapper.map(users, UserDTO.class);
            userRepository.saveAndFlush(mapUser(userDTOs));
        }
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public CompletableFuture<Boolean> deleteByID(int id) {
        long ids=id;
        userRepository.deleteById(ids);
        userRepository.flush();
        Boolean isDeleted=false;
        Optional<User> userbyId= userRepository.findById(ids);
        if(userbyId.isPresent()){
            isDeleted=true;
        }
        return CompletableFuture.completedFuture(isDeleted);
    }

    private User mapUser(UserDTO userDto) throws Exception {
        User user=new User();
        if(null!=userDto.getUsername() && !userDto.getUsername().isEmpty()){
            user.setUsername(userDto.getUsername());
        }
        if(null!=userDto.getPassword() && !userDto.getPassword().isEmpty()){
            user.setPassword(passwordEncoder().encode(userDto.getPassword()));
        }
        if(null!=userDto.getRoles() && !userDto.getRoles().isEmpty()){
            user.setRoles(getAllRoles(userDto.getRoles()));
        }
        if(null!=userDto.getAddress() && !userDto.getAddress().isEmpty()){
            user.setAddress(userDto.getAddress());
        }
        if(null!=userDto.getEmail_id() && !userDto.getEmail_id().isEmpty()){
            user.setEmail_id(userDto.getEmail_id());
        }
        if(null!=userDto.getRoles() && !userDto.getRoles().isEmpty()){
            Set<Roles> getRolesFromRepo=user.getRoles();
            List<RolesDTO> getRolesFromAdmin=userDto.getRoles();
            getRolesFromAdmin.forEach(eachNewRole->{
                roleRepository.flush();
                Roles role =new Roles();
                role.setRole(eachNewRole.getRole());
                getRolesFromRepo.add(role);
            });
            user.setRoles(getRolesFromRepo);
        }

        return user;
    }

    private Set<Roles> getAllRoles(List<RolesDTO> rolesDto){
        Set<Roles> rolesModel=new HashSet<>();
        Roles roles=new Roles();
        rolesDto.forEach(eachRole->{
            roles.setRole(eachRole.getRole());
            rolesModel.add(roles);
        });
        return rolesModel;
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private List<UserDTO> getListOfStudent(Page<User> pagedResult){
        List<UserDTO> listOfUser =new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<User> listOfPaged=pagedResult.getContent();
        listOfPaged.stream().forEach(each->{
            UserDTO userDTO = modelMapper.map(each, UserDTO.class);
            listOfUser.add(userDTO);
        });
        return listOfUser;
    }

    private UserDTO getUser(User user){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }


}
