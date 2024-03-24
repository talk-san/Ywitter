import React from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { MessageWindow } from "../components/Messaging/MessageWindow";
import { UserList } from "../components/Messaging/UserList";

const webSocketUrls = {};
const registration = {};

class WebSocket extends React.Component {
    state = {
        ws: null, // Setting ws to null initially
        timeStamp: Date.now(),
        maxReconnect: 1
    };

    componentDidMount() {
        this.setupWebSocket();
    }

    setupWebSocket = () => {
        const socket = new SockJS('http://localhost:8080/ws');
        const ws = Stomp.over(socket);
        this.setState({ ws }, () => {
            const webSoc = this.state.ws;
            webSoc.connect({}, this.connect);
            webSoc.message = (body) => this.setState({ timeStamp: Date.now() });
            webSoc.error = (err) => {
                if (this.state.maxReconnect > 0) {
                    this.setState({ maxReconnect: this.state.maxReconnect - 1 }, this.connect);
                }
            };
        });
    };

    connect = () => {
        const channels = webSocketUrls[this.props.name];
        this.setState({ maxReconnect: this.props.maxReconnect });
        channels.forEach((channel) => {
            const webSoc = this.state.ws;
            webSoc.subscribe(channel.route, channel.callback);
            webSoc.send(registration.route, { timeStamp: this.state.timeStamp.toString() }, 'timeStamp');
        });
    };

    render() {
        return <span />;
    }
}

WebSocket.defaultProps = {
    name: 'something',
    maxReconnect: 5,
};

export const MessagingPage = () => {
    return (
        <div style={{ display: 'flex' }}>
            <div style={{ flex: '0 0 30%', backgroundColor: '#2a2828', padding: '20px' }}>
                <UserList />
            </div>
            <div style={{ flex: '1', padding: '20px' }}>
                <MessageWindow />
            </div>
        </div>
    );
};
