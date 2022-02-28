import React, { Component } from 'react'
import UserService from '../../../services/UserService';

class CreateUserComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
            username: '',
            password: '',
            email_id: '',
            address:'',
            roles:[] 
        }
        this.changeUserNameHandler = this.changeUserNameHandler.bind(this);
        this.changePaswordHandler = this.changePaswordHandler.bind(this);

        this.saveOrUpdateUser = this.saveOrUpdateUser.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            UserService.getUserById(this.state.id).then( (res) =>{
                let user = res.data;
                this.setState({username: user.username,
                    password: user.password,
                    email_id : user.email_id,
                    address : user.address,
                    roles : user.roles

                });
            });
        }        
    }
    saveOrUpdateUser = (e) => {
        e.preventDefault();
        let user = {username: this.state.username, password: this.state.password, 
            email_id : this.state.email_id,
            address : this.state.address,
            roles : this.state.roles};
        console.log('user => ' + JSON.stringify(user));

        // step 5
        if(this.state.id === '_add'){
            UserService.createUser(user).then(res =>{
                this.props.history.push('/users');
            });
        }else{
            UserService.updateUser(user, this.state.id).then( res => {
                this.props.history.push('/users');
            });
        }
    }
    
    changeUserNameHandler= (event) => {
        this.setState({username: event.target.value});
    }

    changePaswordHandler= (event) => {
        this.setState({password: event.target.value});
    }

    changeEmailHandler= (event) => {
        this.setState({email_id: event.target.value});
    }
    changeAddress= (event) => {
        this.setState({address: event.target.value});
    }
    changeRoles= (event) => {
        this.setState({roles: event.target.value});
    }

    cancel(){
        this.props.history.push('/user');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add User</h3>
        }else{
            return <h3 className="text-center">Update User</h3>
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
                                            <label> Username: </label>
                                            <input placeholder="First Name" name="username" className="form-control" 
                                                value={this.state.username} onChange={this.changeUserNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Password: </label>
                                            <input placeholder="Last Name" name="password" className="form-control" 
                                                value={this.state.password} onChange={this.changePaswordHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Email Id: </label>
                                            <input placeholder="Email Address" name="email_Id" className="form-control" 
                                                value={this.state.email_id} onChange={this.changeEmailHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Address: </label>
                                            <input placeholder="Address" name="address" className="form-control" 
                                                value={this.state.address} onChange={this.changeAddress}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Roles: </label>
                                        
                                        <select>
                                                    {options.map((option) => (
                                                    <option name="roles" className="form-control" style={{alignContent: "center"}} value={this.state.roles} onChange={this.changeRoles}>{option.label}</option>
                                                    ))}
                                                </select>
                                                
                                        </div>
                                        <button className="btn btn-success" onClick={this.saveOrUpdateUser}>Save</button>
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

export default CreateUserComponent
