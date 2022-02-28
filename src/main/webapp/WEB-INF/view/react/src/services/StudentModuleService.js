
const STUDENT_API_BASE_URL = "http://localhost:8080/api/v1/userstudent";

class StudentModuleService {

    getAssignedCoursesToStudents(pageNumber,pageSizes,sortBy,sortingOrder){
        return axios.get(STUDENT_API_BASE_URL+'/pageNumber='+pageNumber+'&pageSizes='+pageSizes+'&sortBy='+sortBy+'&sortingOrder='+sortingOrder);
    }

    getStudentCourseById(courseId){
        return axios.get(STUDENT_API_BASE_URL + '/' + courseId);
    }

    getStudentCourseById(date){
        return axios.get(STUDENT_API_BASE_URL + '/' + date);
    }
}

export default new StudentModuleService()