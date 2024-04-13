import * as React from 'react';

import {request} from '../../axios_helper';
import Post from './Post';
import NewPost from './NewPost';
import blankPfp from "../icons/blankPfp.png";

export default class Feed extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data : [],
            feed: "forYou",
            loggedInUser: {}
        };
    };

    componentDidMount() {
        this.fetchData();
        this.fetchUser();
    }

    onFeedChange = (feedType) => {
        this.setState({ feed: feedType }, () => {
            this.fetchData();
        });
    };

    updateFeed = (newPost) => {
        this.setState(prevState => ({
            data: [newPost, ...prevState.data]
        }));
    }

    fetchData() {
        if (this.state.feed === 'forYou') {
            request(
                "GET",
                "/api/v1/post/all",
                {}).then(
                (response) => {
                    this.setState({data: response.data})
                })
                .catch((error) => {
                    console.log("Error fetching posts")
                    this.setState({ error: "Error connecting to the database. Try logging in again" });
                });
        } else if (this.state.feed === 'following') {
            request(
                "GET",
                "/api/v1/post/feed",
                {}).then(
                (response) => {
                    this.setState({data: response.data})
                })
                .catch((error) => {
                    console.log("Error fetching posts")
                    this.setState({ error: "Error connecting to the database." });
                });
        }
    }

    fetchUser() {
        request("GET", "/api/v1/user/getUser")
            .then((response) => {
                this.setState({ loggedInUser: response.data });
            })
            .catch((error) => {
                this.setState({ error: "Error getting user data." });
            });
    }

    render() {
        return (
            <div className="container-fluid" style={{border: '1px solid #333', borderRadius: '15px', padding: '10px'}}>
                <nav className="navbar navbar-dark bg-black sticky-top"
                     style={{border: '1px solid #333', borderRadius: '15px', padding: '10px', marginBottom: '10px'}}>
                    <div className="container-fluid d-flex justify-content-around">
                        <ul className="navbar-nav hover rounded-pill">
                            <li className="nav-item">
                                <button
                                    className={`btn text-white ${this.state.feed === 'forYou' ? 'active selected' : ''}`}
                                    aria-pressed={this.state.feed === 'forYou'}
                                    onClick={() => this.onFeedChange('forYou')}>
                                    Your posts
                                </button>
                            </li>
                        </ul>
                        <div className="separator mx-3"></div>
                        <ul className="navbar-nav hover rounded-pill">
                            <li className="nav-item">
                                <button
                                    className={`btn text-white ${this.state.feed === 'following' ? 'active selected' : ''}`}
                                    aria-pressed={this.state.feed === 'following'}
                                    onClick={() => this.onFeedChange('following')}>
                                    Following
                                </button>
                            </li>
                        </ul>
                    </div>
                </nav>
                <div style={{display: 'flex', alignItems: 'center', margin: '10px 0'}}>
                    <img
                        src={this.state.loggedInUser.photoUrl ? this.state.loggedInUser.photoUrl : blankPfp}
                        alt="User Profile Picture"
                        className="rounded-circle mr-3"
                        style={{width: "50px", height: "50px"}}
                    />
                    <span style={{
                        color: 'white',
                        marginLeft: '10px'
                    }}>Welcome back, {this.state.loggedInUser.firstName}</span>
                </div>
                <NewPost onPostSuccess={this.updateFeed}/>
                {this.state.error && <div className="error" style={{color: 'white'}}>{this.state.error}</div>}
                {this.state.data.map((post, index) => (
                    <Post
                        key={index}
                        firstName={post.firstName}
                        username={post.username}
                        postedAt={post.postedAt}
                        text={post.text}
                        userPhoto={post.userPhoto}
                    />
                ))}
            </div>
        );
    }
}