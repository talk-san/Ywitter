import React from "react";

import WelcomeContent from './WelcomeContent'
import AuthContent from "./AuthContent"
import LoginForm from "./LoginForm"
import Buttons from "./Buttons"
import Tweets from "./Tweets";

import {request, setAuthHeader} from '../axios_helper'
import Feed from "./Feed";


export default class AppContent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      componentToShow: "welcome"
    };
  }  
  

  login = () => {
    this.setState({componentToShow: "login"})
  };

  logout = () => {
    this.setState({componentToShow: "welcome"})
    setAuthHeader(null);
  };

  onLogin = (e, email, password) => {
    e.preventDefault();
    request("POST",
      "/api/v1/auth/authenticate",
      { 
        email: email, 
        password: password
      }
      ).then((response) => {
        setAuthHeader(response.data.token);
        this.setState({componentToShow: "feed"})
      }).catch((error) => {
        setAuthHeader(null);
        this.setState({componentToShow: "welcome"});
      });
  };

  onRegister = (e, firstName, lastName, email, password) => {
    e.preventDefault();
    request("POST",
      "/api/v1/auth/register",
      { 
        firstName: firstName,
        lastName: lastName,
        email: email, 
        password: password
      }
      ).then((response) => {
        this.setState({componentToShow: "messages"});
        setAuthHeader(response.data.token);
      }).catch((error) => {
        this.setState({componentToShow: "welcome"});
      });
  };


  render () {
        return (
          <div>
            <Buttons login={this.login} logout={this.logout}/>
            {this.state.componentToShow === "welcome" && <WelcomeContent/>}
            {this.state.componentToShow === "messages" && <AuthContent/>}
            {this.state.componentToShow === "login" && <LoginForm onLogin={this.onLogin} onRegister={this.onRegister}/>}
            {this.state.componentToShow === "tweets" && <Tweets/>}
            {this.state.componentToShow === "feed" && <Feed/>}
      
          </div>  
        );
    };
}