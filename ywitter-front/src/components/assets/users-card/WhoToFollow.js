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
        <div className="container-fluid sticky-top">
            <div className="rounded-5" style={{
                marginTop: '20px',
                width: '300px',
                height: '500px',
                paddingLeft: '20px',
                backgroundColor: '#202327',
                border: 'none',
                color: "white"}}>
                <h2 style={{ marginTop: '30px' }}>Who to Follow</h2>
                {usersToFollow.map((user, index) => (
                    <UserCard key={index} user={user} />
                ))}
            </div>
        </div>
    );
};

export default WhoToFollow;