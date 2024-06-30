// src/App.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import './App.css';

function App() {
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);

  const handleGetStartedClick = () => {
    navigate('/main');
  };

  useEffect(() => {
    const socket = new SockJS('http://localhost:8081/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log('Connected');
        stompClient.subscribe('/topic/messages', (message) => {
          setMessages((prevMessages) => [...prevMessages, message.body]);
        });
      },
      onStompError: (frame) => {
        console.error(frame);
      },
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, []);

  return (
    <div className="app-container">
      <header className="app-header">
        <h1 className="app-title">Bem vindo ao React</h1>
        <p className="app-description">React application!</p>
      </header>
      <main className="app-main">
        <button className="app-button" onClick={handleGetStartedClick}>
          Vamos iniciar!
        </button>
        <div>
          <h2>Mensagens do RabbitMQ</h2>
          <ul>
            {messages.map((msg, index) => (
              <li key={index}>{msg}</li>
            ))}
          </ul>
        </div>
      </main>
      <footer className="app-footer">
        <p className="app-footer-text">© 2024 React Application. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;
