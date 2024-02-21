import './App.css'
import logo from '../logo.png'

import Header from './Header'
import AppContent from './AppContent'
import Tweets from './Tweets'
import { BrowserRouter as Router,Routes, Route } from 'react-router-dom';


function App() {
    return (
        <Router>
            <div>
                <Header pageTitle="Ywitter" logoSrc={logo} />
                <div className="container-fluid">
                    <div className="row">
                        <div className="col">
                            {/* Define routes */}
                            <Routes>
                                <Route path="/" element={<AppContent />} /> {/* Main page */}
                                <Route path="/feed" element={<Tweets />} /> {/* Route for /feed */}
                            </Routes>
                        </div>
                    </div>
                </div>
            </div>
        </Router>
    );
}

export default App;