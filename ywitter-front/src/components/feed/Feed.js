import * as React from 'react';

import {request} from '../../axios_helper';
import Post from './Post';
import NewPost from './NewPost';
import blankPfp from "../icons/blankPfp.png";
import './Post.css'


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
        console.log(feedType)
        if (!isNaN(parseInt(feedType))) {
            this.setState({ feed: parseInt(feedType) }, () => {
                this.fetchPostAndReplies(parseInt(feedType));
            });
        } else {
            this.setState({ feed: feedType }, () => {
                this.fetchData();
            });
        }
    };

    fetchPostAndReplies(id) {
        console.log(id)
        const commentsUrl = `api/v1/post/getComments/${id}`;
        const postUrl = `api/v1/post/get/${id}`;
        // Fetch parent post
        // Fetch parent post
        request(
            "GET",
            postUrl,
            {}
        ).then(
            (postResponse) => {
                // Set parent post in state
                const post = postResponse.data;

                // Fetch comments after parent post is fetched
                request(
                    "GET",
                    commentsUrl,
                    {}
                ).then(
                    (commentsResponse) => {
                        // Combine post and comments into a single array
                        const combinedData = [post, ...commentsResponse.data];

                        // Set combined data in state
                        this.setState({
                            data: combinedData
                        });
                    }
                ).catch((commentsError) => {
                    console.log("Error fetching comments", commentsError);
                    this.setState({ error: "Error fetching comments" });
                });
            }
        ).catch((postError) => {
            console.log("Error fetching post", postError);
            this.setState({ error: "Error fetching post" });
        });


    }

    updateFeed = (newPost) => {
        if (newPost) {
            this.setState(prevState => ({
                data: [newPost, ...prevState.data]
            }));
        } else {
            this.fetchData();
        }
    }

    refreshFeed = (feedType) => {
        this.setState({ feed: feedType }, () => {
            this.fetchData();
        });
    }


    fetchData() {
        if (this.state.feed === 'forYou') {
            request(
                "GET",
                "/api/v1/post/all/parent",
                {}).then(
                (response) => {
                    this.setState({data: response.data})
                    console.log(response.data)
                })
                .catch((error) => {
                    console.log("Error fetching posts")
                    this.setState({ error: "Error connecting to the database. Try logging in again" });
                });
        }
        else if (this.state.feed === 'following') {
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
                {this.state.data
                    .map((post, index) => (
                    <div key={index}>
                        <Post
                            postId={post.postId}
                            firstName={post.firstName}
                            username={post.username}
                            postedAt={post.postedAt}
                            text={post.text}
                            userPhoto={post.userPhoto ? post.userPhoto : blankPfp}
                            userId={post.userId}
                            numOfLikes={post.numOfLikes}
                            numOfComments={post.numOfComments}
                            updateFeed={this.updateFeed}
                            parentPostId={post.parentPostId}
                            onFeedChange={this.onFeedChange}
                            refreshFeed={this.refreshFeed}
                        />
                    </div>
                ))}
            </div>
        );
    }
}