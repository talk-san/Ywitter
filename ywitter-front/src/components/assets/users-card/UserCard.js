import React, {useState} from 'react';
import blankPfp from '../icons/blankPfp.png'
import {request} from "../../../axios_helper";


const UserCard = ({user}) => {
    const [isFollowing, setIsFollowing] = useState(false);
    const { firstName, email, photoUrl, id} = user;
    const trimmedEmail = email.length > 12 ? email.substring(0, 12) + '...' : email;
    const followUser = () => {
        // Implement follow functionality here
        setIsFollowing(!isFollowing);
        request(
            "POST",
            `/api/v1/user/follow/${id}`, {})
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log("Error following user:", error);
            });

    };

    return (
        <div className="user-card d-flex align-items-center" style={{ position: 'relative' }}>
            <img src={photoUrl ? photoUrl : blankPfp} alt={email} className="rounded-circle mr-3" style={{ width: "50px", height: "50px", marginLeft: '-5px' }} />
            <div className="card-body">
                <h4 className="mb-1" style={{ fontSize: "1rem", marginLeft: '5px', marginTop: '5px' }}>{firstName}</h4>
                <p className="mb-1" style={{ marginLeft: '5px' }}>{trimmedEmail}</p>
            </div>
            <button onClick={followUser} className={`btn ${isFollowing ? 'btn-light' : 'btn-outline-primary'} rounded-pill`} style={{ position: 'absolute', right: '-100px', top: '10px' }}>
                {isFollowing ? 'Following' : 'Follow'}
            </button>
        </div>
    );


};

export default UserCard;