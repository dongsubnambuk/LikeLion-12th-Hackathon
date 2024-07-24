import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import'./App.css';
import MainPage from './pages/MainPage';
import Login from './pages/Login';
import Signup from './pages/Signup';
import AllDiet from './pages/AllDietPage';

const App = () => (
  <div className="mobile-container">
        <Router>
            <Routes>
                <Route path="/" element={<MainPage/>} />
                <Route path="/login" element={<Login/>} />
                <Route path="/signup" element={<Signup/>} />
                <Route path="/alldiet" element={<AllDiet />} />
            </Routes>
        </Router>

</div>
);

export default App;
