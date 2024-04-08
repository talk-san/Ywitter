import React, {useEffect, useState} from 'react';
import UserCard from "./UserCard";
import profilePic from '../users-card/aki.jpg';
import {request} from "../../../axios_helper";

const WhoToFollow = config => {

    const [usersToFollow, setUsersToFollow] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = () => {
        request(
            "GET",
            "/api/v1/user/whoToFollow", {})
            .then((response) => {
                setUsersToFollow(response.data);
            })
            .catch((error) => {
                console.log("Error fetching users to follow:", error);
                setError("Error fetching users to follow. Please try again later.");
            });
    };

    return (
        <div className="container-fluid">
            <div className="rounded-5" style={{
                position: 'relative', // Set position to relative
                marginTop: '20px',
                width: '300px',
                height: '460px',
                paddingLeft: '20px',
                backgroundColor: '#202327',
                border: 'none',
                color: 'white'
            }}>
                <h4 style={{ position: 'absolute', top: '10px' }}>Who to Follow</h4>
                <div style={{ position: 'absolute', top: '50px' }}>
                    {usersToFollow.slice(0, 7).map((user, index) => (
                        <UserCard key={index} user={user} style={{ marginBottom: '10px' }} />
                    ))}
                </div>
            </div>
        </div>
    );


};

export default WhoToFollow;