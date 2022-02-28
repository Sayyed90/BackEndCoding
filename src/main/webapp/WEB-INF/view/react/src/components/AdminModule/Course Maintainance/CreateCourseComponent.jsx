import React, { Component } from 'react'
import CourseService from '../../../services/CourseService';

class CreateCourseComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            standard: '',
            subjectName: '',
            venue: '',
            isOnlineClass:0,
            isF2FClass: 0,
            date:'',
            students:[] 
        }
        this.changeStandardHandler = this.changeStandardHandler.bind(this);
        this.changeSubjectNameHandler = this.changeSubjectNameHandler.bind(this);

        this.saveOrUpdateCourse = this.saveOrUpdateCourse.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            CourseService.getCourseById(this.state.id).then( (res) =>{
                let course = res.data;
                this.setState({standard: course.standard,
                    subjectName: course.subjectName,
                    venue : course.venue,
                    isOnlineClass : course.isOnlineClass,
                    isF2FClass : course.isF2FClassm,
                    date : course.date,
                    students : course.students

                });
            });
        }        
    }
    saveOrUpdateCourse = (e) => {
        e.preventDefault();
        let course = {standard: this.state.standard, subjectName: this.state.subjectName, 
            venue : this.state.venue,
            isOnlineClass : this.state.isOnlineClass,
            isF2FClass : this.state.isF2FClass,date : this.state.date,students : this.state.students};
        console.log('Course => ' + JSON.stringify(course));

        // step 5
        if(this.state.id === '_add'){
            CourseService.createCourse(course).then(res =>{
                this.props.history.push('/courses');
            });
        }else{
            CourseService.updateCourse(course, this.state.id).then( res => {
                this.props.history.push('/courses');
            });
        }
    }
    
    changeStandardHandler= (event) => {
        this.setState({standard: event.target.value});
    }

    changeSubjectNameHandler= (event) => {
        this.setState({subjectName: event.target.value});
    }

    changeVenueHandler= (event) => {
        this.setState({venue: event.target.value});
    }
    changeOnlnClass= (event) => {
        this.setState({isOnlineClass: event.target.value});
    }
    changeF2FClass= (event) => {
        this.setState({isF2FClass: event.target.value});
    }
    changeDate= (event) => {
        this.setState({date: event.target.value});
    }
    changeStudents= (event) => {
        this.setState({students: event.target.value});
    }

    cancel(){
        this.props.history.push('/course');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Course</h3>
        }else{
            return <h3 className="text-center">Update Course</h3>
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
                                            <label> Standard: </label>
                                            <input placeholder="Standard" name="standard" className="form-control" 
                                                value={this.state.standard} onChange={this.changeStandardHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Subject Name: </label>
                                            <input placeholder="Subject Name" name="subjectName" className="form-control" 
                                                value={this.state.subjectName} onChange={this.changeSubjectNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Venue: </label>
                                            <input placeholder="Venue" name="venue" className="form-control" 
                                                value={this.state.venue} onChange={this.changeVenueHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Is Online Class? : </label>
                                            <input placeholder="Is Online Class" name="isOnlineClass" className="form-control" 
                                                value={this.state.isOnlineClass} onChange={this.changeOnlnClass}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Is F2F Class? : </label>
                                            <input placeholder="Is F2F Class" name="isF2FClass" className="form-control" 
                                                value={this.state.isF2FClass} onChange={this.changeF2FClass}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Date: </label>
                                            <input placeholder="Date" name="date" className="form-control" 
                                                value={this.state.date} onChange={this.changeDate}/>
                                        </div>

                                        <div className = "form-group">
                                            <label> Students: </label>
                                        
                                        <select>
                                                    {options.map((option) => (
                                                    <option name="students" className="form-control" style={{alignContent: "center"}} value={this.state.students} onChange={this.changeStudents}>{option.label}</option>
                                                    ))}
                                                </select>
                                                
                                        </div>
                                        <button className="btn btn-success" onClick={this.saveOrUpdateCourse}>Save</button>
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

export default CreateCourseComponent
