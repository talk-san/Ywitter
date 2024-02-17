import * as React from 'react';

import {request} from '../axios_helper';

export default class AuthContent extends React.Component {
    constructor(props) {
        super(props);
        this.sate = {
            data : []
        };
    };

    componentDidMount() {
        request(
            "GET",
            "/messages",
            {}
        ).then((response) => {
            this.setState({data : response.data})
        });
    };

    render () {
        return (
            <div>
                {this.state.data && this.state.data.map((line) => <p>{line}</p>)}
            </div>
        );
    };
}