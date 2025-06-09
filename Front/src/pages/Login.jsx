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



    const checkPayList = (payUserList) => {
        // 로컬 스토리지에 저장된 나의 이메일 불러오기
        const email = localStorage.getItem("email");

        // 배열 payList 안에 나의 이메일이 포함되는 지 확인
        let isNotPaidUser = false;

        //console.log("결제 유저 확인, 해당 이메일 : ", email)
        //console.log("결제 유저 확인 ", payUserList)

        for (let i = 0; i < payUserList.length; i++) {
            if (payUserList[i] === email) {
                isNotPaidUser = true;
                break;
            }
        }

        // 결제 여부에 따라 로컬 스토리지 값 설정
        if (!isNotPaidUser) {
            // 결제 했으면
            //console.log("결제 함");
            localStorage.setItem("isPay", true);
        }
        else {
            // 결제 안 했으면
            //console.log("결제 안 함");
            localStorage.setItem("isPay", false);
        }

    };


    // 식단 미결제 유저 리스트 불러오기
    const handleGetPay = async () => {
        const token = localStorage.getItem("token");
    
        try {
            const response = await fetch(`http://3.37.64.39:8000/api/payment/unpaid-users`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });
    
            const result = await response.json();
    
            if (response.status === 200) {
                checkPayList(result.email);
            } else {
                console.log("결제 정보 확인 실패: ", result.message);
                alert("결제 정보 확인 실패: " + result.message);
            }
        } catch (error) {
            console.error("Fetch error: ", error);
        }
    };


    // login fetch 함수
    const handleLogin = async (event) => {
        event.preventDefault();
    
        try {
            const response = await fetch('http://3.37.64.39:8000/api/users/login', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
            });
    
            const result = await response.json();
    
            if (response.status === 200) {
                localStorage.setItem("token", result.token);
                localStorage.setItem("email", result.email);
                localStorage.setItem("role", result.role);
                localStorage.setItem("isLoggedIn", "true");
    
                await handleGetPay(); // 결제 정보 확인 후 로컬 스토리지에 저장
    
                if (result.role === 'ROLE_ADMIN') {
                    navigate('/admin');
                } else {
                    navigate('/');
                }
            } else {
                console.log("로그인 실패");
                alert("로그인 실패: " + result.message);
            }
        } catch (error) {
            console.error("Fetch error: ", error);
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
