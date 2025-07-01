// src/pages/Login.jsx
import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import '../CSS/Login.css';
import Cookies from 'js-cookie';
import logo from '../images/logo.png';

function Login({ onLoginSuccess }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSignupClick = () => {
        navigate('/signup');
    };

    const changeEmail = (e) => {
        setEmail(e.target.value);
    };

    const changePassword = (e) => {
        setPassword(e.target.value);
    };

    const handleLogin = async (event) => {
        event.preventDefault();
    
        try {
            const response = await fetch('http://nimn.store/api/users/login', {
                method: "POST",
                credentials: "include", 
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
            });
    
            const result = await response.json();
    
            if (response.status === 200) {
                const token = Cookies.get('token');
                const payload = JSON.parse(atob(token.split('.')[1]));
                
                // 사용자 정보 조회 후 상태 업데이트
                try {
                    const userResponse = await fetch('http://nimn.store/api/users', {
                        method: 'GET',
                        credentials: 'include'
                    });
                    
                    if (userResponse.ok) {
                        const userData = await userResponse.json();
                        // 부모 컴포넌트에 로그인 성공 알림
                        if (onLoginSuccess) {
                            onLoginSuccess(userData);
                        }
                    }
                } catch (error) {
                }
            
                if (payload.role === "ROLE_ADMIN") {
                    navigate('/admin');
                } else if (payload.role === "ROLE_USER") {
                    navigate('/');
                }
            } else {
                alert("로그인 실패: " + result.message);
            }
        } catch (error) {
            alert("로그인 중 오류가 발생했습니다.");
        }
    };

    return (
        <div className="login-container">
            <div className="login-inner">
                <img src={logo} className="logoImage-login" alt="logo" />

                <input type="text" id="username" value={email} className="login-email" placeholder="아이디" onChange={changeEmail} />
                <input type="password" id="password" value={password} className="login-password" placeholder="비밀번호" onChange={changePassword} />

                <button className="login-btn" onClick={handleLogin}>로그인</button>
                <div className="login-options">
                    {/* <div>
                        <a href="/find-id">아이디 찾기</a> | <a href="/find-password">비밀번호 찾기</a>
                    </div> */}
                </div>
                <p className="signup-link">
                    아직 회원이 아니신가요? <span onClick={handleSignupClick}>회원가입</span>
                </p>
            </div>
        </div>
    );
}

export default Login;
