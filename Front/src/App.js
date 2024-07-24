import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import'./App.css';
import MainPage from './pages/MainPage';
import Login from './pages/Login';

const App = () => (
  <div className="mobile-container">
        <Router>
            <Routes>
                <Route path="/" element={<MainPage/>} />
                <Route path="/login" element={<Login/>} />
            </Routes>
        </Router>

</div>
);

export default App;
