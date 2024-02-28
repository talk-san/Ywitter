import React from 'react';
import './Feed.css'


class Post extends React.Component {
    render() {
        return (
            <div className="card bg-black text-white" style={{ border: '1px solid #333', borderRadius: '1px', padding: '10px' }}>
                <div className="card-body">
                    <div className="d-flex align-items-center">
                        <img src={this.props.profilePic} className="mb-2" alt="Profile" style={{ width: '40px', height: '40px', borderRadius: '50%' }} />
                        <div style={{ marginLeft: '8px' }}>
                            <h5 className="card-title" style={{ color: 'white', marginTop: '1px' }}>{this.props.firstName}</h5>
                            <h6 className="card-subtitle mb-2" style={{ color: '#64686d', marginTop: '1px' }}>{this.props.username}</h6>
                        </div>
                    </div>
                    <p className="card-text mt-3" style={{ marginTop: '5px' }}>{this.props.text}</p>
                    <div className="me-2">
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-heart text-white "></i></button>
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-chat text-white "></i></button>
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-trash text-white "></i></button>
                        <button className="btn btn-sm me-lg-5 hover rounded-pill"><i className="bi bi-bookmark text-white "></i></button>
                    </div>
                </div>
            </div>
        );
    }

}

export default Post;