import axios from 'axios';

const TEACHER_API_BASE_URL = "http://localhost:8080/api/v1/teachermanagement";

class TeacherModuleService {

    getTeachers(pageNumber,pageSizes,sortBy,sortingOrder){
        return axios.get(TEACHER_API_BASE_URL+'/pageNumber='+pageNumber+'&pageSizes='+pageSizes+'&sortBy='+sortBy+'&sortingOrder='+sortingOrder);
    }

    getTeacherById(courseId){
        return axios.get(TEACHER_API_BASE_URL + '/' + courseId);
    }
    getTeacherById(date){
        return axios.get(TEACHER_API_BASE_URL + '/' + date);
    }

    updateTeacher(courseId, course){
        return axios.put(TEACHER_API_BASE_URL + '/' + courseId, course);
    }
}

export default new TeacherModuleService()