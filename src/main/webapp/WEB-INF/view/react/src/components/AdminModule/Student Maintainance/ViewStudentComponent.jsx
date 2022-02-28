import React, { Component } from 'react'
import StudentService from '../../../services/StudentService'

class ViewStudentComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            student: {}
        }
    }

    componentDidMount(){
        StudentService.getStudentById(this.state.id).then( res => {
            this.setState({student: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Student Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> Student name: </label>
                            <div> { this.state.student.studentName }</div>
                        </div>
                        <div className = "row">
                            <label> YEAR GROUP: </label>
                            <div> { this.state.student.yearGroup }</div>
                        </div>
                        <div className = "row">
                            <label> D.O.B: </label>
                            <div> { this.state.student.dateOfBirth }</div>
                        </div>
                        <div className = "row">
                            <label> Student Picture: </label>
                            <div> { this.state.student.studentImage }</div>
                        </div>
                        <div className = "row">
                            <label> Courses: </label>
                            <div> { this.state.student.courses }</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default ViewStudentComponent
