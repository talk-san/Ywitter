import React, { Component } from 'react';
import Tweets from './Tweets'; // Import the Tweets component

class Feed extends Component {
  render() {
    return (
      <div>
        <h1>Welcome to Ywitter</h1>
        <Tweets tweets={this.props.tweets} /> {/* Render the Tweets component */}
      </div>
    );
  }
}

export default Feed;
