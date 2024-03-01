import * as React from 'react';

import {request} from '../../../axios_helper';
import Post from './Post';
import NewPost from './NewPost';

export default class Feed extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data : [],
            feed: "forYou"
        };
    };

    componentDidMount() {
        this.fetchData();
    }

    onFeedChange = (feedType) => {
        this.setState({ feed: feedType }, () => {
            this.fetchData();
        });
    };

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
                });
        }
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
                                    For You
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
                <NewPost/>
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