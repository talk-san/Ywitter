import React, { useState } from 'react';

const CommentPopup = ({ show, onClose, onSubmit }) => {
    const [commentText, setCommentText] = useState('');

    const handleChange = (event) => {
        setCommentText(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        onSubmit(commentText);
        setCommentText('');
    }

    return (
        <div className={`modal fade ${show ? 'show' : ''}`} id="commentModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden={!show}>
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Add Comment</h5>
                        <button type="button" className="btn-close" onClick={onClose} aria-label="Close"></button>
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div className="modal-body">
                            <div className="mb-3">
                                <label htmlFor="commentText" className="form-label">Your Comment</label>
                                <input type="text" className="form-control" id="commentText" value={commentText} onChange={handleChange} />
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" onClick={onClose}>Close</button>
                            <button type="submit" className="btn btn-primary">Post</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CommentPopup;
