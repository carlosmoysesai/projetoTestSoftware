import React from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';

const HomePage = () => {
  const navigate = useNavigate();

  const handleGetStartedClick = () => {
    navigate('/main');
  };

  return (
    <div className="homepage-container">
      <h2>Home Page</h2>
      <button className="app-button" onClick={handleGetStartedClick}>
        Vamos iniciar!
      </button>
    </div>
  );
};

export default HomePage;
