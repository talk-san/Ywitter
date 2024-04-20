import React from 'react';
import './Feed.css'
import {getYuserId, request} from "../../axios_helper";


class Post extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLiked: false,
            numOfLikes: this.props.numOfLikes
        };
    }

    componentDidMount() {
        this.fetchLikes();
    }

    fetchLikes = () => {
        const postId = this.props.postId;
        const url = `/api/v1/post/getLikes/${postId}`;
        request('GET', url)
            .then(response => {
                const likedUsers = response.data;
                const currUserId = parseInt(getYuserId());
                const isLiked = likedUsers.some(user => user.id === currUserId);
                this.setState({ isLiked });
            })
            .catch(error => {
                console.error('Error fetching likes for post:', error);
            });
    }



    handleLikeSubmit = (event) => {
        event.preventDefault();
        // Logic for handling like submission
        console.log('Like submitted');

        const url = `api/v1/post/like/${this.props.postId}`;

        this.setState(prevState => ({
            isLiked: !prevState.isLiked, // Toggle the isLiked state
            numOfLikes: prevState.numOfLikes + (prevState.isLiked ? -1 : 1) // Increment or decrement numOfLikes based on the current isLiked state
        }), () => {
            request('POST', url)
                .then(response => {
                    console.log(response.data);
                })
                .catch(error => {
                    console.error('Error deleting post:', error);
                });

        });


    }

    // Handler method for handling comment submission
    handleCommentSubmit = (event) => {
        //event.preventDefault();
        // Logic for handling comment submission
        console.log('Comment submitted');
    }

    handleDeleteSubmit = (event) => {
        event.preventDefault();

        const url = `api/v1/post/delete/${this.props.postId}`;

        request('DELETE', url)
            .then(response => {
                console.log(response.data);
                this.props.updateFeed();
            })
            .catch(error => {
                console.error('Error deleting post:', error);
            });
    }

    formatPostedAt = (postedAt) => {
        const now = new Date();
        const postTime = new Date(postedAt);
        const timeDifference = now.getTime() - postTime.getTime();
        const seconds = Math.floor(timeDifference / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);

        if (days > 0) {
            return `${days} day${days > 1 ? 's' : ''} ago`;
        } else if (hours > 0) {
            return `${hours} hour${hours > 1 ? 's' : ''} ago`;
        } else if (minutes > 0) {
            return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
        } else {
            return `${seconds} second${seconds !== 1 ? 's' : ''} ago`;
        }
    }

    render() {
        const formattedPostedAt = this.formatPostedAt(this.props.postedAt);
        const isUserPost = String(this.props.userId) === getYuserId();

        return (
            <div className="card bg-black text-white" style={{ border: '1px solid #333', borderRadius: '1px', padding: '10px', position: 'relative' }}>
                <div className="card-body">
                    <div className="d-flex align-items-center">
                        <div className="d-flex align-items-center">
                            <img src={this.props.userPhoto} className="mb-2" alt="Profile"
                                 style={{width: '40px', height: '40px', borderRadius: '50%'}}/>
                            <div style={{marginLeft: '8px'}}>
                                <h5 className="card-title"
                                    style={{color: 'white', marginTop: '1px'}}>{this.props.firstName}</h5>
                                <h6 className="card-subtitle mb-2"
                                    style={{color: '#64686d', marginTop: '1px'}}>{this.props.username}</h6>
                            </div>
                        </div>
                        <div style={{position: 'absolute', top: 25, right: 25}}>
                            <p className="card-text"
                               style={{color: '#64686d', marginTop: '1px', marginBottom: '0'}}>{formattedPostedAt}</p>
                        </div>
                    </div>
                    <p className="card-text mt-3" style={{marginTop: '5px'}}>{this.props.text}</p>
                    <div className="me-2">
                        <form onSubmit={this.handleLikeSubmit} className="me-lg-2"
                              style={{display: 'inline-block', marginRight: '5px'}}>
                            <button type="submit" className="btn btn-sm hover rounded-pill">
                                <i className={`bi bi-heart${this.state.isLiked ? '-fill' : ''} text-white`}></i>
                                <span style={{color: 'white', marginLeft: '5px'}}>{this.state.numOfLikes}</span>
                            </button>

                        </form>
                        <form onSubmit={this.handleCommentSubmit} className="me-lg-2"
                              style={{display: 'inline-block', marginRight: '5px'}}>
                            <button type="submit" className="btn btn-sm hover rounded-pill">
                                <i className="bi bi-chat text-white"></i>
                                <span style={{color: 'white', marginLeft: '5px'}}>{this.props.numOfComments}</span>
                            </button>
                        </form>
                        {isUserPost &&
                            <form onSubmit={this.handleDeleteSubmit} style={{display: 'inline-block'}}>
                                <button type="submit" className="btn btn-sm hover rounded-pill">
                                    <i className="bi bi-trash text-white"></i>
                                </button>
                            </form>
                        }
                    </div>


                </div>
            </div>
        );
    }
}

export default Post;