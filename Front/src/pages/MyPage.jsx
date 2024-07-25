import React, { useState,useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
import {Avatar} from 'antd';
import '../CSS/Mypage.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faReceipt ,faCalendarDays} from "@fortawesome/free-solid-svg-icons";

function MyPage(){

const [Image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")

const [userName, setUserName] = useState('서동섭'); // 하드코딩된 유저 이름
const [showPopup, setShowPopup] = useState(false);
const navigate = useNavigate();

const handleImageSave = (newImage) => {
    setImage(newImage);
};

    //로그아웃 함수
    const handleLogout = () => {


    	// sessionStorage.removeItem("token");
    	// sessionStorage.removeItem("email");
    	// sessionStorage.removeItem("role");
    	// 페이지 이동
    	navigate("/");
  	};

      const handleUnsubscribeClick = () => {
        setShowPopup(true);
    };

    const handleConfirmUnsubscribe = () => {
        setShowPopup(false);
        alert('탈퇴가 완료되었습니다.')
        navigate('/');
    };

    const handleCancelUnsubscribe = () => {
        setShowPopup(false);
    };


    return(
        <>
        <Header/>
        <div className="mypage-userinfo">
        <Avatar 
                src={Image || ""} // 기본 이미지 경로 또는 수정된 이미지
                style={{ margin: '20px' }} 
                size={90} 
            />

            <h2> 환영합니다! {`${userName} 님`}</h2> {/*back user db에서 이름 read*/}
        </div>
        
        <div className="mypage-info">
            <p>주소: 대구 달서구 계대동문로 3안길 13-5</p> {/*back user db에서 주소, 연락처 read*/}
            <p>연락처: 010- 1234 - 5678</p>
            <hr/>
        </div>

        <div className="mypage-pages">

            <div className="mypage-link" onClick={() => navigate('/userinfoupdate')}>
            <FontAwesomeIcon icon={faPenToSquare} size="2x"/>
                <span>내 정보 수정</span>
                <span className="arrow">&gt;</span>
            </div>
            
            <div className="mypage-link" onClick={() => navigate('/order-history')}>
            <FontAwesomeIcon icon={faReceipt} size="2x" />
                <span>주문 내역 확인</span>
                <span className="arrow">&gt;</span>
            </div>

            <div className="mypage-link" onClick={() => navigate('/subscription-status')}>
            <FontAwesomeIcon icon={faCalendarDays} size="2x"/>
                <span>구독 상태 확인</span>
                <span className="arrow">&gt;</span>
            </div>
              
        </div>
        <div className="mypage-link unsubscribe" onClick={handleUnsubscribeClick}>
            <span>회원탈퇴</span>
            <span className="arrow">&gt;</span>
        </div>
        {showPopup && (
                <div className="popup-overlay">
                    <div className="popup">
                        <h2>회원탈퇴</h2>
                        <p>정말로 탈퇴하시겠습니까?</p>
                        <div className="popup-buttons">

                        <button onClick={handleCancelUnsubscribe} className="popup-buttons-cancel">취소</button>
                        <button onClick={handleConfirmUnsubscribe} className="popup-buttons-ok">확인</button>

                        </div>
                    </div>
                </div>
            )}

        <div className="mypage-logout">
            <span onClick={handleLogout}>로그아웃</span>
        </div>
            <BottomNav/>
        </>
    );
}

export default MyPage;