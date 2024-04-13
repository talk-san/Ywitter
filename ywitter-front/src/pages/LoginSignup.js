import LoginForm from '../components/LoginForm';
import logoSrc from '../components/icons/logo.png'
import Footer from '../components/footer/Footer';
import {useNavigate} from "react-router-dom";



export const LoginSignup = () => {

    const navigate = useNavigate();

    return (
      <div className='container-fluid'>
        <div className='row justify-content-center align-items-center' style={{ minHeight: '100vh' }}>
          <img src={logoSrc} alt="Logo" style={{ position: 'absolute', top: '150px', left: '50%', transform: 'translateX(-50%)', zIndex: 1, width: '100px', height: '100px' }} />
              <div className='col-md-6'>
                  <LoginForm navigate={navigate}/>
              </div>
          <Footer/>
        </div>
      </div>
    );
};


