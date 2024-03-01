import React from 'react';
import { setAuthHeader } from '../../../axios_helper';
import { useNavigate } from 'react-router-dom';
import logoSrc from '../icons/logo.png';
import { Navbar, Nav } from 'react-bootstrap';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './CustomNavbar.css'

export default function CustomNavbarComponent() {
  const navigate = useNavigate();

  const logout = () => {
    setAuthHeader(null)
    navigate("/login");
  };

    return (
        <Navbar bg="black" variant="dark" expand="lg" className="flex-column align-items-end sticky-top">
            <Navbar.Brand href="/feed" className="logo-wrapper rounded-pill">
                <img
                    src={logoSrc}
                    alt="Logo"
                    width="100"
                    height="100"
                    className="d-inline-block align-content-start"
                />
            </Navbar.Brand>
            <Nav className="flex-column text-start" style={{ fontSize: '1.5rem' }}>
                <Nav.Link href="/feed" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-house-door"></i> Feed</Nav.Link>
                <Nav.Link href="/search" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-search"></i> Search</Nav.Link>
                <Nav.Link href="/notifications" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-bell"></i> Notifications</Nav.Link>
                <Nav.Link href="/messages" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-chat-dots"></i> Messages</Nav.Link>
                <Nav.Link href="/bookmarks" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-bookmark"></i> Bookmarks</Nav.Link>
                <Nav.Link href="/profile" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-person"></i> Profile</Nav.Link>
                <Nav.Link href="/settings" className="text-white mb-2 rounded-pill custom-nav-link"><i className="bi bi-gear"></i> Settings</Nav.Link>
            </Nav>
            <button className="btn btn-light mt-3 rounded-pill" onClick={logout}>Logout</button>
        </Navbar>
    );
}
