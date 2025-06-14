import React, { useState, useEffect } from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png';
import { useNavigate, useLocation } from "react-router-dom";

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('');
    const [notificationCount, setNotificationCount] = useState(0);
    const [surveyCount, setSurveyCount] = useState(0);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        // 기존 로컬스토리지 기반 로그인 상태 확인
        const storedIsLoggedIn = localStorage.getItem("isLoggedIn") === "true";
        if (storedIsLoggedIn) {
            setIsLoggedIn(true);
            setUserName("김영희"); // 샘플 데이터
            
            // 샘플 알림 데이터 (로그인된 사용자만)
            setNotificationCount(3);
            setSurveyCount(1);
        }

        // TODO: 백엔드 구축 후 활성화할 API 호출 코드
        /*
        const checkLoginStatus = async () => {
            try {
                const token = localStorage.getItem("token");
                if (!token) {
                    setIsLoggedIn(false);
                    return;
                }

                const response = await fetch('/api/auth/verify', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                if (response.ok) {
                    const userData = await response.json();
                    setIsLoggedIn(true);
                    setUserName(userData.name);
                    
                    // 알림 및 설문 수 가져오기
                    await fetchNotificationCounts();
                } else {
                    // 토큰이 유효하지 않은 경우
                    localStorage.removeItem("token");
                    localStorage.removeItem("isLoggedIn");
                    setIsLoggedIn(false);
                }
            } catch (error) {
                console.error('로그인 상태 확인 실패:', error);
                setIsLoggedIn(false);
            }
        };

        const fetchNotificationCounts = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await fetch('/api/notifications/count', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    setNotificationCount(data.notificationCount || 0);
                    setSurveyCount(data.surveyCount || 0);
                }
            } catch (error) {
                console.error('알림 수 가져오기 실패:', error);
            }
        };

        checkLoginStatus();
        */
    }, []);

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handleBackClick = () => {
        navigate(-1);
    };

    const handleIconClick = (path) => {
        if (isLoggedIn) {
            navigate(path);
        } else {
            navigate('/login');
        }
    };

    const getPageTitle = () => {
        switch (location.pathname) {
            case '/login':
                return '로그인';
            case '/signup':
                return '회원가입';
            case '/alldiet':
                return '모든 식단 목록';
            case '/dietinfo':
                return '식단 상세 정보';
            case '/mypage':
                return '내정보';
            case '/userinfoupdate':
                return '내 정보 수정';
            case '/orderlist':
                return '주문 내역';
            case '/weeklyfoodmenu':
                return '1주 전체 식단';
            case '/survey':
                return '설문조사';
            case '/notification':
                return '알림';
            case '/dietselection':
                return '식단 선택';
            case '/menuselection':
                return '메뉴 선택';
            case '/dietpayment':
                return '식단 결제';
            case '/admin':
                return '관리자 페이지';
            default:
                return 'NutriHub';
        }
    };

    const isMainPage = location.pathname === '/';

    return (
        <header className="header-header">
            <div className="header-header-container">
                {!isMainPage && (
                    <div className="header-other-page-header">
                        <button 
                            className="header-back-button"
                            onClick={handleBackClick}
                            aria-label="뒤로가기"
                        >
                            ← 뒤로
                        </button>
                        <h1 className="header-page-title">{getPageTitle()}</h1>
                    </div>
                )}
                {isMainPage && (
                    <>
                        <div className="header-header-left">
                            {isLoggedIn ? (
                                <span className="header-user-greeting">{userName}님</span>
                            ) : (
                                <button 
                                    className="header-login-button"
                                    onClick={handleLoginClick}
                                >
                                    로그인
                                </button>
                            )}
                        </div>
                        <div className="header-header-center">
                            <img 
                                src={logo} 
                                className="header-logo-image" 
                                alt="NutriHub" 
                                onClick={() => navigate('/')}
                            />
                        </div>
                        <div className="header-header-right">
                            {isLoggedIn && (
                                <div className="header-header-actions">
                                    <button 
                                        className="header-action-button"
                                        onClick={() => handleIconClick('/notification')}
                                        aria-label="알림"
                                    >
                                        알림
                                        {notificationCount > 0 && (
                                            <span className="header-notification-badge">{notificationCount}</span>
                                        )}
                                    </button>
                                    <button 
                                        className="header-action-button"
                                        onClick={() => handleIconClick('/survey')}
                                        aria-label="설문조사"
                                    >
                                        설문
                                        {surveyCount > 0 && (
                                            <span className="header-notification-badge">{surveyCount}</span>
                                        )}
                                    </button>
                                </div>
                            )}
                        </div>
                    </>
                )}
            </div>
        </header>
    );
};

export default Header;
