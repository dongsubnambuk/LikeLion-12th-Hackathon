import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import '../CSS/Login.css';
import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode';
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

    // JWT 토큰 디코딩 함수 (jwt-decode 라이브러리 사용)
    const decodeJWT = (token) => {
        try {
            const decodedToken = jwtDecode(token);
            return decodedToken;
        } catch (error) {
            console.error('JWT 디코딩 실패:', error);
            return null;
        }
    };

    // 역할에 따른 라우팅 함수
    const navigateByRole = (token) => {
        const decodedToken = decodeJWT(token);
        
        if (!decodedToken) {
            console.error('토큰 디코딩 실패');
            alert('로그인 처리 중 오류가 발생했습니다.');
            return;
        }

        console.log('디코딩된 토큰:', decodedToken);
        
        // 토큰에서 역할 정보 추출 
        const role = decodedToken.role || decodedToken.roles || decodedToken.authorities;
        
        console.log('사용자 역할:', role);


        if (role === 'ROLE_ADMIN' || (Array.isArray(role) && role.includes('ROLE_ADMIN'))) {
            console.log('관리자로 로그인, /admin으로 이동');
            navigate('/admin');
        } else if (role === 'ROLE_USER' || (Array.isArray(role) && role.includes('ROLE_USER'))) {
            console.log('일반 사용자로 로그인, /로 이동');
            navigate('/');
        } else {
            console.log('알 수 없는 역할, 기본 페이지로 이동');
            navigate('/');
        }
    };

    // login fetch 함수
    const handleLogin = async (event) => {
        event.preventDefault();
    
        try {
            const response = await fetch('http://nimn.store/api/users/login', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include", 
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
            });
    
            const result = await response.json();
    
            if (response.status === 200) {
                console.log("로그인 성공!");
                console.log("서버에서 쿠키 자동 저장됨");
                
                // 쿠키에서 토큰 가져오기
                const token = Cookies.get('token');
                console.log('token 쿠키 값:', token);
                
                if (token) {
                    // JWT 토큰 디코딩 후 역할에 따른 라우팅
                    navigateByRole(token);
                } else {
                    console.error('토큰을 찾을 수 없습니다.');
                    alert('로그인 처리 중 오류가 발생했습니다.');
                }
            } else {
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