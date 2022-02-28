import React, { Component } from 'react'
import CourseService from '../../../services/CourseService'

class ListCourseComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            courses: []
        }
        this.addCourse = this.addCourse.bind(this);
        this.editCourse  = this.editCourse.bind(this);
        this.deleteCourse  = this.deleteCourse.bind(this);
    }

    deleteCourse(id){
        CourseService.deleteCourse(id).then( res => {
            this.setState({courses: this.state.courses.filter(course => course.id !== id)});
        });
    }
    viewCourse(id){
        this.props.history.push(`/view-courses/${id}`);
    }
    editCourse(id){
        this.props.history.push(`/add-courses/${id}`);
    }

    componentDidMount(){
        CourseService.getCourses().then((res) => {
            this.setState({ courses: res.data});
        });
    }

    addCourse(){
        this.props.history.push('/add-courses/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Course List</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addCourse}> Add Course</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Standard</th>
                                    <th> Subject Name</th>
                                    <th> Venue</th>
                                    <th> Is Online Class </th>
                                    <th> is F2F Class</th>
                                    <th> Date</th>
                                    <th> Students</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.courses.map(
                                        course => 
                                        <tr key = {course.id}>
                                             <td> {course.standard} </td>   
                                             <td> {course.subjectName}</td>
                                             <td> {course.venue}</td>
                                             <td> {course.isOnlineClass}</td>
                                             <td> {course.isF2FClass}</td>
                                             <td> {course.date}</td>
                                             <td> {course.students}</td>
                                             <td>
                                                 <button onClick={ () => this.editCourse(course.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteCourse(course.id)} className="btn btn-danger">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewCourse(course.id)} className="btn btn-info">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListCourseComponent
