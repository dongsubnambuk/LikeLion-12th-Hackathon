import React, { useState, useEffect } from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png';
import { useNavigate, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faFileLines } from '@fortawesome/free-regular-svg-icons';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('');
    const navigate = useNavigate();
    const location = useLocation();

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
                    const response = await fetch(`http://3.37.64.39:8000/users?email=${email}`, {
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
    }, []);

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handleLogoutClick = () => {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        localStorage.removeItem('email');
        setIsLoggedIn(false);
        setUserName('');
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
            case '/dietpaymentverification':
                return '식단 결제';
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
                        <FontAwesomeIcon icon={faArrowLeft} onClick={handleBackClick} className="faArrowLeft" style={{ cursor: 'pointer' }} />
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
                                <li>
                                    <FontAwesomeIcon icon={faBell} size="2x" onClick={() => handleIconClick('/notification')} />
                                </li>
                                <li>
                                    <FontAwesomeIcon icon={faFileLines} size="2x" onClick={() => handleIconClick('/survey')} />
                                    <FontAwesomeIcon icon={faFileLines} size="2x" onClick={() => navigate('/survey')} />
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
