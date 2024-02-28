import * as React from 'react';

import {request, setAuthHeader} from '../../../axios_helper';
import Post from './Post';
import NewPost from './NewPost';
import profilePic from '../icons/aki.jpg';

export default class Feed extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data : []
        };
    };

    componentDidMount() {
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
    }



    render() {
        return (
            <div className="container-fluid" style={{ border: '1px solid #333', borderRadius: '2px', padding: '10px' }}>
                <NewPost/>
                {this.state.data.map((post, index) => (
                    <Post
                        key={index}
                        firstName={post.firstName}
                        username={post.username}
                        postedAt={post.postedAt}
                        text={post.text}
                        profilePic={profilePic}
                    />
                ))}
            </div>
        );
    }
}