import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import'./App.css';
import Login from './pages/Login';
import Signup from './pages/Signup';
import AllDiet from './pages/AllDietPage';
import DietInfo from "./pages/DietInfoPage";
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import UserInfoUpdate from './pages/UserInfoUpdate';
import OrderList from './pages/OrderList';
import WeeklyFoodMenu from './pages/WeeklyFoodMenu';
import Survey from './pages/Survey';
import SurveyDetail from './pages/SurveyDetail';
import Notification from './pages/Notification';

const App = () => (
  <div className="mobile-container">
        <Router>
            <Routes>
                <Route path="/" element={<MainPage/>} />
                <Route path="/login" element={<Login/>} />
                <Route path="/signup" element={<Signup/>} />
                <Route path="/alldiet" element={<AllDiet />} />
                <Route path="/dietinfo" element={<DietInfo />} />
                <Route path="/mypage" element={<MyPage />} />
                <Route path="/userinfoupdate" element={<UserInfoUpdate />} />
                <Route path="/orderlist" element={<OrderList />} />
                <Route path="/weeklyfoodmenu" element={<WeeklyFoodMenu />} />
                <Route path="/survey" element={<Survey />} />
                <Route path="/survey-detail" element={<SurveyDetail />} />
                <Route path="/notification" element={<Notification />} />
            </Routes>
        </Router>

</div>
);

export default App;
