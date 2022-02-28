import React, { Component } from 'react'
import TeacherService from '../../../services/TeacherService';

class CreateTeacherComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            firstName: '',
            lasName: '',
            title: '',
            major:'',
            courses:[] 
        }
        this.changeFirstNameHandler = this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler = this.changeLastNameHandler.bind(this);

        this.saveOrUpdateTeacher = this.saveOrUpdateTeacher.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            TeacherService.getTeacherById(this.state.id).then( (res) =>{
                let teacher = res.data;
                this.setState({firstName: teacher.firstName,
                    lasName: teacher.lasName,
                    title : teacher.title,
                    major : teacher.major,
                    courses : teacher.courses

                });
            });
        }        
    }
    saveOrUpdateTeacher = (e) => {
        e.preventDefault();
        let teacher = {firstName: this.state.firstName, lasName: this.state.lasName, 
            title : this.state.title,
            major : this.state.major,
            courses : this.state.courses};
        console.log('Teacher => ' + JSON.stringify(teacher));

        // step 5
        if(this.state.id === '_add'){
            TeacherService.createTeacher(teacher).then(res =>{
                this.props.history.push('/teachers');
            });
        }else{
            TeacherService.updateTeacher(teacher, this.state.id).then( res => {
                this.props.history.push('/teachers');
            });
        }
    }
    
    changeFirstNameHandler= (event) => {
        this.setState({firstName: event.target.value});
    }

    changeLastNameHandler= (event) => {
        this.setState({lasName: event.target.value});
    }

    changeTitleHandler= (event) => {
        this.setState({title: event.target.value});
    }
    changeMajor= (event) => {
        this.setState({major: event.target.value});
    }
    changeCourses= (event) => {
        this.setState({courses: event.target.value});
    }

    cancel(){
        this.props.history.push('/teacher');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Teacher</h3>
        }else{
            return <h3 className="text-center">Update Teacher</h3>
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
                                            <label> First Name: </label>
                                            <input placeholder="First Name" name="firstname" className="form-control" 
                                                value={this.state.firstName} onChange={this.changeFirstNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Last Name: </label>
                                            <input placeholder="Last Name" name="lastName" className="form-control" 
                                                value={this.state.lasName} onChange={this.changeLastNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Title: </label>
                                            <input placeholder="Title" name="title" className="form-control" 
                                                value={this.state.title} onChange={this.changeTitleHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Major: </label>
                                            <input placeholder="Major" name="major" className="form-control" 
                                                value={this.state.major} onChange={this.changeMajor}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Courses: </label>
                                        
                                        <select>
                                                    {options.map((option) => (
                                                    <option name="courses" className="form-control" style={{alignContent: "center"}} value={this.state.courses} onChange={this.changeCourses}>{option.label}</option>
                                                    ))}
                                                </select>
                                                
                                        </div>
                                        <button className="btn btn-success" onClick={this.saveOrUpdateTeacher}>Save</button>
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

export default CreateTeacherComponent
