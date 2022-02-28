
const STUDENT_API_BASE_URL = "http://localhost:8080/api/v1/student";

class StudentService {

    getStudents(pageNumber,pageSizes,sortBy,sortingOrder){
        return axios.get(STUDENT_API_BASE_URL+'/pageNumber='+pageNumber+'&pageSizes='+pageSizes+'&sortBy='+sortBy+'&sortingOrder='+sortingOrder);
    }

    createStudent(student){
        return axios.post(STUDENT_API_BASE_URL, student);
    }

    getStudentById(studentId){
        return axios.get(STUDENT_API_BASE_URL + '/' + studentId);
    }

    updateStudent(student, studentId){
        return axios.put(STUDENT_API_BASE_URL + '/' + studentId, student);
    }

    deleteStudent(studentId){
        return axios.delete(STUDENT_API_BASE_URL + '/' + studentId);
    }
}

export default new StudentService()