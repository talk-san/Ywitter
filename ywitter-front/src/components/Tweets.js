import * as React from 'react';

import {request, setAuthHeader} from '../axios_helper';

export default class Tweets extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data : []
        };
    };

    componentDidMount() {
        request(
            "GET",
            "/api/v1/post/all",
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
                {this.state.data.map((post, index) => {
                    const postedDate = new Date(post.postedAt);
                    const formattedDate = `${postedDate.toLocaleDateString()} ${postedDate.toLocaleTimeString()}`;
                    return (
                        <div key={index}>
                            <h1>{post.firstName} - {formattedDate}</h1>
                            <p>{post.contents}</p>
                        </div>
                    );
                })}
            </div>
        );
    }
    
    
}

    
    
