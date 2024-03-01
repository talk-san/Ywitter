import React from 'react';
import './Feed.css'


class Post extends React.Component {
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

        return (
            <div className="card bg-black text-white" style={{ border: '1px solid #333', borderRadius: '1px', padding: '10px', position: 'relative' }}>
                <div className="card-body">
                    <div className="d-flex align-items-center">
                        <div className="d-flex align-items-center">
                            <img src={this.props.userPhoto} className="mb-2" alt="Profile" style={{ width: '40px', height: '40px', borderRadius: '50%' }} />
                            <div style={{ marginLeft: '8px' }}>
                                <h5 className="card-title" style={{ color: 'white', marginTop: '1px' }}>{this.props.firstName}</h5>
                                <h6 className="card-subtitle mb-2" style={{ color: '#64686d', marginTop: '1px' }}>{this.props.username}</h6>
                            </div>
                        </div>
                        <div style={{ position: 'absolute', top: 25, right: 25 }}>
                            <p className="card-text" style={{ color: '#64686d', marginTop: '1px', marginBottom: '0' }}>{formattedPostedAt}</p>
                        </div>
                    </div>
                    <p className="card-text mt-3" style={{ marginTop: '5px' }}>{this.props.text}</p>
                    <div className="me-2">
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-heart text-white "></i></button>
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-chat text-white "></i></button>
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-trash text-white "></i></button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Post;