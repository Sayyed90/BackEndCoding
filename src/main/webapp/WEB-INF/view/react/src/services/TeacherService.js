import axios from 'axios';

const TEACHER_API_BASE_URL = "http://localhost:8080/api/v1/teacher";

class TeacherService {

    getTeachers(pageNumber,pageSizes,sortBy,sortingOrder){
        return axios.get(TEACHER_API_BASE_URL+'/pageNumber='+pageNumber+'&pageSizes='+pageSizes+'&sortBy='+sortBy+'&sortingOrder='+sortingOrder);
    }

    createTeacher(teacher){
        return axios.post(TEACHER_API_BASE_URL, teacher);
    }

    getTeacherById(teacherId){
        return axios.get(TEACHER_API_BASE_URL + '/' + teacherId);
    }

    updateTeacher(teacher, teacherId){
        return axios.put(TEACHER_API_BASE_URL + '/' + teacherId, teacher);
    }

    deleteTeacher(teacherId){
        return axios.delete(TEACHER_API_BASE_URL + '/' + teacherId);
    }
}

export default new TeacherService()