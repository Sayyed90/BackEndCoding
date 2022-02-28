package com.example.etiqatest.service;

import com.example.etiqatest.ErrorHandler.CommonException;
import com.example.etiqatest.ErrorHandler.CourseExceptionError;
import com.example.etiqatest.ErrorHandler.EntityWithIDNotFoundException;
import com.example.etiqatest.ErrorHandler.NoDataFoundException;
import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.commonconstant.ErrorConstant;
import com.example.etiqatest.dto.CourseDTO;
import com.example.etiqatest.entity.*;
import com.example.etiqatest.repository.CourseRepository;
import com.example.etiqatest.repository.StudentRepository;
import com.example.etiqatest.repository.TeacherRepository;
import com.example.etiqatest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class CourseMaintainenceServiceImpl implements  CourseMaintainenceService{

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Async
    @Override
    public CompletableFuture<List<CourseDTO>> getAll(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder) {

        Pageable paging = null;
        if(sortOrder.equalsIgnoreCase(CommonConstant.ASC)){
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).ascending());
        }else{
            paging=  PageRequest.of(pageNumber, pageSizes, Sort.by(sortBy).descending());
        }

        if (null==paging) {
            throw new NoDataFoundException();
        }

        Page<Course> pagedResult = courseRepository.findAll(paging);
        return CompletableFuture.completedFuture(getListOfCourse(pagedResult));
    }

    @Async
    @Override
    public CompletableFuture<CourseDTO> getById(int id) {

        long ids=id;
        Course course= courseRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.COURSE));

        return  CompletableFuture.completedFuture(getCourses(course));
    }

    @Async
    @Override
    public CompletableFuture<CourseDTO> post(CourseDTO courseDTO) {

        Course course=null;
        CourseDTO courseDTOs=null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if(courseDTO.getId()==0){
            course= courseRepository.saveAndFlush(mapCourse(courseDTO));
            courseDTOs = modelMapper.map(course, CourseDTO.class);
        }
        return CompletableFuture.completedFuture(courseDTOs);

    }

    @Async
    @Override
    public CompletableFuture<Boolean> updateByID(int id, CourseDTO courseDTO) {
        long ids=id;
        Course course= courseRepository.findById(ids)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.COURSE));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if(null!=course && null!=courseDTO){
            CourseDTO courseDTOs = modelMapper.map(course, CourseDTO.class);
            courseRepository.saveAndFlush(mapCourse(courseDTOs));
        }
        return CompletableFuture.completedFuture(true);

    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteByID(int id) {
        long ids=id;
        courseRepository.deleteById(ids);
        courseRepository.flush();
        Boolean isDeleted=false;
        Optional<Course> courseById= courseRepository.findById(ids);
        if(courseById.isPresent()){
            isDeleted=true;
        }
        return CompletableFuture.completedFuture(isDeleted);
    }

    @Async
    @Override
    public CompletableFuture<CourseDTO> validateTeacherGetById(int id, String currentUsername) {
        long ids=id;

        long getUserId= getUserId(currentUsername);

        Teacher teacher= teacherRepository.findById(getUserId)
                .orElseThrow(() -> new CommonException(ErrorConstant.STUDENT_NOT_EXIST));

        Set<Course> getAllCourses=teacher.getCourses();
        if(null==getAllCourses ){
            throw new CommonException(ErrorConstant.COURSE_NOT_EXIST);
        }

        boolean isValidCourse=getAllCourses.stream().anyMatch(s -> s.getId().equals(getUserId));
            if(!isValidCourse){
                throw new CommonException(ErrorConstant.COURSE_NOT_ASSIGNED);
            }

        return  CompletableFuture.completedFuture(getCourses((Course) getAllCourses.stream().filter(each-> each.getId().equals(getUserId))));
    }

    @Async
    @Override
    public CompletableFuture<CourseDTO> validateStudentGetById(int id, String currentUsername) {

        long ids=id;
        long getUserId= getUserId(currentUsername);

        Student student= studentRepository.findById(getUserId)
                .orElseThrow(() -> new EntityWithIDNotFoundException(ids, CommonConstant.STUDENT));

        Set<Course> courses=student.getCourses();
        boolean isValidCourse=courses.stream().anyMatch(s -> s.getId().equals(ids));
        if(!isValidCourse){
            throw new CourseExceptionError();
        }
        Course course= (Course) courses.stream().filter(s ->s.getId().equals(ids));
        if(null==course){
            throw new CommonException(ErrorConstant.COURSE_NOT_EXIST);
        }

        return  CompletableFuture.completedFuture(getCourses(course));
    }

    @Async
    @Override
    public CompletableFuture<List<CourseDTO>> getAllCourseAssignedToUser(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder, String currentUsername) {

        long getUserId= getUserId(currentUsername);

        Student student= studentRepository.findById(getUserId)
                .orElseThrow(() -> new CommonException(ErrorConstant.STUDENT_NOT_EXIST));
        Set<Course> courses=student.getCourses();

        return  CompletableFuture.completedFuture(getlistOfCoursesDTOS(getSortedStreamOfFooStream(sortOrder,courses,pageNumber,pageSizes)));
    }

    @Async
    @Override
    public CompletableFuture<List<CourseDTO>> getAllCourseAssignedToTeacher(Integer pageNumber, Integer pageSizes, String sortBy, String sortOrder, String currentUsername) {

        long getUserId= getUserId(currentUsername);

        Teacher teacher= teacherRepository.findById(getUserId)
                .orElseThrow(() -> new CommonException(ErrorConstant.STUDENT_NOT_EXIST));
        Set<Course> getAllCourses=teacher.getCourses();

        if(null==getAllCourses ){
            throw new CommonException(ErrorConstant.COURSE_NOT_EXIST);
        }

        return  CompletableFuture.completedFuture(getlistOfCoursesDTOS(getSortedStreamOfFooStream(sortOrder,getAllCourses,pageNumber,pageSizes)));
    }

    @Async
    @Override
    public CompletableFuture<List<CourseDTO>> searchCoursesByDate(LocalDateTime date, String currentUsername) {

       Map <Long,Set<Roles>> getRoles=getRoles(currentUsername);
       Set<Roles> getAllRoles= (Set<Roles>) getRoles.values().toArray()[0];

       long getUserId= (long) getRoles.keySet().toArray()[0];

       boolean isExist= getAllRoles.stream().anyMatch(s -> (s.getRole().equalsIgnoreCase(CommonConstant.TEACHER) && s.getId()==getUserId)
               || s.getRole().equalsIgnoreCase(CommonConstant.STUDENT) && s.getId()==getUserId);

       if(!isExist){
           throw new CommonException(ErrorConstant.NOT_EXIST);
       }
        Roles getObject= (Roles) getAllRoles.stream().filter(s ->s.getId()==getUserId);


       if(getObject.getRole().equalsIgnoreCase(CommonConstant.TEACHER)){
           Teacher teacher=teacherRepository.getByDateAndTeacherID(getUserId,date);
           return CompletableFuture.completedFuture(getListOfCourseFromList((List<Course>) teacher.getCourses()));
       }
        List<Course> course=courseRepository.getByDateAndStudentID(getUserId,date);
        return CompletableFuture.completedFuture(getListOfCourseFromList(course));
    }

    private Course mapCourse(CourseDTO courseDTO) {
        Course course=new Course();
        if(courseDTO.getId()>0){
            course.setId(courseDTO.getId());
        }
        if(courseDTO.getIsF2FClass()){
            course.setIsF2FClass(courseDTO.getIsF2FClass());
        }
        if(courseDTO.getIsOnlineClass()){
            course.setIsOnlineClass(courseDTO.getIsOnlineClass());
        }
        if(null!=courseDTO.getStudents()){
            course.setStudents(courseDTO.getStudents());
        }
        if(null!=courseDTO.getSubjectName() && !courseDTO.getSubjectName().isEmpty()){
            course.setSubjectName(courseDTO.getSubjectName());
        }
        if(null!=courseDTO.getVenue() && !courseDTO.getVenue().isEmpty()){
            course.setVenue(courseDTO.getVenue());
        }
        if(null!=courseDTO.getStandard() && !courseDTO.getStandard().isEmpty()){
            course.setStandard(courseDTO.getStandard());
        }
        if(null!=courseDTO.getDate()){
            course.setDate(courseDTO.getDate());
        }
        return course;
    }

    private long getUserId(String username){
        Optional<User> user=userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new CommonException(ErrorConstant.USER_NOT_FOUND);
        }
        return user.get().getId();
    }

    private Map<Long,Set<Roles>> getRoles(String username){
        Optional<User> user=userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new CommonException(ErrorConstant.USER_NOT_FOUND);
        }
        Map<Long,Set<Roles>> mapObj=new HashMap<>();
        mapObj.put(user.get().getId(), user.get().getRoles());
        return mapObj;
    }

    private List<CourseDTO> getlistOfCoursesDTOS(Stream<Course> sortedStreamOfFoo){
        List<CourseDTO> listOfCoursesDTOS=new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        sortedStreamOfFoo.forEach(each->{
            CourseDTO  courseDTO = modelMapper.map(each, CourseDTO.class);
            listOfCoursesDTOS.add(courseDTO);
        });
        return listOfCoursesDTOS;
    }

    private CourseDTO getCourses(Course course){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        return courseDTO;
    }

    private List<CourseDTO> getListOfCourse(Page<Course> pagedResult){
       List<CourseDTO> listOfCourse =new ArrayList<>();
       ModelMapper modelMapper = new ModelMapper();
       modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Course> listOfPaged=pagedResult.getContent();
        listOfPaged.stream().forEach(each->{
            CourseDTO curseDTO = modelMapper.map(each, CourseDTO.class);
            listOfCourse.add(curseDTO);
           });
           return listOfCourse;
   }
    private List<CourseDTO> getListOfCourseFromList(List<Course> course){
        List<CourseDTO> listOfCourse =new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        course.stream().forEach(each->{
            CourseDTO curseDTO = modelMapper.map(each, CourseDTO.class);
            listOfCourse.add(curseDTO);
        });
        return listOfCourse;
    }
   private Stream<Course> getSortedStreamOfFooStream(String sortOrder,Set<Course> getAllCourses,Integer pageNumber,Integer pageSizes){
       Stream<Course> sortedStreamOfFoo =null;
       if(sortOrder.equalsIgnoreCase(CommonConstant.ASC)){
           sortedStreamOfFoo = getAllCourses.stream()
                   .sorted(Comparator.comparing(Course::getStandard))
                   .collect(Collectors.toList()).stream().limit(pageNumber*pageSizes);
       }else{
           sortedStreamOfFoo = getAllCourses.stream()
                   .sorted(Comparator.comparing(Course::getStandard).reversed())
                   .collect(Collectors.toList()).stream().limit(pageNumber*pageSizes);
       }

       if(null==sortedStreamOfFoo){
           throw new CommonException(ErrorConstant.CANNOT_BE_SORTED);
       }
       return sortedStreamOfFoo;
   }
}
