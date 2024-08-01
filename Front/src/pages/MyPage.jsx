import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import { Avatar } from 'antd';
import '../CSS/Mypage.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faReceipt } from "@fortawesome/free-solid-svg-icons";

function MyPage() {
    const [Image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
    const [userName, setUserName] = useState('서동섭'); // 초기 값은 빈 문자열로 수정해야됨
    const [showPopup, setShowPopup] = useState(false);
    const navigate = useNavigate();

    const handleImageSave = (newImage) => {
        setImage(newImage);
    };

    // 로그아웃 함수
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
        alert('탈퇴가 완료되었습니다.');
        navigate('/');
    };

    const handleCancelUnsubscribe = () => {
        setShowPopup(false);
    };

    useEffect(() => {
        // 임의의 데이터 저장
        const postData = async () => {
            try {
                const postResponse = await axios.post('http://15.165.192.29:8080/test/post', {
                    id: 222,
                    name: '홍길동',
                    email: 'abc@naver.com',
                    address: '대구',
                    age: 21
                });
                console.log('Post Response:', postResponse.data);
            } catch (error) {
                console.error('Error posting data:', error);
            }
        };

        // 사용자 이름 불러오기
        const getData = async () => {
            try {
                const getResponse = await axios.get('http://15.165.192.29:8080/test/get');
                console.log('Get Response:', getResponse.data);
                if (Array.isArray(getResponse.data) && getResponse.data.length > 0) {
                    // 리스트의 0번째 요소의 name을 userName에 설정
                    setUserName(getResponse.data[0].name);
                }
            } catch (error) {
                console.error('Error getting data:', error);
            }
        };

        //postData();
        //getData();
    }, []);

    return (
        <>
            <Header />
            <div className="mypage-userinfo">
                <Avatar 
                    src={Image || ""} // 기본 이미지 경로 또는 수정된 이미지
                    style={{ margin: '20px' }} 
                    size={90} 
                />
                <h2> 환영합니다! {`${userName} 님`}</h2> {/* userName을 백엔드에서 불러옴 */}
            </div>
            <div className="mypage-info">
                <p>주소: 대구 달서구 계대동문로 3안길 13-5</p> {/* back user db에서 주소, 연락처 read */}
                <p>연락처: 010-1234-5678</p>
                <hr />
            </div>
            <div className="mypage-pages">
                <div className="mypage-link" onClick={() => navigate('/userinfoupdate')}>
                    <FontAwesomeIcon icon={faPenToSquare} size="2x" />
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
