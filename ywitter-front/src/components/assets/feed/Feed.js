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
        this.fetchData = this.fetchData.bind(this); 
        this.handlePostSuccess = this.handlePostSuccess.bind(this); 
    };

    componentDidMount() {
        this.fetchData(); 
    }

    fetchData() {
        request(
            "GET",
            "/api/v1/post/all",
            {}).then(
            (response) => {
                this.setState({data: response.data})
            }).catch(
            (error) => {
                if (error.response.status === 401) {
                    setAuthHeader(null);
                } else {
                    this.setState({data: error.response.code})
                }

            }
        );
    };

    handlePostSuccess(newPost) {
        this.setState(prevState => ({
            data: [newPost, ...prevState.data]
        }));
    }

    render() {
        return (
            <div className="container-fluid" style={{ border: '1px solid #353535' }}>
                <NewPost onPostSuccess={this.handlePostSuccess} />
                {this.state.data.map((post, index) => (
                    <Post
                        key={index}
                        firstName={post.firstName}
                        username={post.username}
                        postedAt={post.postedAt}
                        content={post.content}
                        profilePic={profilePic}
                    />
                ))}
            </div>
        );
    }
}