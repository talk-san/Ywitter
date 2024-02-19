import * as React from 'react';

import {request, setAuthHeader} from '../axios_helper';

export default class AuthContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data : []
        };
    };

    componentDidMount() {
        request(
            "GET",
            "/api/v1/open/messages",
            {}).then(
            (response) => {
                this.setState({data: response.data})
            }).catch(
            (error) => {
                if (error.response.status === 401) {
                    setAuthHeader(null);
                } else {
                    this.setState({data: error.response.code})
                }

            }
        );
    };

    render() {
        return (
            <div>
                {this.state.data && this.state.data.map((line, index) => (
                    <p key={index}>{line}</p>
                ))}
            </div>
        );
    }
    
}