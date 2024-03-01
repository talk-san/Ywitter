import React, { useState } from 'react';
import { request } from '../../../axios_helper';


const NewPost = ({ onPostSuccess }) => {
    const [text, setText] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
       
        request("POST", "/api/v1/post/create", { text })
            .then((response) => {
                
                onPostSuccess(response.data);
                setText('');
            })
            .catch((error) => {
            });
    };

    return (
        <div style={{ color: 'white' }}>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateRows: 'auto 1fr auto', gap: '1rem', borderRadius: '25px'}}>
               <textarea
                   value={text}
                   onChange={(e) => setText(e.target.value)}
                   placeholder="What's on your mind?"
                   rows={4}
                   cols={30}
                   required
                   className="form-control"
                   style={{ backgroundColor: 'black', color: 'white', resize: 'none', border: '1px solid #333',  }}
              ></textarea>
                <button type="submit" className="btn btn-primary rounded-pill" style={{ justifySelf: 'end' }}>Post</button>
            </form>
        </div>
    );


};

export default NewPost;