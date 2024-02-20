
import React from 'react';

const Feed = ({ tweets }) => {
  return (
    <div>
      {tweets.map((tweet) => (
        <div key={tweet.id}>{tweet.text}</div>
      ))}
    </div>
  );
};

export default Feed;

