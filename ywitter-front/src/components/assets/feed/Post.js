import React from 'react';

class Post extends React.Component {
    render() {
        return (
            <div className="card bg-dark text-white">
                <div className="card-body">
                    {}
                    <div className="d-flex align-items-center">
                        <img src={this.props.profilePic} className="mr-2" alt="Profile" style={{ width: '40px', height: '40px', borderRadius: '50%' }} />
                        <div style={{ marginLeft: '8px' }}>
                            <h5 className="card-title" style={{ color: 'white' }}>{this.props.firstName}</h5>
                            <h6 className="card-subtitle mb-1 text-muted" style={{ color: 'white' }}>@{this.props.username}</h6>
                        </div>
                    </div>
                    {}
                    <p className="card-text mt-3">{this.props.content}</p>
                    {}
                    <div className="mt-3">
                        <button className="btn btn-primary btn-sm mr-2"><i className="bi bi-heart-fill text-white"></i></button>
                        <button className="btn btn-secondary btn-sm mr-2"><i className="bi bi-chat-fill text-white"></i></button>
                        <button className="btn btn-danger btn-sm"><i className="bi bi-trash text-white"></i></button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Post;