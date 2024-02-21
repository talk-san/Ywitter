import './App.css'
import logo from '../logo.png'
import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Header from './Header'
import AppContent from './AppContent'
import { getAuthToken } from '../axios_helper'
import MainPage from '../pages/MainPage';


function App() {
    const [authenticated, setAuthenticated] = useState(false);
  
    useEffect(() => {
      // Check if user is authenticated
      const token = getAuthToken(); // Implement this method to retrieve token
      setAuthenticated(!!token); // Set authenticated to true if token exists
    }, []);
  
    return (
      <Router>
        <div>
          <Header pageTitle="Ywitter" logoSrc={logo} />
          <div className="container-fluid">
            <div className="row">
              <div className="col">
                {/* Define routes */}
                <Routes>
                  <Route
                    path="/"
                    element={
                      authenticated ? (
                        <Navigate to="/feed" />
                      ) : (
                        <Navigate to="/login" />
                      )
                    }
                  />
                  {/* Main page */}
                  <Route
                    path="/feed"
                    element={
                      authenticated ? (
                        <MainPage />
                      ) : (
                        <Navigate to="/login" />
                      )
                    }
                  />
                  {/* Route for /feed */}
                  <Route
                    path="/login"
                    element={
                      authenticated ? (
                        <Navigate to="/feed" />
                      ) : (
                        <AppContent/>
                      )
                    }
                  />
                  {/* Route for /login */}
                </Routes>
              </div>
            </div>
          </div>
        </div>
      </Router>
    );
  }
  

export default App;