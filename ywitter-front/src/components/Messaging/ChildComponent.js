import React, { useState } from 'react';
import { useSubscription } from "react-stomp-hooks";

export const ChildComponent = () => {
    const [message, setMessage] = useState("");

    // Subscribe to the custom queue
    useSubscription('/user/public/', (message) => {setMessage(message.body)});

    return (
        <div> The message from some other user is {message}</div>
    )
}
