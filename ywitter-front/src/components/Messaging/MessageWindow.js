import React, { useState } from 'react';
import { ChildComponent } from './ChildComponent';
import { PublishComponent } from './PublishComponent';

export const MessageWindow = () => {
    // Dummy list of recent messages
    const [messages, setMessages] = useState([
        "Hello!",
        "How are you?",
        "Nice to meet you!"
    ]);

    // Dummy state for new message
    const [newMessage, setNewMessage] = useState("");

    const handleSendMessage = () => {
        // Add new message to the list of messages
        setMessages([...messages, newMessage]);
        // Clear the input field after sending message
        setNewMessage("");
    };

    return (
        <div>
            <div style={{ maxHeight: '70%', overflowY: 'auto', marginBottom: '10px' }}>
                {/* Display recent messages */}
                {messages.map((message, index) => (
                    <div key={index}>{message}</div>
                ))}
            </div>
            <div style={{ display: 'flex' }}>
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    style={{ flex: '1', marginRight: '10px' }}
                />
                <button onClick={handleSendMessage}>Send</button>
            </div>
            <ChildComponent />
            <PublishComponent />
        </div>
    );
};
