// src/App.js
import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import './App.css';
import Layout from './components/Layout';
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
import DietPaymentMain from './pages/DietPaymentMainPage';
import DietPayment from './pages/DietPaymentPage';
import Admin from './pages/Admin';
import DietPaymentComplete from './pages/DietPaymentComplete';

const AppContent = () => {
  const location = useLocation();
  const isAdminPage = location.pathname === '/admin';
  
  // 알림 개수 상태 관리
  const [notificationCount, setNotificationCount] = useState(0);
  const [surveyCount, setSurveyCount] = useState(0);

  // 로그인 상태 전역 관리
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userInfo, setUserInfo] = useState(null);

  // 알림 개수 업데이트 콜백 함수
  const handleNotificationCountChange = (notifCount, survCount) => {
    setNotificationCount(notifCount);
    setSurveyCount(survCount);
  };

  // 로그인 상태 확인 함수
  const checkAuthStatus = async () => {
    try {
      const response = await fetch('http://nimn.store/api/users', {
        method: 'GET',
        credentials: 'include'
      });

      if (response.ok) {
        const userData = await response.json();
        setIsLoggedIn(true);
        setUserInfo(userData);
      } else {
        setIsLoggedIn(false);
        setUserInfo(null);
      }
    } catch (error) {
      setIsLoggedIn(false);
      setUserInfo(null);
    }
  };

  // 로그인 성공 핸들러
  const handleLoginSuccess = (userData) => {
    setIsLoggedIn(true);
    setUserInfo(userData);
  };

  // 로그아웃 핸들러
  const handleLogoutSuccess = () => {
    setIsLoggedIn(false);
    setUserInfo(null);
  };

  useEffect(() => {
    checkAuthStatus();
  }, []);

  useEffect(() => {
    const isSafari = /^((?!chrome|android).)*safari/i.test(navigator.userAgent);
    let unloadHandled = false;

    const handleBeforeUnload = (event) => {
      // Chrome 브라우저에서 새로고침 시 로컬 스토리지 유지
      if (unloadHandled) {
        localStorage.clear();
      } else {
        // 기본 새로고침 경고 메시지 처리
        event.preventDefault();
        event.returnValue = ''; // 일부 브라우저에서 기본 경고 메시지를 표시합니다.
      }
    };

    const handleVisibilityChange = () => {
      // Safari 브라우저에서 페이지가 보이지 않을 때 로컬 스토리지 비우기
      if (document.visibilityState === 'hidden') {
        localStorage.clear();
      }
    };

    const handleUnload = () => {
      // 페이지가 완전히 닫힐 때 로컬 스토리지 비우기
      unloadHandled = true;
      localStorage.clear();
    };

    if (isSafari) {
      // Safari 브라우저일 경우 visibilitychange 이벤트를 사용합니다.
      document.addEventListener('visibilitychange', handleVisibilityChange);
    } else {
      // Safari가 아닐 경우 beforeunload와 unload 이벤트를 사용합니다.
      window.addEventListener('beforeunload', handleBeforeUnload);
      window.addEventListener('unload', handleUnload);
    }

    return () => {
      if (isSafari) {
        document.removeEventListener('visibilitychange', handleVisibilityChange);
      } else {
        window.removeEventListener('beforeunload', handleBeforeUnload);
        window.removeEventListener('unload', handleUnload);
      }
    };
  }, []);

  return (
    <div className={isAdminPage ? "admin-container" : "mobile-container"}>
      {isAdminPage ? (
        <Routes>
          <Route path="/admin" element={<Admin />} />
        </Routes>
      ) : (
        // Layout에 알림 개수 props 전달
        <Layout 
          notificationCount={notificationCount} 
          surveyCount={surveyCount}
        >
          <Routes>
            {/* MainPage에 알림 개수 콜백 함수 전달 */}
            <Route 
              path="/" 
              element={
                <MainPage 
                  onNotificationCountChange={handleNotificationCountChange} 
                />
              } 
            />
            <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/alldiet" element={<AllDiet />} />
            <Route path="/dietinfo" element={<DietInfo />} />
            <Route path="/mypage" element={<MyPage onLogoutSuccess={handleLogoutSuccess} />} />
            <Route path="/userinfoupdate" element={<UserInfoUpdate />} />
            <Route path="/orderlist" element={<OrderList />} />
            <Route path="/weeklyfoodmenu" element={<WeeklyFoodMenu />} />
            <Route path="/survey" element={<Survey />} />
            <Route path="/survey-detail" element={<SurveyDetail />} />
            <Route path="/notification" element={<Notification />} />
            <Route path="/dietselection" element={<DietSelection />} />
            <Route path="/dietpaymentmain" element={<DietPaymentMain />} />
            <Route path="/dietpayment" element={<DietPayment />} />
            <Route path="/dietpaymentcomplete" element={<DietPaymentComplete />} />
          </Routes>
        </Layout>
      )}
    </div>
  );
};

const App = () => {
  return (
    <Router>
      <AppContent />
    </Router>
  );
};

export default App;
