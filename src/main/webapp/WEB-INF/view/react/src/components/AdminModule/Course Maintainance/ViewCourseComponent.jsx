import React, { Component } from 'react'
import CourseService from '../../../services/CourseService'

class ViewCourseComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            course: {}
        }
    }

    componentDidMount(){
        CourseService.getCourseById(this.state.id).then( res => {
            this.setState({course: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Course Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> Standard: </label>
                            <div> { this.state.course.standard }</div>
                        </div>
                        <div className = "row">
                            <label> Subject Name: </label>
                            <div> { this.state.course.subjectName }</div>
                        </div>
                        <div className = "row">
                            <label> Venue: </label>
                            <div> { this.state.course.venue }</div>
                        </div>
                        <div className = "row">
                            <label> Is Online Class: </label>
                            <div> { this.state.course.isOnlineClass }</div>
                        </div>
                        <div className = "row">
                            <label> Is F2F Class: </label>
                            <div> { this.state.course.isF2FClass }</div>
                        </div>
                        <div className = "row">
                            <label> Date: </label>
                            <div> { this.state.course.date }</div>
                        </div>
                        <div className = "row">
                            <label> Students: </label>
                            <div> { this.state.teacoursecher.students }</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default ViewCourseComponent
