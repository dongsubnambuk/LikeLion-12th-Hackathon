import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import '../CSS/Login.css';
import Cookies from 'js-cookie';
import logo from '../images/logo.png';

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

    // login fetch 함수
    const handleLogin = async (event) => {
        event.preventDefault();
    
        try {
            const response = await fetch('http://nimn.store/api/users/login', {
                method: "POST",
                credentials: "include", 
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
            });
    
            const result = await response.json();
    
            if (response.status === 200) {
                const token = Cookies.get('token');
                console.log(token);
                const payload = JSON.parse(atob(token.split('.')[1]));
                console.log("로그인 성공!");
                console.log("서버에서 쿠키 자동 저장됨");
            
                if (payload.role === "ROLE_ADMIN") {
                    navigate('/admin');
                } else if (payload.role === "ROLE_USER") {
                    navigate('/');
                }
            }
             else {
                console.log("로그인 실패");
                alert("로그인 실패: " + result.message);
            }
        } catch (error) {
            console.error("Fetch error: ", error);
            alert("로그인 중 오류가 발생했습니다.");
        }
    };

    return (
        <>
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
        </>
    );
}

export default Login;