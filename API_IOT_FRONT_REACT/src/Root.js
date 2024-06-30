// src/Root.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import App from './App';
import UsersList from './components/UsersList';
import UserForm from './components/UserForm';
import MainLayout from './components/MainLayout';

function Root() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="main" element={<MainLayout />}>
          <Route path="userform" element={<UserForm />} />
          <Route path="users" element={<UsersList />} />
        </Route>
      </Routes>
    </Router>
  );
}

// src/index.js
export default Root;