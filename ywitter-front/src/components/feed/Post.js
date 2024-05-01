import React from 'react';
import './Feed.css'
import {getYuserId, request} from "../../axios_helper";
import {Button, Modal} from 'react-bootstrap';


class Post extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLiked: false,
            numOfLikes: this.props.numOfLikes,
            numOfComments: this.props.numOfComments,
            showModal: false,
            text: ''
        };
    }

    componentDidMount() {
        this.fetchLikes();
    }

    handleChange = (e) => {
        this.setState({text: e.target.value});
    }

    fetchLikes = () => {
        const postId = this.props.postId;
        const url = `/api/v1/post/getLikes/${postId}`;
        request('GET', url)
            .then(response => {
                const likedUsers = response.data;
                const currUserId = parseInt(getYuserId());
                const isLiked = likedUsers.some(user => user.id === currUserId);
                this.setState({isLiked});
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
                    console.error('Error liking post:', error);
                });

        });


    }

    // Handler method for handling comment submission
    handleCommentSubmit = (event) => {
        event.preventDefault()
        this.setState({showModal: true});
        console.log(this.state.showModal)
    };

    handleCloseModal = () => {
        this.setState({showModal: false});
        console.log(this.state.showModal)
    };

    // This is for posting a comment
    handleSubmitCommentPost = (event) => {
        event.preventDefault()
        console.log("Comment submitted")

        const url = `api/v1/post/comment/${this.props.postId}`;


        this.setState(prevState => ({
            numOfComments: prevState.numOfComments + 1
        }), () => {
            request('POST', url, {text: this.state.text})
                .then(response => {
                    console.log(response.data);
                    this.setState({showModal: false});
                    this.props.updateFeed();
                })
                .catch(error => {
                    console.error('Error commenting post:', error);
                });
        });

    }

    handleDeleteSubmit = (event) => {
        event.preventDefault();
        const url = `api/v1/post/delete/${this.props.postId}`;

        request('DELETE', url)
            .then(response => {
                console.log(response.data);
                this.props.refreshFeed('forYou')
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

    handleClick(postId) {
        this.props.onFeedChange(postId)
    }

    render() {
        const formattedPostedAt = this.formatPostedAt(this.props.postedAt);
        const isUserPost = String(this.props.userId) === getYuserId();

        return (
            <div className="card bg-black text-white post" onClick={() => this.handleClick(this.props.postId)}
                 style={{border: '1px solid #333', borderRadius: '1px', padding: '10px', position: 'relative'}}>
                <div className="card-body" onClick={() => this.handleClick(this.props.postId)}>
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
                        <form className="me-lg-2"
                              style={{display: 'inline-block', marginRight: '5px'}}>
                            <button type="submit" className="btn btn-sm hover like-button rounded-pill"
                                    onClick={(e) => {
                                        e.stopPropagation(); // Prevent event propagation
                                        this.handleLikeSubmit(e);
                                    }}>
                                <i className={`bi bi-heart${this.state.isLiked ? '-fill' : ''} text-white`}></i>
                                <span style={{color: 'white', marginLeft: '5px'}}>{this.state.numOfLikes}</span>
                            </button>
                        </form>
                        <form className="me-lg-2"
                              style={{display: 'inline-block', marginRight: '5px'}}>
                            <button type="submit" className="btn btn-sm hover comment-button rounded-pill"
                                    onClick={(e) => {
                                        e.stopPropagation(); // Prevent event propagation
                                        this.handleCommentSubmit(e);
                                    }}>
                                <i className="bi bi-chat text-white"></i>
                                <span style={{color: 'white', marginLeft: '5px'}}>{this.props.numOfComments}</span>
                            </button>
                        </form>

                        {isUserPost &&
                            <form style={{display: 'inline-block'}}>
                                <button type="submit" className="btn btn-sm hover delete-button rounded-pill"
                                        onClick={(e) => {
                                            e.stopPropagation(); // Prevent event propagation
                                            this.handleDeleteSubmit(e);
                                        }}>
                                    <i className="bi bi-trash text-white"></i>
                                </button>
                            </form>
                        }

                    </div>
                </div>

                <Modal
                    show={this.state.showModal}
                    onHide={this.handleCloseModal}
                    style={{backgroundColor: 'rgba(128, 128, 128, 0.2)'}}>
                    <div onClick={(e) => e.stopPropagation()}>
                        <Modal.Header closeButton
                                      style={{backgroundColor: 'black', border: 'none', borderBottom: '1px solid #333'}}
                        >
                            <Modal.Title style={{color: 'white'}}>Replying to {this.props.firstName}</Modal.Title>
                        </Modal.Header>
                        <Modal.Body style={{backgroundColor: 'black', color: 'white'}}>
                            <div style={{
                                display: 'grid',
                                gridTemplateRows: 'auto 1fr auto',
                                gap: '1rem',
                                borderRadius: '15px',
                                padding: '20px'
                            }}>
                        <textarea
                            value={this.state.text}
                            onChange={this.handleChange}
                            placeholder="Reply to the post"
                            rows={4}
                            cols={30}
                            required
                            className="form-control"
                            style={{
                                backgroundColor: 'black',
                                color: 'white',
                                resize: 'none',
                                border: '1px solid #333',
                            }}
                        ></textarea>
                                <form onSubmit={this.handleSubmitCommentPost}>
                                    <button type="submit" className="btn btn-primary rounded-pill"
                                            style={{justifySelf: 'end'}}>Post
                                    </button>
                                </form>
                            </div>
                        </Modal.Body>
                    </div>
                </Modal>
            </div>
        );
    }


}

export default Post;