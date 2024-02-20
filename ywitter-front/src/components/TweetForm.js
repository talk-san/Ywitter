import React, { useState } from 'react';

const TweetForm = ({ onTweetSubmit }) => {
  const [tweet, setTweet] = useState('');

  const handleTweetChange = (e) => {
    setTweet(e.target.value);
  };

  const handleSubmit = () => {
    onTweetSubmit(tweet);
    setTweet('');
  };

  return (
    <div>
      <textarea value={tweet} onChange={handleTweetChange} />
      <button onClick={handleSubmit}>Tweet</button>
    </div>
  );
};

export default TweetForm;
