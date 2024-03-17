import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { request } from '../axios_helper'; // Import your custom request function

const VerificationPage = () => {
    const { code } = useParams();
    const [verificationResult, setVerificationResult] = useState(null);

    useEffect(() => {
        const verifyCode = async () => {
            try {
                const response = await request('GET', `http://localhost:3030/verify?code=${code}`);
                if (response.status === 200) {
                    setVerificationResult({ success: true });
                } else {
                    const data = await response.data;
                    throw new Error(data.message);
                }
            } catch (error) {
                console.error('Error verifying code:', error);
                setVerificationResult({ success: false, message: error.message });
            }
        };

        verifyCode();
    }, [code]);

    return (
        <div>
            {verificationResult ? (
                verificationResult.success ? (
                    <p>Verification successful!</p>
                ) : (
                    <p>{verificationResult.message}</p>
                )
            ) : (
                <p>Verifying...</p>
            )}
        </div>
    );
};

export default VerificationPage;
