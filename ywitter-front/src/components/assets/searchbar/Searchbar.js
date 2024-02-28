import React from 'react'
import './Searchbar.css'
export const Searchbar = () => {
    
    const userList = ['User1', 'User2', 'User3', 'User4', 'User5'];
  
    const handleSearch = () => {
      console.log("searching")
    };
  
    return (
      <div className="container-fluid sticky-top">
        <div>
          <input className="rounded-pill" type="text" placeholder="⌕ Search... " style={{
              marginTop: '10px', width: '300px', height: '50px', paddingLeft: '20px', backgroundColor: '#202327', border: 'none', color: "white"}} />
        </div>

      </div>
    );
  };
