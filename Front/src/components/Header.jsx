import React, { useState, useEffect } from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png';
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faFileLines } from '@fortawesome/free-regular-svg-icons';

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('서동섭');
    const navigate = useNavigate(); // useNavigate 훅을 사용하여 페이지 이동

    useEffect(() => {
        // 여기에 로그인 상태와 사용자 이름을 가져오는 로직을 추가하세요.
        // 예를 들어, 로컬 스토리지나 API 요청을 통해 상태를 확인할 수 있습니다.
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setIsLoggedIn(true);
            setUserName(user.name);
        }
    }, []);

    const handleLoginClick = () => {
        // 로그인 페이지로 이동합니다.
        navigate('/login');
    };

    return (
        <header className="header">
            <div className="contents">
                <div className="header_contents">
                    {/* 사용자가 로그인되어 있으면 이름을 표시하고, 아니면 "로그인"을 표시합니다 */}
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
            </div>
        </header>
    );
};

export default Header;
