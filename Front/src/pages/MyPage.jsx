import React, { useState,useRef,useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { Avatar } from 'antd';
import '../CSS/Mypage.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faReceipt } from "@fortawesome/free-solid-svg-icons";

function MyPage(){

const [Image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")

const [userName, setUserName] = useState(''); // 하드코딩된 유저 이름
const [address, setAddress] = useState({ roadAddress: '', detailAddress: '' });
const [phoneNumber, setphoneNumber] = useState(''); // 하드코딩된 유저 이름
const [showPopup, setShowPopup] = useState(false);
const navigate = useNavigate();

    const handleImageSave = (newImage) => {
        setImage(newImage);
    };

    // 로그아웃 함수
    const handleLogout = () => {

    	localStorage.removeItem("token");
    	localStorage.removeItem("email");
    	navigate("/");
        
  	};

    const handleUnsubscribeClick = () => {
        setShowPopup(true);
    };

    const handleConfirmUnsubscribe = () => {
        setShowPopup(false);
        alert('탈퇴가 완료되었습니다.');
        navigate('/');
    };

    const handleCancelUnsubscribe = () => {
        setShowPopup(false);
    };

    useEffect(() => {
        const handleget = async () => {
            const token = localStorage.getItem("token");
            const email = localStorage.getItem("email");

      
          
            const response = await fetch(`http://3.37.64.39:8000/users?email=${email}`, { // 서버 URL을 실제 API 엔드포인트로 변경하세요
                method: "GET",
                headers: {
                    "Content-Type": "application/json", 
                    "Authorization": token,
                }
            });
      
            const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환
      
            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                // Store token in local storage
                
                setUserName(result.name);
                setphoneNumber(result.phoneNumber)
                setAddress({
                    roadAddress: result.roadAddress,
                    detailAddress: result.detailAddress,
                  });

            
      
            } else {
                console.log("로그인 실패");
                alert("로그인 실패: " + result.message);
            }
        };
        handleget();
    })

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
            <p>주소: {address.roadAddress} {address.detailAddress}</p> 
            <p>연락처: {phoneNumber}</p>
            <hr/>
        </div>

        <div className="mypage-pages">

            <div className="mypage-link" onClick={() => navigate('/userinfoupdate')}>
            <FontAwesomeIcon icon={faPenToSquare} size="2x"/>
                <span>내 정보 수정</span>
                <span className="arrow">&gt;</span>
            </div>
            
            <div className="mypage-link" onClick={() => navigate('/orderlist')}>
            <FontAwesomeIcon icon={faReceipt} size="2x" />
                <span>주문 내역 확인</span>
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
            <BottomNav />
        </>
    );
}

export default MyPage;
