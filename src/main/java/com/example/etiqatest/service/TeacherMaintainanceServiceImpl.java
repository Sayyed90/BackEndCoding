package com.example.etiqatest.service;

import com.example.etiqatest.ErrorHandler.EntityWithIDNotFoundException;
import com.example.etiqatest.ErrorHandler.NoDataFoundException;
import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.dto.TeacherDTO;
import com.example.etiqatest.entity.Teacher;
import com.example.etiqatest.repository.TeacherRepository;
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
class TeacherMaintainanceServiceImpl implements TeacherMaintainanceService{
    @Autowired
    TeacherRepository teacherRepository;

    @Async
    @Override
    public CompletableFuture<List<TeacherDTO>> getAll(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder) {

        Pageable paging = null;
        if(sortOrder.equalsIgnoreCase(CommonConstant.ASC)){
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).ascending());

        }else{
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).descending());
        }

        if (null==paging) {
            throw new NoDataFoundException();
        }
        Page<Teacher> pagedResult = teacherRepository.findAll(paging);

        return CompletableFuture.completedFuture(getListOfTeacher(pagedResult));

    }

    @Async
    @Override
    public CompletableFuture<TeacherDTO> getById(int id) {
        long ids=id;
        Teacher teacher= teacherRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.CLASS_ROOM));

        return  CompletableFuture.completedFuture(getTeachers(teacher));
    }

    @Async
    @Override
    public CompletableFuture<TeacherDTO> post(TeacherDTO teacherDTO) {
        Teacher teacher=null;
        TeacherDTO teacherDTOs=null;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if(teacherDTO.getId()==0){
            teacher= teacherRepository.saveAndFlush(mapTeacher(teacherDTO));
            teacherDTOs = modelMapper.map(teacher, TeacherDTO.class);
        }
        return CompletableFuture.completedFuture(teacherDTOs);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> updateByID(int id, TeacherDTO teacherDTO) {
        long ids=id;
        Teacher teacher= teacherRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.TEACHER));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if(null!=teacher && null!=teacherDTO){
            TeacherDTO teacherDTOs = modelMapper.map(teacher, TeacherDTO.class);
            teacherRepository.saveAndFlush(mapTeacher(teacherDTOs));
        }
        return CompletableFuture.completedFuture(true);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteByID(int id) {
        long ids=id;
        teacherRepository.deleteById(ids);
        teacherRepository.flush();
        Boolean isDeleted=false;
        Optional<Teacher> classRoomById= teacherRepository.findById(ids);
        if(classRoomById.isPresent()){
            isDeleted=true;
        }
        return CompletableFuture.completedFuture(isDeleted);
    }

    private Teacher mapTeacher(TeacherDTO teacherDTO) {
        Teacher teacher=new Teacher();
        if(teacherDTO.getId()>0){
            teacher.setId(teacherDTO.getId());
        }
        if(null!=teacherDTO.getFirstName() && !teacherDTO.getFirstName().isEmpty()){
            teacher.setFirstName(teacherDTO.getFirstName());
        }
        if(null!=teacherDTO.getLasName() && !teacherDTO.getLasName().isEmpty()){
            teacher.setLasName(teacherDTO.getLasName());
        }
        if(null!=teacherDTO.getTitle() && !teacherDTO.getTitle().isEmpty()){
            teacher.setTitle(teacherDTO.getTitle());
        }
        return teacher;
    }

    private List<TeacherDTO> getListOfTeacher(Page<Teacher> pagedResult){
        List<TeacherDTO> listOfTeacher =new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Teacher> listOfPaged=pagedResult.getContent();
        listOfPaged.stream().forEach(each->{
            TeacherDTO teacherDTO = modelMapper.map(each, TeacherDTO.class);
            listOfTeacher.add(teacherDTO);
        });
        return listOfTeacher;
    }

    private TeacherDTO getTeachers(Teacher teacher){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
        return teacherDTO;
    }
}
