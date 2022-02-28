import React, { useReducer } from 'react'
import { Grid,Paper, Avatar, TextField, Button, Typography,Icon } from '@material-ui/core'
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import {Redirect} from 'react-router-dom';
import CreateUserComponent from './AdminModule/UserMaintenance/CreateUserComponent';
export function MaterialUIFormSubmit(props) {
    const paperStyle={padding :20,height:'70vh',width:280, margin:"20px auto"}
    const avatarStyle={backgroundColor:'#1bbd7e'}
    const btnstyle={margin:'8px 0'}

    const [formInput, setFormInput] = useReducer(
        (state, newState) => ({ ...state, ...newState }),
        {
          username: "",
          password: "",
          flag:0
        }
      );

      const handleInput = evt => {
        const name = evt.target.name;
        const newValue = evt.target.value;
        setFormInput({ [name]: newValue });
      };
      
      const handleSubmit = evt => {
        
        evt.preventDefault();
        let data=new FormData(evt.target);
        console.log(data);
        fetch("http://127.0.0.1:8080/login", {
          
          //  credentials: 'include',
          withCredentials : 'include',
            method: "POST",
            body:data
            
          })
          .then(v => {

            if(!v.ok) {
                setFormInput({ "flag": 2 });
                return v.text().then(text => { throw new Error(text) })
              
              }else{
                 setFormInput({ "flag": 1 }); 
                 console.log( v.text());
               }
            
        })

          .catch(err => {
            console.log('caught it!',err);
         })
       
      };

     if (formInput.flag === 1) {
     //   return <Redirect to='/employees'/>

     //return <Redirect to='/user'/>
    }
    return(

        <Grid>
            <Paper elevation={10} style={paperStyle}>
                <Grid align='center'>
                     <Avatar style={avatarStyle}><LockOutlinedIcon/></Avatar>
                    <h2>Sign In</h2>
                </Grid>
                <Typography variant="h5" component="h3">
          {props.formName}
        </Typography>
        <Typography component="p">{props.formDescription}</Typography>
                <form onSubmit={handleSubmit}>
                <TextField 
                label='Username'
                name="username"
                defaultValue={formInput.username}
                 placeholder='Enter username' 
                 onChange={handleInput}
                 fullWidth required/>

                <TextField 
                label='Password' 
                name="password"
                placeholder='Enter password' 
                defaultValue={formInput.password}
                type='password' 
                onChange={handleInput}
                fullWidth required/>

                <FormControlLabel
                    control={
                    <Checkbox
                        name="checkedB"
                        color="primary"
                    />
                    }
                    label="Remember me"
                 />
                <Button type='submit' color='primary' variant="contained" style={btnstyle} fullWidth >Sign in</Button>
                
            </form>
            { formInput.flag === 2 && <p>Username and Password is wrong</p>}
            </Paper>
        </Grid>
    )
}
