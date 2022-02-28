import React, { Component } from 'react'
import StudentService from '../../../services/StudentService';

class CreateStudentComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            studentName: '',
            yearGroup: '',
            dateOfBirth: '',
            studentImage:'',
            courses:[] 
        }
        this.changeStudentNameHandler = this.changeStudentNameHandler.bind(this);
        this.changeyearGroupHandler = this.changeyearGroupHandler.bind(this);

        this.saveOrUpdateStudent = this.saveOrUpdateStudent.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            StudentService.getStudentById(this.state.id).then( (res) =>{
                let student = res.data;
                this.setState({studentName: student.studentName,
                    yearGroup: student.yearGroup,
                    dateOfBirth : student.dateOfBirth,
                    studentImage : student.studentImage,
                    courses : student.courses

                });
            });
        }        
    }
    saveOrUpdateStudent = (e) => {
        e.preventDefault();
        let student = {studentName: this.state.studentName, yearGroup: this.state.yearGroup, 
            dateOfBirth : this.state.dateOfBirth,
            studentImage : this.state.studentImage,
            courses : this.state.courses};
        console.log('Student => ' + JSON.stringify(student));

        // step 5
        if(this.state.id === '_add'){
            StudentService.createStudent(student).then(res =>{
                this.props.history.push('/students');
            });
        }else{
            StudentService.updateStudent(student, this.state.id).then( res => {
                this.props.history.push('/students');
            });
        }
    }
    
    changeStudentNameHandler= (event) => {
        this.setState({studentName: event.target.value});
    }

    changeyearGroupHandler= (event) => {
        this.setState({yearGroup: event.target.value});
    }

    changeDOBHandler= (event) => {
        this.setState({dateOfBirth: event.target.value});
    }
    changeImage= (event) => {
        this.setState({studentImage: event.target.value});
    }
    changeCourses= (event) => {
        this.setState({courses: event.target.value});
    }

    cancel(){
        this.props.history.push('/student');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Student</h3>
        }else{
            return <h3 className="text-center">Update Student</h3>
        }
    }

    
    render() {

        const options = [
            {
              label: "Admin",
              value: "Admin",
            },
            {
              label: "Student",
              value: "Student",
            },
            {
              label: "Teacher",
              value: "Teacher",
            },
          ];
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Student Name: </label>
                                            <input placeholder="Student Name" name="studentName" className="form-control" 
                                                value={this.state.studentName} onChange={this.changeStudentNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Year Group: </label>
                                            <input placeholder="Year Group" name="yearGroup" className="form-control" 
                                                value={this.state.yearGroup} onChange={this.changeyearGroupHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Date Of Birth: </label>
                                            <input placeholder="Date Of Birth" name="dateOfBirth" className="form-control" 
                                                value={this.state.dateOfBirth} onChange={this.changeDOBHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Student Picture: </label>
                                            <input placeholder="Student Image" name="studentImage" className="form-control" 
                                                value={this.state.studentImage} onChange={this.changeImage}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Courses: </label>
                                        
                                        <select>
                                                    {options.map((option) => (
                                                    <option name="courses" className="form-control" style={{alignContent: "center"}} value={this.state.courses} onChange={this.changeCourses}>{option.label}</option>
                                                    ))}
                                                </select>
                                                
                                        </div>
                                        <button className="btn btn-success" onClick={this.saveOrUpdateStudent}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default CreateStudentComponent
