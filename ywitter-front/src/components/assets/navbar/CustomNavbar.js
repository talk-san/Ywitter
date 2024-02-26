import React from 'react';
import { setAuthHeader } from '../../../axios_helper';
import { useNavigate } from 'react-router-dom';
import logoSrc from '../icons/logo.png';
import { Navbar, Nav } from 'react-bootstrap';
import 'bootstrap-icons/font/bootstrap-icons.css';

export default function CustomNavbarComponent() {
  const navigate = useNavigate();

  const logout = () => {
    setAuthHeader(null)
    navigate("/login");
  };

  return (
    <Navbar bg="black" variant="dark" expand="lg" className="flex-column ">
      <Navbar.Brand href="#home" className="ml-auto">
        <img
          src={logoSrc} 
          alt="Logo"
          width="100"
          height="100"
          className="d-inline-block align-top"
        />
      </Navbar.Brand>
      <Nav className="flex-column text-right" style={{ fontSize: '1.5rem' }}>
        <Nav.Link href="/feed" className="text-white mb-2"><i className="bi bi-house-door-fill"></i> Feed</Nav.Link>
        <Nav.Link href="/search" className="text-white mb-2" ><i className="bi bi-search"></i> Search</Nav.Link>
        <Nav.Link href="/notifications" className="text-white mb-2"><i className="bi bi-bell-fill"></i> Notifications</Nav.Link>
        <Nav.Link href="/messages" className="text-white mb-2"><i className="bi bi-chat-dots-fill"></i> Messages</Nav.Link>
        <Nav.Link href="/bookmarks" className="text-white mb-2"><i className="bi bi-bookmark-fill"></i> Bookmarks</Nav.Link>
        <Nav.Link href="/profile" className="text-white mb-2"><i className="bi bi-person-circle"></i> Profile</Nav.Link>
        <Nav.Link href="/settings" className="text-white mb-2"><i className="bi bi-gear-fill"></i> Settings</Nav.Link>
      </Nav>
      <button className="btn btn-light mt-3 ml-auto" onClick={logout}>Logout</button> 
      </Navbar>
  );
}
