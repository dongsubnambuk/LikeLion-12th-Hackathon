import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft} from '@fortawesome/free-solid-svg-icons';
import '../CSS/Signup.css';
import BottomNav from '../components/BottomNav';
import Modal from "react-modal"; // 추가
import DaumPostcode from "react-daum-postcode";

function Signup(){
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [userName, setUserName] = useState("");
    const [phoneNumber, setphoneNumber] = useState("");
    const [zipCode, setZipcode] = useState("");
    const [roadAddress, setRoadAddress] = useState("");
    const [detailAddress, setDetailAddress] = useState("");    // 추가
    const [isOpen, setIsOpen] = useState(false); // 추가
    const [error, setError] = useState("");

    const completeHandler = (data) => {
        setZipcode(data.zonecode);
        setRoadAddress(data.roadAddress);
        setIsOpen(false); // 추가
    };

        // Modal 스타일
        const customStyles = {
            overlay: {
                backgroundColor: "rgba(0,0,0,0.5)",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
            },
            content: {
                left: "0",
                margin: "auto",
                width: "100%",
                height: "80%",
                padding: "0",
                overflow: "hidden",
                display: "flex",
                flexDirection: "column",
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
    
     
        const clickHandler = () => {
            if (detailAddress === "") {
                alert("상세주소를 입력해주세요.");
            } else {
                console.log(zipCode, roadAddress, detailAddress);
            }
        };
    
    const navigate = useNavigate();

    const handleBack = () => {
        navigate(-1); //뒤로가기
      };
      const validatePasswords = (password, confirmPassword) => {
        if (password !== confirmPassword) {
            setError("비밀번호가 일치하지 않습니다.");
        } else {
            setError("");
        }
    };

    return(
        <>
            <div className="signup-topbar">
                <FontAwesomeIcon icon={faArrowLeft} onClick={handleBack} className="back-btn"/>
                <h2>회원가입</h2>
            </div>

            <div className="signup-inner">
            <div className="form-group">
                <label htmlFor="email">아이디</label>
                <input
                    type="text"
                    id="email"
                    value={email}
                    className="signup-email"
                    placeholder="아이디를 입력해주세요"
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>

            <div className="form-group">
                <label htmlFor="password">비밀번호</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    className="signup-password"
                    placeholder="비밀번호를 입력해주세요"
                    onChange={(e) => {
                        setPassword(e.target.value);
                        validatePasswords(e.target.value, confirmPassword);
                    }}
                />
            </div>

            <div className="form-group">
                <label htmlFor="confirm-password">비밀번호 확인</label>
                <input
                    type="password"
                    id="confirm-password"
                    value={confirmPassword}
                    placeholder="비밀번호를 다시 입력해 주세요"
                    onChange={(e) => {
                        setConfirmPassword(e.target.value);
                        validatePasswords(password, e.target.value);
                    }}
                />
                    {error && <div className="error-message">{error}</div>} {/* 에러 메시지 표시 */}
            </div>

            <div className="form-group">
                <label htmlFor="username">이름</label>
                <input
                    type="text"
                    id="username"
                    value={userName}
                    placeholder="이름을 입력해주세요."
                    onChange={(e) => setUserName(e.target.value)}
                />
             
            </div>

            <div className="form-group">
                <label htmlFor="phonenumber">연락처</label>
                <input
                    type="text"
                    id="phonenumber"
                    value={phoneNumber}
                    placeholder="예)01012345678"
                    onChange={(e) => setphoneNumber(e.target.value)}
                />
            </div>
            <div className="form-group">
                <div className="address">
               
                    <label htmlFor="address">주소</label>
                    <div className="address-serch">
                        <input value={zipCode} readOnly placeholder="우편번호" />
                        <button onClick={toggle}>주소 찾기</button>
                    </div>
                    <div className="address-detail">
                        <input value={roadAddress} readOnly placeholder="도로명 주소" />
                        <Modal isOpen={isOpen} ariaHideApp={false} style={customStyles}>
                        <button onClick={toggle} style={{ alignSelf: 'center', padding: '10px 20px', fontSize: '16px', marginTop: '20px' }}>닫기</button> {/* 닫기 버튼 추가 및 스타일링 */}
                            <DaumPostcode onComplete={completeHandler} height="100%" />
                        </Modal>
                        <input
                            type="text"
                            onChange={changeHandler}
                            value={detailAddress}
                            placeholder="상세주소"
                        />
                    </div>
                    
                </div>
            </div>
            <button className="signup-btn">회원가입</button>
        </div>
     
            <BottomNav/>
        </>
    );
}

export default Signup;