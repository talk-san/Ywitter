import './App.css'
import logo from '../logo.png'

import Header from './Header'
import AppContent from './AppContent'
import Navbar from './Navbar';
import Feed from './Feed';
import TweetForm from './TweetForm';
import { useState } from 'react';
import { BrowserRouter as Router,Routes, Route } from 'react-router-dom';


function App() {

    const [tweets, setTweets] = useState([]);
    const handleTweetSubmit = (tweet) => {
        setTweets([...tweets, {id:tweets.length+1, text:tweet}]);
    };
    return (
        <div>
            
            <Header pageTitle="Ywitter" logoSrc = {logo} />
            <div className="container-fluid">
                <div className="row">
                    <div className="col">
                        <AppContent/>
            <Router>
                        <Navbar />
                        <Routes>
                          <Route path="/" element={<Feed tweets={tweets} />} />
                        </Routes>
                        <TweetForm onTweetSubmit={handleTweetSubmit} />
            </Router>
                         
                    </div>
                </div>
            </div>
            
        </div>
    );
}

export default App;