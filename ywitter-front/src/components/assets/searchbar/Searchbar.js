import React from 'react'

export const Searchbar = () => {
    
    const userList = ['User1', 'User2', 'User3', 'User4', 'User5'];
  
    const handleSearch = () => {
      console.log("searching")
    };
  
    return (
      <div className="container-fluid">
        <div>
          <input type="text" placeholder="Search..." />
          <button onClick={handleSearch}>Search</button>
        </div>
        <div className="user-list">
          <h2>People to Follow</h2>
          <ul>
            {userList.map((user, index) => (
              <li key={index}>{user}</li>
            ))}
          </ul>
        </div>
      </div>
    );
  };
