import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
import axios from 'axios';

function Header() {
  const [visibleSubMenu, setVisibleSubMenu] = useState(null);
  const [messages, setMessages] = useState([]);

  const handleMouseEnter = (item) => {
    setVisibleSubMenu(item);
  };

  const handleMouseLeave = () => {
    setVisibleSubMenu(null);
  };

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/messages');
        setMessages(response.data);
      } catch (error) {
        console.error('Error fetching messages:', error);
      }
    };

    fetchMessages();
  }, []);

  return (
      <header className="app-header">
        <nav>
          <ul className="menu">
            <li>
              <Link to="/">React App</Link>
            </li>
            <li onMouseEnter={() => handleMouseEnter('users')} onMouseLeave={handleMouseLeave}>
              <span>Usuários</span>
              <ul className={`submenu ${visibleSubMenu === 'users' ? 'show' : ''}`}>
                <li><Link to="/main/userform">Cadastrar Novo Usuário</Link></li>
                <li><Link to="/main/users">Consultar Todos</Link></li>
              </ul>
            </li>
            <li onMouseEnter={() => handleMouseEnter('device')} onMouseLeave={handleMouseLeave}>
              <span>Dispositivo</span>
              <ul className={`submenu ${visibleSubMenu === 'device' ? 'show' : ''}`}>
                <li><Link to="#">Cadastrar Dispositivo</Link></li>
                <li><Link to="#">Consultar Dispositivo</Link></li>
              </ul>
            </li>
            <li onMouseEnter={() => handleMouseEnter('gateway')} onMouseLeave={handleMouseLeave}>
              <span>Gateway</span>
              <ul className={`submenu ${visibleSubMenu === 'gateway' ? 'show' : ''}`}>
                <li><Link to="#">Cadastrar Gateway</Link></li>
                <li><Link to="#">Consultar Gateway</Link></li>
              </ul>
            </li>
            <li onMouseEnter={() => handleMouseEnter('reading')} onMouseLeave={handleMouseLeave}>
              <span>Leitura</span>
              <ul className={`submenu ${visibleSubMenu === 'reading' ? 'show' : ''}`}>
                <li><Link to="#">Cadastrar Leitura</Link></li>
                <li><Link to="#">Consultar Leitura</Link></li>
              </ul>
            </li>
            <li onMouseEnter={() => handleMouseEnter('sensor')} onMouseLeave={handleMouseLeave}>
              <span>Sensor</span>
              <ul className={`submenu ${visibleSubMenu === 'sensor' ? 'show' : ''}`}>
                <li><Link to="#">Cadastrar Sensor</Link></li>
                <li><Link to="#">Consultar Sensor</Link></li>
              </ul>
            </li>
            <li>
              <span>Mensagens Recebidas</span>
              <ul className="messages">
                {messages.map((message, index) => (
                  <li key={index}>{message}</li>
                ))}
              </ul>
            </li>
          </ul>
        </nav>
      </header>
  );
}

export default Header;
