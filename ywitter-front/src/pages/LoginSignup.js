import LoginForm from '../components/LoginForm';
import logoSrc from '../components/assets/icons/logo.png'
import Footer from '../components/assets/footer/Footer';
import {request, setAuthHeader} from "../axios_helper";
import {useNavigate} from "react-router-dom";
import {useState} from "react";


export const LoginSignup = () => {

    const navigate = useNavigate();

    const onLogin = (e, email, password) => {
        e.preventDefault();
        request("POST", "/api/v1/auth/authenticate", { email, password })
            .then((response) => {
                setAuthHeader(response.data.token);
                navigate("/feed");
            })
            .catch((error) => {
                setAuthHeader(null);
            });
    };

    const onRegister = (e, firstName, lastName, email, password) => {
        e.preventDefault();
        request(
            "POST",
            "/api/v1/auth/register", { firstName, lastName, email, password })
            .then(
                (response) => {
                setAuthHeader(response.data.token);
                navigate("/feed");
            })
            .catch((error) => {
                navigate("/login");
            });
    };

   return (
     <div className='container-fluid'>
       <div className='row justify-content-center align-items-center' style={{ minHeight: '100vh' }}>
         <img src={logoSrc} alt="Logo" style={{ position: 'absolute', top: '150px', left: '50%', transform: 'translateX(-50%)', zIndex: 1, width: '100px', height: '100px' }} />
             <div className='col-md-6'>
                 <LoginForm onLogin={onLogin} onRegister={onRegister} />
             </div>
         <Footer/>
       </div>
     </div>
   );
};


