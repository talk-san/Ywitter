import * as React from 'react';

export default class WelcomeContent extends React.Component {
    render() {
        return (
            <div className="row justify-content-md-center">
                <div className="jumbotron jumbotron-fluid">
                    <div className="container">
                        Welcome, login to access ywitter.
                    </div>
                </div>
            </div>
        );
    }
}
