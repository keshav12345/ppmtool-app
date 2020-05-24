import React from 'react';
import logo from './logo.svg';
import './App.css';
import Dashboard from './component/Dashboard';
import Header from './component/layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import AddProject from './component/Project/AddProject'
import UpdateProject from './component/Project/UpdateProject'
import {Provider} from 'react-redux';
import store from './store';

function App() {
  return (
    <Provider store={store}>
    <Router>
        <div>
    <Header/>
    </div>
    <Route exact path="/dashboard" component={Dashboard}/>
    <Route exact path="/addProject" component={AddProject}/>
    <Route exact path="/updateProject/:id" component={UpdateProject}/>
    </Router>
    </Provider>
      );
}

export default App;
