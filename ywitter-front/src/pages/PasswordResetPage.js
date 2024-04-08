import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { request } from '../axios_helper';
import logoSrc from '../components/assets/icons/logo.png';

const PasswordResetPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [resetToken, setResetToken] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [resetSuccess, setResetSuccess] = useState(false);

    useEffect(() => {
        const token = new URLSearchParams(location.search).get("token");

        if (token) {
            setResetToken(token);
        }
    }, [location.search]);

    const handleResetPassword = () => {
        if (newPassword.trim() === '') {
            setErrorMessage('Please enter a new password.');
            return;
        }
        console.log("Token: ", resetToken);
        console.log("New Pass: ", newPassword);

        request('POST', `/api/v1/auth/change-password`, { token: resetToken, newPassword })
            .then(response => {
                setResetSuccess(true);
            })
            .catch(error => {
                setErrorMessage(error.response.data.message || 'An error occurred.');
            });
    };

    const handleBackToLogin = () => {
        navigate('/login');
    };

    return (
        <div className="container-fluid d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
            <div className="row text-center">
                <div className="col mb-4">
                    <img src={logoSrc} alt="Logo" style={{ maxWidth: '100px', marginBottom: '20px' }} />
                    {!resetSuccess && (
                        <div>
                            <h3 style={{ color: 'white' }}>Enter your new Password</h3>
                            <div className="form-group">
                                <input type="password" className="form-control" placeholder="New Password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} style={{ marginBottom: '10px' }} />
                            </div>
                            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                            <button className="btn btn-primary" onClick={handleResetPassword}>Reset Password</button>
                        </div>
                    )}
                    {resetSuccess && (
                        <div>
                            <p>Your password has been successfully reset.</p>
                            <button className="btn btn-link" onClick={handleBackToLogin}>Back to login</button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );

}

export default PasswordResetPage;
