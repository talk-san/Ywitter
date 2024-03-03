import React from 'react';
import blankPfp from '../icons/blankPfp.png'


const UserCard = ({user}) => {

    const { firstName, email, photoUrl } = user;

    const followUser = () => {
        // Implement follow functionality here
        console.log(`Following ${firstName}`);
    };

    return (
        <div className="user-card d-flex align-items-center">
            <img src={photoUrl ? photoUrl : blankPfp} alt={email} className="rounded-circle mr-3" style={{width: "50px", height: "50px", marginLeft: '-5px'}} />
            <div className="card-body">
                <h4 className="mb-1" style={{ fontSize: "1rem", marginLeft: '5px', marginTop: '5px'}}>{firstName}</h4>
                <p className="mb-1" style={{ marginLeft: '5px'}}>{email}</p>
            </div>
            <div className="ml-auto">
                <button onClick={followUser} className="btn btn-outline-primary rounded-pill" style={{marginRight: '15px'}}>Follow</button>
            </div>
        </div>
    );
};

export default UserCard;