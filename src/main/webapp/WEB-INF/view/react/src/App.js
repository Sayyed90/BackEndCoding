import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import { MaterialUIFormSubmit } from './components/login';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateUserComponent from './components/AdminModule/UserMaintenance/CreateUserComponent';
import ListUserComponent from './components/AdminModule/UserMaintenance/ListUserComponent';
import ViewUserComponent from './components/AdminModule/UserMaintenance/ViewUserComponent';
function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                         <Route path = "/" exact component = {MaterialUIFormSubmit}></Route>
                          <Route path = "/user" exact component = {ListUserComponent}></Route>
                          <Route path = "/add-users/:id" component = {CreateUserComponent}></Route>
                          <Route path = "/view-users/:id" component = {ViewUserComponent}></Route>
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
