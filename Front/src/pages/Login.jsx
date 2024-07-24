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
        let { value } = { ...e.target }
        setEmail(value)
    }
    const changePassword = (e) => {
        let { value } = { ...e.target }
        setPassword(value)
    }

    return(
        <>
        <Header/>

        <div className="login-inner">
        <img src={logo} className="logoImage-login" alt="logo"/>


        <input type="text" id="username" value={email} className="login-email" placeholder="아이디" onChange={changeEmail}/>
        <input type="password" id="password" value={password} className="login-password" placeholder="비밀번호" onChange={changePassword}/>
    
        <button className="login-btn">로그인</button>
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

        <BottomNav/>

        </>
    );
}

    export default Login;
