import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import '../CSS/Signup.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import Modal from "react-modal";
import DaumPostcode from "react-daum-postcode";

function Signup(){
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [userName, setUserName] = useState("");
    const [phoneNumber, setphoneNumber] = useState("");
    const [zipCode, setZipcode] = useState("");
    const [roadAddress, setRoadAddress] = useState("");
    const [detailAddress, setDetailAddress] = useState("");
    const [isOpen, setIsOpen] = useState(false);
    const [error, setError] = useState("");
    const [emailError, setEmailError] = useState("");
    const [emailSuccess, setEmailSuccess] = useState("");
    const [isEmailChecked, setIsEmailChecked] = useState(false);
    const [isEmailChecking, setIsEmailChecking] = useState(false);
    const navigate = useNavigate();

    const completeHandler = (data) => {
        setZipcode(data.zonecode);
        setRoadAddress(data.roadAddress);
        setIsOpen(false);
    };

    // Modal 스타일
    const SignupCustomStyles = {
        overlay: {
            backgroundColor: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
        },
        content: {
            left: "0",
            margin: "auto",
            width: "90%",
            maxWidth: "500px",
            height: "80%",
            padding: "0",
            overflow: "hidden",
            display: "flex",
            flexDirection: "column",
            borderRadius: "12px",
            border: "none",
            boxShadow: "0 10px 40px rgba(0,0,0,0.2)",
        },
    };

    // 검색 클릭
    const toggle = () => {
        setIsOpen(!isOpen);
    };

    // 상세 주소검색 event
    const changeHandler = (e) => {
        setDetailAddress(e.target.value);
    };

    // 이메일 형식 검증
    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    // 이메일 중복 검사(수정 필요)
    const handleEmailCheck = async () => {
        if (!email) {
            setEmailError("이메일을 입력해주세요.");
            return;
        }

        if (!validateEmail(email)) {
            setEmailError("올바른 이메일 형식을 입력해주세요.");
            return;
        }

        setIsEmailChecking(true);
        setEmailError("");
        setEmailSuccess("");

        try {
            const response = await fetch(`http://3.37.64.39:8000/api/users/check-email`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: email,
                }),
            });

            const result = await response.json();

            if (response.status === 200) {
                // 사용 가능한 이메일
                setEmailSuccess("사용 가능한 이메일입니다.");
                setIsEmailChecked(true);
            } else if (response.status === 409) {
                // 이미 사용 중인 이메일
                setEmailError("이미 사용 중인 이메일입니다.");
                setIsEmailChecked(false);
            } else {
                setEmailError("이메일 확인 중 오류가 발생했습니다.");
                setIsEmailChecked(false);
            }
        } catch (error) {
            console.error("Email check error: ", error);
            setEmailError("이메일 확인 중 오류가 발생했습니다.");
            setIsEmailChecked(false);
        } finally {
            setIsEmailChecking(false);
        }
    };

    // 이메일 변경 시 중복검사 상태 초기화
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        setIsEmailChecked(false);
        setEmailError("");
        setEmailSuccess("");
    };

    // 비밀번호 유효성 검사
    const validatePasswords = (password, confirmPassword) => {
        if (password.length < 8) {
            setError("비밀번호는 8자 이상이어야 합니다.");
            return false;
        }
        if (password !== confirmPassword) {
            setError("비밀번호가 일치하지 않습니다.");
            return false;
        }
        setError("");
        return true;
    };

    // 전화번호 형식 자동 변환
    const formatPhoneNumber = (value) => {
        const phoneNumber = value.replace(/[^\d]/g, '');
        const phoneNumberLength = phoneNumber.length;
        
        if (phoneNumberLength < 4) return phoneNumber;
        if (phoneNumberLength < 8) {
            return `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3)}`;
        }
        return `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3, 7)}-${phoneNumber.slice(7, 11)}`;
    };

    const handlePhoneNumberChange = (e) => {
        const formattedPhoneNumber = formatPhoneNumber(e.target.value);
        setphoneNumber(formattedPhoneNumber);
    };

    // 폼 유효성 검사
    const isFormValid = () => {
        return (
            isEmailChecked &&
            password &&
            confirmPassword &&
            password === confirmPassword &&
            password.length >= 8 &&
            userName &&
            phoneNumber &&
            roadAddress &&
            detailAddress
        );
    };

    //회원가입 fetch
    const handleSignup = async (event) => {
        event.preventDefault();

        if (!isEmailChecked) {
            alert("이메일 중복확인을 해주세요.");
            return;
        }

        if (!validatePasswords(password, confirmPassword)) {
            return;
        }

        if (!isFormValid()) {
            alert("모든 필드를 올바르게 입력해주세요.");
            return;
        }

        try {
            const response = await fetch('http://3.37.64.39:8000/api/users/signup', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                    name: userName,
                    phoneNumber: phoneNumber.replace(/-/g, ''), // 하이픈 제거
                    roadAddress: roadAddress,
                    detailAddress: detailAddress,
                }),
            });

            const result = await response.json();

            if (response.status === 201) {
                console.log("회원가입 성공");
                alert("회원가입이 완료되었습니다!");
                navigate('/login');
            } else {
                console.log("회원가입 실패");
                alert("회원가입 실패: " + result.message);
            }
        } catch (error) {
            console.error("Signup error: ", error);
            alert("회원가입 중 오류가 발생했습니다.");
        }
    };

    return(
        <>
            <Header/>
            <div className="signup_inner">
                <div className="signup_form_group">
                    <label htmlFor="email">이메일</label>
                    <div className="signup_email_check_group">
                        <div className="signup_email_input_wrapper">
                            <input
                                type="email"
                                id="email"
                                value={email}
                                className={`signup_email ${isEmailChecked ? 'signup_valid' : emailError ? 'signup_invalid' : ''}`}
                                placeholder="이메일을 입력해주세요"
                                onChange={handleEmailChange}
                            />
                        </div>
                        <button 
                            type="button"
                            className="signup_email_check_btn"
                            onClick={handleEmailCheck}
                            disabled={isEmailChecking || !email}
                        >
                            {isEmailChecking ? "확인중..." : "중복확인"}
                        </button>
                    </div>
                    {emailError && <div className="signup_error_message">{emailError}</div>}
                    {emailSuccess && <div className="signup_success_message">{emailSuccess}</div>}
                </div>

                <div className="signup_form_group">
                    <label htmlFor="password">비밀번호</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        className={`signup_password ${password.length >= 8 ? 'signup_valid' : password ? 'signup_invalid' : ''}`}
                        placeholder="비밀번호를 입력해주세요 (8자 이상)"
                        onChange={(e) => {
                            setPassword(e.target.value);
                            validatePasswords(e.target.value, confirmPassword);
                        }}
                    />
                </div>

                <div className="signup_form_group">
                    <label htmlFor="confirm-password">비밀번호 확인</label>
                    <input
                        type="password"
                        id="confirm-password"
                        value={confirmPassword}
                        className={`${confirmPassword && password === confirmPassword ? 'signup_valid' : confirmPassword ? 'signup_invalid' : ''}`}
                        placeholder="비밀번호를 다시 입력해 주세요"
                        onChange={(e) => {
                            setConfirmPassword(e.target.value);
                            validatePasswords(password, e.target.value);
                        }}
                    />
                    {error && <div className="signup_error_message">{error}</div>}
                </div>

                <div className="signup_form_group">
                    <label htmlFor="username">이름</label>
                    <input
                        type="text"
                        id="username"
                        value={userName}
                        className={userName ? 'signup_valid' : ''}
                        placeholder="이름을 입력해주세요"
                        onChange={(e) => setUserName(e.target.value)}
                    />
                </div>

                <div className="signup_form_group">
                    <label htmlFor="phonenumber">연락처</label>
                    <input
                        type="text"
                        id="phonenumber"
                        value={phoneNumber}
                        className={phoneNumber.length >= 13 ? 'signup_valid' : phoneNumber ? 'signup_invalid' : ''}
                        placeholder="010-1234-5678"
                        maxLength="13"
                        onChange={handlePhoneNumberChange}
                    />
                </div>

                <div className="signup_form_group">
                    <div className="signup_address">
                        <label htmlFor="address">주소</label>
                        <div className="signup_address_serch">
                            <input 
                                value={zipCode} 
                                readOnly 
                                placeholder="우편번호"
                                className={zipCode ? 'signup_valid' : ''}
                            />
                            <button type="button" onClick={toggle}>주소 찾기</button>
                        </div>
                        <div className="signup_address_detail">
                            <input 
                                value={roadAddress} 
                                readOnly 
                                placeholder="도로명 주소"
                                className={roadAddress ? 'signup_valid' : ''}
                            />
                            <Modal isOpen={isOpen} ariaHideApp={false} style={SignupCustomStyles}>
                                <div style={{ 
                                    display: 'flex', 
                                    justifyContent: 'center', 
                                    padding: '15px',
                                    borderBottom: '1px solid #e5e7eb'
                                }}>
                                    <button 
                                        onClick={toggle} 
                                        style={{ 
                                            padding: '8px 16px', 
                                            fontSize: '14px',
                                            background: '#6b7280',
                                            color: 'white',
                                            border: 'none',
                                            borderRadius: '8px',
                                            cursor: 'pointer'
                                        }}
                                    >
                                        닫기
                                    </button>
                                </div>
                                <DaumPostcode onComplete={completeHandler} height="100%" />
                            </Modal>
                            <input
                                type="text"
                                onChange={changeHandler}
                                value={detailAddress}
                                placeholder="상세주소"
                                className={detailAddress ? 'signup_valid' : ''}
                            />
                        </div>
                    </div>
                </div>

                <button 
                    className="signup_btn" 
                    onClick={handleSignup}
                    disabled={!isFormValid()}
                >
                    회원가입
                </button>
            </div>
            <BottomNav/>
        </>
    );
}

export default Signup;