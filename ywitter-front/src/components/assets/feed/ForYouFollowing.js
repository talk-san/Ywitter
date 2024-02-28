import React from 'react';
import './Feed.css';

function ForYouFollowing({ onFeedChange }) {
    return (
        <nav className="navbar navbar-dark bg-black sticky-top" style={{ border: '1px solid #333', borderRadius: '2px', padding: '10px' }}>
            <div className="container-fluid d-flex justify-content-around">
                <ul className="navbar-nav hover rounded-pill">
                    <li className="nav-item">
                        <button className="btn text-white hover" onClick={() => onFeedChange('forYou')}>For You</button>
                    </li>
                </ul>
                <div className="separator mx-3"></div>
                <ul className="navbar-nav hover rounded-pill">
                    <li className="nav-item">
                        <button className="btn text-white" onClick={() => onFeedChange('following')}>Following</button>
                    </li>
                </ul>
            </div>
        </nav>
    );
}

export default ForYouFollowing;
