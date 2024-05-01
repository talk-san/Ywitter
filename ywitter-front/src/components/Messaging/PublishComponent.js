import React from 'react';
import { useStompClient } from "react-stomp-hooks";

export const PublishComponent = () => {
    const stompClient = useStompClient();

    const publishMessage = () => {
        if(stompClient) {
            stompClient.publish({destination: '/messages', body: 'Hello User'})
        }
    }

    return (
        <div onClick={publishMessage}> Send </div>
    )
}
