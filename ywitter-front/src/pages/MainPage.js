import React from 'react'; 
import Buttons from '../components/Buttons';
import Feed from '../components/Feed';
import { setAuthHeader } from '../axios_helper';
import WelcomeContent from '../components/WelcomeContent';

export default class MainPage extends React.Component {

  logout = () => {
    this.setState({componentToShow: "welcome"})
    setAuthHeader(null);
  };

  render() {
    return (
      <div>
        <Feed feed={this.props.feed} /> 
        <Buttons login={this.props.login} logout={this.logout} /> 
        {this.state.componentToShow === "welcome" && <WelcomeContent/>}
      </div>
    );
  }
}


