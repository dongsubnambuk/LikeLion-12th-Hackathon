import React, { useState, useEffect } from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png';
import { useNavigate, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faFileLines } from '@fortawesome/free-regular-svg-icons';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('서동섭');
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setIsLoggedIn(true);
            setUserName(user.name);
        }
    }, []);

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handleBackClick = () => {
        navigate(-1);
    };

    const getPageTitle = () => {
        switch (location.pathname) {
            // 페이지 타이틀 추가
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
            default:
                return 'Main Page';
        }
    };

    const isMainPage = location.pathname === '/';

    return (
        <header>
            <div className="contents">
                {/* 메인 페이지가 아니면 뒤로가기 버튼과 타이틀 보임 */}
                {!isMainPage && (
                    <div className="otherPageHeader">
                        <FontAwesomeIcon icon={faArrowLeft}  onClick={handleBackClick} className="faArrowLeft" style={{cursor: 'pointer'}}/>
                        <span className="pageTitle" style={{fontSize: 20, fontWeight: 600}}>{getPageTitle()}</span>
                    </div>
                )}
                {isMainPage && (
                    <>
                        <div className="header_contents">
                            {isLoggedIn ? `${userName} 님` : <span onClick={handleLoginClick} className="Login-btn">로그인</span>}
                        </div>
                        <div className="header_contents">
                            <img src={logo} className="logoImage" alt="logo" />
                        </div>
                        <div className="header_contents">
                            <ul>
                                <li>
                                    <FontAwesomeIcon icon={faBell} size="2x" />
                                </li>
                                <li>
                                    <FontAwesomeIcon icon={faFileLines} size="2x" />
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
