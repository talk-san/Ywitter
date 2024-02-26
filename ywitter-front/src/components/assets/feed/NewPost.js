import React, { useState } from 'react';
import { request } from '../../../axios_helper';


const NewPost = ({ onPostSuccess }) => {
    const [content, setContent] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
       
        request("POST", "/api/v1/post/create", { content })
            .then((response) => {
                
                onPostSuccess(response.data);
                setContent('');
            })
            .catch((error) => {
            });
    };

    return (
        <div>
            <h2>New Post</h2>
            <form onSubmit={handleSubmit}>
                <textarea 
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="What's on your mind?"
                    rows={4}
                    cols={30}
                    required
                    className="form-control"
                ></textarea>
                <br />
                <button type="submit" className="btn btn-primary">Post</button>
            </form>
        </div>
    );
};

export default NewPost;