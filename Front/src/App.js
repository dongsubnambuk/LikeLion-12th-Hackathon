import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import './App.css';
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
import DietSelection from './pages/DietSelectionPage';
import MenuSelection from './pages/MenuSelectionPage';
import DietPaymentMain from './pages/DietPaymentMainPage';
import DietPayment from './pages/DietPaymentPage';
import Admin from './pages/Admin';
import DietPaymentComplete from './pages/DietPaymentComplete';

const App = () => {

    useEffect(() => {
        let unloadEventFired = false;
    
        const handleBeforeUnload = (event) => {
          // 브라우저가 페이지를 새로고침하거나 이동할 때 이 이벤트가 발생합니다.
          if (unloadEventFired) {
            localStorage.clear(); // 페이지가 완전히 닫히는 경우만 처리하기 위해
          } else {
            // 새로고침 시에는 로컬 스토리지를 유지
            event.preventDefault();
            event.returnValue = ''; // 일부 브라우저에서 경고 메시지를 표시
          }
        };
    
        const handleUnload = () => {
          // 페이지가 완전히 닫힐 때 이 이벤트가 발생합니다.
          unloadEventFired = true;
          localStorage.clear();
        };
    
        window.addEventListener('beforeunload', handleBeforeUnload);
        window.addEventListener('unload', handleUnload);
    
        return () => {
          window.removeEventListener('beforeunload', handleBeforeUnload);
          window.removeEventListener('unload', handleUnload);
        };
      }, []);
    

  return (
    <div className="mobile-container">
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/alldiet" element={<AllDiet />} />
          <Route path="/dietinfo" element={<DietInfo />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/userinfoupdate" element={<UserInfoUpdate />} />
          <Route path="/orderlist" element={<OrderList />} />
          <Route path="/weeklyfoodmenu" element={<WeeklyFoodMenu />} />
          <Route path="/survey" element={<Survey />} />
          <Route path="/survey-detail" element={<SurveyDetail />} />
          <Route path="/notification" element={<Notification />} />
          <Route path="/dietselection" element={<DietSelection />} />
          <Route path="/menuselection" element={<MenuSelection />} />
          <Route path="/dietpaymentmain" element={<DietPaymentMain />} />
          <Route path="/dietpayment" element={<DietPayment />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/dietpaymentcomplete" element={<DietPaymentComplete />} />
        </Routes>
      </Router>
    </div>
  );
};

export default App;
