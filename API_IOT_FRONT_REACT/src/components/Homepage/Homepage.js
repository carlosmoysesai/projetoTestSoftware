import React from 'react';
import ImageSide from './ImageSide';
import LoginSignupForm from './LoginSignupForm';
import './styles.css';

const HomePage = () => {
    return (
        <div className="homepage">
            <ImageSide />
            <LoginSignupForm />
        </div>
    );
};

export default HomePage;
