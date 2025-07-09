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
    const handleAccountRecoveryClick = () => {
        navigate('/account-recovery')
    };

    const changeEmail = (e) => {
        setEmail(e.target.value);
    };

    const changePassword = (e) => {
        setPassword(e.target.value);
    };

    const handleLogin = async (event) => {
        event.preventDefault();

        // 입력값 검증
        if (!email.trim() || !password.trim()) {
            alert('아이디와 비밀번호를 모두 입력해주세요.');
            return;
        }

        try {
            const response = await fetch('https://nimn.store/api/users/login', {
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


                try {
                    const userResponse = await fetch('https://nimn.store/api/users', {
                        method: 'GET',
                        credentials: 'include'
                    });

                    if (userResponse.ok) {
                        const userData = await userResponse.json();

                        if (onLoginSuccess) {
                            onLoginSuccess(userData);
                        }
                    }
                } catch (error) {
                    console.error('사용자 정보 조회 실패:', error);
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
            console.error("Fetch error: ", error);
            alert("로그인 중 오류가 발생했습니다.");
        }
    };

    return (
        <div className="login-container">
            <div className="login-inner">
                <img src={logo} className="logoImage-login" alt="logo" />

                <form onSubmit={handleLogin}>
                    <input
                        type="text"
                        id="username"
                        value={email}
                        className="login-email"
                        placeholder="아이디"
                        onChange={changeEmail}
                    />
                    <input
                        type="password"
                        id="password"
                        value={password}
                        className="login-password"
                        placeholder="비밀번호"
                        onChange={changePassword}
                    />

                    <button type="submit" className="login-btn">로그인</button>
                </form>

                <button className="login-btn" onClick={handleLogin}>로그인</button>
                <div className="login-options">
                    <span
                        className="account-recovery-link"
                        onClick={handleAccountRecoveryClick}
                    >
                        계정 찾기
                    </span>
                </div>
                <p className="signup-link">
                    아직 회원이 아니신가요? <span onClick={handleSignupClick}>회원가입</span>
                </p>
            </div>
        </div>
    );
}

export default Login;
