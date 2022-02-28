import React, { Component } from 'react'
import TeacherService from '../../../services/TeacherService'

class ViewTeacherComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            teacher: {}
        }
    }

    componentDidMount(){
       TeacherService.getTeacherById(this.state.id).then( res => {
            this.setState({teacher: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Teacher Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> First name: </label>
                            <div> { this.state.teacher.firstName }</div>
                        </div>
                        <div className = "row">
                            <label> Last Name: </label>
                            <div> { this.state.teacher.lasName }</div>
                        </div>
                        <div className = "row">
                            <label> major: </label>
                            <div> { this.state.teacher.major }</div>
                        </div>
                        <div className = "row">
                            <label> Title: </label>
                            <div> { this.state.teacher.title }</div>
                        </div>
                        <div className = "row">
                            <label> Courses: </label>
                            <div> { this.state.teacher.courses }</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default ViewTeacherComponent
