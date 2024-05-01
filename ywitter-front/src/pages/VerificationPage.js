import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { request } from '../axios_helper';
import logoSrc from '../components/icons/logo.png';

const VerificationPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [verificationMessage, setVerificationMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        const code = new URLSearchParams(location.search).get("code");

        if (code) {
            request('GET', `http://localhost:8080/api/v1/auth/verify?code=${code}`)
                .then(response => {
                    setVerificationMessage(response.data + " You can now close this tab");
                })
                .catch(error => {
                    setErrorMessage(error.response.data);
                });
        }
    }, [location.search]);

    const handleBackToLogin = () => {
        navigate('/login');
    };

    return (
        <div className="container-fluid d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
            <div className="row text-center">
                <div className="col mb-4">
                    <img src={logoSrc} alt="Logo" style={{ maxWidth: '100px' }} />
                    <div className="d-flex flex-column bd-highlight mb-auto">
                        <div className="p-2 bd-highlight">{verificationMessage && <p style={{ color: 'white' }}>{verificationMessage}</p>}</div>
                        <div className="p-2 bd-highlight">{errorMessage && <p style={{ color: 'white' }}>{errorMessage}</p>}</div>
                    </div>
                    {(verificationMessage || errorMessage) && (
                        <div className="btn btn-link" onClick={handleBackToLogin}>
                            Back to login
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default VerificationPage;
