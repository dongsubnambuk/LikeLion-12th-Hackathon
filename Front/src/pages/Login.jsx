import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import '../CSS/Login.css';
import logo from '../images/logo.png';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSignupClick = () => {
        navigate('/signup'); // 회원가입 페이지로 이동
    };

    const changeEmail = (e) => {
        setEmail(e.target.value);
    };

    const changePassword = (e) => {
        setPassword(e.target.value);
    };


    //login fetch함수
    const handleLogin = async (event) => {
        event.preventDefault();

        const response = await fetch('https://your-server-url.com/login', { // 서버 URL을 실제 API 엔드포인트로 변경하세요
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: email,
                password: password,
            }),
        });

        const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환

        if (response.status === 200) { // 응답 status가 200 OK 일 경우
            // Store token in local storage
            localStorage.setItem("token", result.accessToken);  // 로그인 성공 시 보내주는 토큰 localStorage에 저장
            navigate('/'); // 로그인 성공 후 메인 페이지로 이동
        } else {
            console.log("로그인 실패");
            alert("로그인 실패: " + result.message);
        }
    };

    return (
        <>
            <Header />

            <div className="login-inner">
                <img src={logo} className="logoImage-login" alt="logo" />

                <input type="text" id="username" value={email} className="login-email" placeholder="아이디" onChange={changeEmail} />
                <input type="password" id="password" value={password} className="login-password" placeholder="비밀번호" onChange={changePassword} />

                <button className="login-btn" onClick={handleLogin}>로그인</button>
                <div className="login-options">
                    <label>
                        <input type="checkbox" /> 자동 로그인
                    </label>
                    <div>
                        <a href="/find-id">아이디 찾기</a> | <a href="/find-password">비밀번호 찾기</a>
                    </div>
                </div>
                <p className="signup-link">
                    아직 회원이 아니신가요? <span onClick={handleSignupClick}>회원가입</span>
                </p>
            </div>

            <BottomNav />
        </>
    );
}

export default Login;
