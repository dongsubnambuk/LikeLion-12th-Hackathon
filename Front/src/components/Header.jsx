import React, { useState, useEffect } from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png';
import { useNavigate, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faFileLines } from '@fortawesome/free-regular-svg-icons';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('');
    const [notificationCount, setNotificationCount] = useState(0);
    const [surveyCount, setSurveyCount] = useState(0); // 설문조사 카운트 추가
    const navigate = useNavigate();
    const location = useLocation();
    const email = localStorage.getItem("email");

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setIsLoggedIn(true);
            setUserName(user.name);
        }

        const handleGetUser = async () => {
            const token = localStorage.getItem("token");
            const email = localStorage.getItem("email");

            if (token && email) {
                try {
                    const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": token,
                        }
                    });

                    const result = await response.json();

                    if (response.status === 200) {
                        setUserName(result.name);
                        setIsLoggedIn(true);
                    } else {
                        console.log("로그인 실패: ", result.message);
                        alert("로그인 실패: " + result.message);
                    }
                } catch (error) {
                    console.error("Fetch error: ", error);
                }
            }
        };

        handleGetUser();

        // Load notifications from local storage
        const savedNotifications = JSON.parse(localStorage.getItem(`messages_${email}`)) || [];
        setNotificationCount(savedNotifications.length);

        // Load surveys from local storage
        const savedSurveys = JSON.parse(localStorage.getItem(`surveys_${email}`)) || [];
        setSurveyCount(savedSurveys.length);
    }, []);

    useEffect(() => {
        if (!email) return;

        const socket = new SockJS('http://nutrihub.kro.kr:14000/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log("Connected to WebSocket");

            const dietSubscription = client.subscribe(`/topic/notification/diet/${email}`, message => {
                addNotification(message.body);
            });

            const paymentSubscription = client.subscribe(`/topic/notification/payment/${email}`, message => {
                addNotification(message.body);
            });

            const surveySubscription = client.subscribe(`/topic/survey/${email}`, message => {
                addSurvey(message.body);
            });

            return () => {
                dietSubscription.unsubscribe();
                paymentSubscription.unsubscribe();
                surveySubscription.unsubscribe();
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            };
        }, error => {
            console.error('Connection error: ', error);
        });

        return () => {
            if (client && client.connected) {
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, [email]);

    const addNotification = (message) => {
        const savedMessages = JSON.parse(localStorage.getItem(`messages_${email}`)) || [];
        const newMessages = [...savedMessages, JSON.parse(message)];
        localStorage.setItem(`messages_${email}`, JSON.stringify(newMessages));
        setNotificationCount(newMessages.length);
    };

    const addSurvey = (message) => {
        const savedSurveys = JSON.parse(localStorage.getItem(`surveys_${email}`)) || [];
        const newSurveys = [...savedSurveys, JSON.parse(message)];
        localStorage.setItem(`surveys_${email}`, JSON.stringify(newSurveys));
        setSurveyCount(newSurveys.length);
    };

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
            case '/survey-detail':
                return '설문조사';
            case '/notification':
                return '알림';
            case '/dietselection':
                return '식단 선택';
            case '/menuselection':
                return '메뉴 선택';
            case '/dietpaymentmain':
                return '식단 결제';
            case '/dietpayment':
                return '식단 결제';
            case '/admin':
                return '관리자 페이지';
            case '/dietpaymentverification':
                return '식단 결제';
            case '/dietpaymentcomplete':
                return '결제 완료'
            default:
                return 'Main Page';
        }
    };

    const isMainPage = location.pathname === '/';
    const isVerificationPage = location.pathname === '/dietpaymentverification';

    return (
        <header>
            <div className="contents">
                {!isMainPage && (
                    <div className="otherPageHeader">
                        {!isVerificationPage && (
                            <FontAwesomeIcon icon={faArrowLeft} onClick={handleBackClick} className="faArrowLeft" style={{ cursor: 'pointer' }} />
                        )}
                        <span className="pageTitle" style={{ fontSize: 20, fontWeight: 600 }}>{getPageTitle()}</span>
                    </div>
                )}
                {isMainPage && (
                    <>
                        <div className="header_contents">
                            {isLoggedIn ? (
                                <span>{userName}님</span>
                            ) : (
                                <span onClick={handleLoginClick} className="Login-btn">로그인</span>
                            )}
                        </div>
                        <div className="header_contents">
                            <img src={logo} className="logoImage" alt="logo" />
                        </div>
                        <div className="header_contents">
                            <ul>
                                <li style={{ position: 'relative' }}>
                                    <FontAwesomeIcon icon={faBell} size="2x" onClick={() => navigate('/notification')} />
                                    {notificationCount > 0 && (
                                        <span className="notification-badge">{notificationCount}</span>
                                    )}
                                </li>
                                <li style={{ position: 'relative' }}>
                                    <FontAwesomeIcon icon={faFileLines} size="2x" onClick={() => navigate('/survey')} />
                                    {surveyCount > 0 && (
                                        <span className="notification-badge">{surveyCount}</span>
                                    )}
                                </li>
                            </ul>
                        </div>
                    </>
                )}
            </div>
        </header>
    );
};

export default Header;
