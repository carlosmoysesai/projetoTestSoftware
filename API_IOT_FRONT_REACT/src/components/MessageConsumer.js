import React, { useState } from 'react';
import Stomp from 'react-stomp';

function MessageConsumer() {
    const [messages, setMessages] = useState([]);

    const handleReceiveMessage = (msg) => {
        setMessages(prevMessages => [...prevMessages, msg]);
    };

    return (
        <div>
            <h2>Mensagens Recebidas</h2>
            <Stomp
                topic="/topic/mensagem"
                onMessage={(msg) => handleReceiveMessage(msg)}
            />
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>{message}</li>
                ))}
            </ul>
        </div>
    );
}

export default MessageConsumer;
