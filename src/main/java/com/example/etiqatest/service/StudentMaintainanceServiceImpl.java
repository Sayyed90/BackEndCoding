package com.example.etiqatest.service;

import com.example.etiqatest.ErrorHandler.EntityWithIDNotFoundException;
import com.example.etiqatest.ErrorHandler.NoDataFoundException;
import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.StudentDTO;
import com.example.etiqatest.entity.Student;
import com.example.etiqatest.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class StudentMaintainanceServiceImpl implements StudentMaintainanceService{

    @Autowired
    StudentRepository studentRepository;

    @Async
    @Override
    public CompletableFuture<List<StudentDTO>> getAll(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder) {

        Pageable paging = null;
        if(sortOrder.equalsIgnoreCase(CommonConstant.ASC)){
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).ascending());

        }else{
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).descending());
        }

        if (null==paging) {
            throw new NoDataFoundException();
        }
        Page<Student> pagedResult = studentRepository.findAll(paging);

        return CompletableFuture.completedFuture(getListOfStudent(pagedResult));

    }

    @Async
    @Override
    public CompletableFuture<StudentDTO> getById(int id) {

        long ids=id;
        Student student= studentRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.STUDENT));

        return  CompletableFuture.completedFuture(getStudents(student));
    }

    @Async
    @Override
    public CompletableFuture<StudentDTO> post(StudentDTO studentDTO) {
        Student student=null;
        StudentDTO studentDTOs=null;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if(studentDTO.getId()==0){
            student= studentRepository.saveAndFlush(mapStudent(studentDTO));
            studentDTOs = modelMapper.map(student, StudentDTO.class);
        }
        return CompletableFuture.completedFuture(studentDTOs);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> updateByID(int id, StudentDTO studentDTO) {
        long ids=id;
        Student student= studentRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.STUDENT));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if(null!=student && null!=studentDTO){
            StudentDTO studentDTOs = modelMapper.map(student, StudentDTO.class);
            studentRepository.saveAndFlush(mapStudent(studentDTOs));
        }
        return CompletableFuture.completedFuture(true);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteByID(int id) {
        long ids=id;
        studentRepository.deleteById(ids);
        studentRepository.flush();
        Boolean isDeleted=false;
        Optional<Student> studentById= studentRepository.findById(ids);
        if(studentById.isPresent()){
            isDeleted=true;
        }
        return CompletableFuture.completedFuture(isDeleted);
    }

    private Student mapStudent(StudentDTO studentDTO) {
        Student student=new Student();
        if(studentDTO.getId()>0){
            student.setId(studentDTO.getId());
        }
        if(null!=studentDTO.getStudentName()  && !studentDTO.getStudentName().isEmpty()){
            student.setStudentName(studentDTO.getStudentName());
        }
        if(null!=studentDTO.getStudentImage()  && !studentDTO.getStudentImage().isEmpty()){
            student.setStudentImage(studentDTO.getStudentImage());
        }
        if(null!=studentDTO.getDateOfBirth()){
            student.setDateOfBirth(studentDTO.getDateOfBirth());
        }
        if(null!=studentDTO.getYearGroup()  && !studentDTO.getYearGroup().isEmpty()){
            student.setYearGroup(studentDTO.getYearGroup());
        }
        if(null!=studentDTO.getCourses()){
            student.setCourses(studentDTO.getCourses());
        }
        return student;
    }

    private List<StudentDTO> getListOfStudent(Page<Student> pagedResult){
        List<StudentDTO> listOfStudent =new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Student> listOfPaged=pagedResult.getContent();
        listOfPaged.stream().forEach(each->{
            StudentDTO studentDTO = modelMapper.map(each, StudentDTO.class);
            listOfStudent.add(studentDTO);
        });
        return listOfStudent;
    }

    private StudentDTO getStudents(Student student){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        return studentDTO;
    }
}
