import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Avatar } from 'antd';
import '../CSS/Mypage.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faReceipt, faSignOutAlt, faUserMinus, faChevronRight, faMapMarkerAlt, faPhone } from "@fortawesome/free-solid-svg-icons";

function MyPage(){

const [Image] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")

const [userName, setUserName] = useState(''); 
const [address, setAddress] = useState({ roadAddress: '', detailAddress: '' });
const [phoneNumber, setphoneNumber] = useState(''); 
const [showPopup, setShowPopup] = useState(false);
const navigate = useNavigate();

    // 로그아웃 함수
    const handleLogout = () => {
        localStorage.removeItem("email");
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("isPay");
        localStorage.removeItem("checkMealLoad");
        localStorage.removeItem("Meal");
        localStorage.removeItem("token");
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
            
            const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json", 
                    "Authorization": token,
                }
            });
      
            const result = await response.json();
      
            if (response.status === 200) {
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
    }, [])

    return(
        <>
        <div className="mypage_container">
     

            {/* 프로필 카드 */}
            <div className="mypage_profile_card">
                <Avatar 
                    src={Image || ""} 
                    className="mypage_avatar"
                    size={64} 
                />
                <div className="mypage_profile_text">
                    <h2 className="mypage_name">{userName}</h2>
                    <p className="mypage_greeting">마이페이지에 오신 것을 환영합니다</p>
                </div>
            </div>

            {/* 계정정보 섹션 */}
            <div className="mypage_section">
                <h3 className="mypage_section_title">계정 정보</h3>
                <div className="mypage_info_list">
                    <div className="mypage_info_item">
                        <div className="mypage_info_icon_wrapper">
                            <FontAwesomeIcon icon={faMapMarkerAlt} className="mypage_info_icon"/>
                        </div>
                        <div className="mypage_info_content">
                            <span className="mypage_info_label">주소</span>
                            <span className="mypage_info_value">
                                {address.roadAddress} {address.detailAddress}
                            </span>
                        </div>
                    </div>
                    <div className="mypage_info_item">
                        <div className="mypage_info_icon_wrapper">
                            <FontAwesomeIcon icon={faPhone} className="mypage_info_icon"/>
                        </div>
                        <div className="mypage_info_content">
                            <span className="mypage_info_label">연락처</span>
                            <span className="mypage_info_value">{phoneNumber}</span>
                        </div>
                    </div>
                </div>
            </div>

            {/* 서비스 섹션 */}
            <div className="mypage_section">
                <h3 className="mypage_section_title">서비스</h3>
                <div className="mypage_menu_list">
                    <button className="mypage_menu_item" onClick={() => navigate('/userinfoupdate')}>
                        <div className="mypage_menu_content">
                            <div className="mypage_menu_icon_wrapper">
                                <FontAwesomeIcon icon={faPenToSquare} className="mypage_menu_icon"/>
                            </div>
                            <div className="mypage_menu_text">
                                <span className="mypage_menu_title">내 정보 수정</span>
                                <span className="mypage_menu_subtitle">개인정보 및 계정 설정</span>
                            </div>
                        </div>
                        <FontAwesomeIcon icon={faChevronRight} className="mypage_menu_arrow"/>
                    </button>
                    
                    <button className="mypage_menu_item" onClick={() => navigate('/orderlist')}>
                        <div className="mypage_menu_content">
                            <div className="mypage_menu_icon_wrapper">
                                <FontAwesomeIcon icon={faReceipt} className="mypage_menu_icon"/>
                            </div>
                            <div className="mypage_menu_text">
                                <span className="mypage_menu_title">주문 내역</span>
                                <span className="mypage_menu_subtitle">주문 및 배송 현황 확인</span>
                            </div>
                        </div>
                        <FontAwesomeIcon icon={faChevronRight} className="mypage_menu_arrow"/>
                    </button>
                </div>
            </div>

            {/* 계정 관리 섹션 */}
            <div className="mypage_section">
                <h3 className="mypage_section_title">계정 관리</h3>
                <div className="mypage_action_list">
                    <button className="mypage_action_item" onClick={handleLogout}>
                        <FontAwesomeIcon icon={faSignOutAlt} className="mypage_action_icon"/>
                        <span className="mypage_action_text">로그아웃</span>
                    </button>
                    
                    <button className="mypage_action_item mypage_danger" onClick={handleUnsubscribeClick}>
                        <FontAwesomeIcon icon={faUserMinus} className="mypage_action_icon"/>
                        <span className="mypage_action_text">회원탈퇴</span>
                    </button>
                </div>
            </div>
        </div>

        {/* 팝업 모달 */}
        {showPopup && (
            <div className="mypage_modal_overlay">
                <div className="mypage_modal">
                    <div className="mypage_modal_content">
                        <h3 className="mypage_modal_title">회원탈퇴</h3>
                        <p className="mypage_modal_message">정말로 탈퇴하시겠습니까?</p>
                        <p className="mypage_modal_warning">탈퇴 시 모든 정보가 영구적으로 삭제됩니다.</p>
                    </div>
                    <div className="mypage_modal_buttons">
                        <button className="mypage_modal_button mypage_modal_cancel" onClick={handleCancelUnsubscribe}>
                            취소
                        </button>
                        <button className="mypage_modal_button mypage_modal_confirm" onClick={handleConfirmUnsubscribe}>
                            탈퇴하기
                        </button>
                    </div>
                </div>
            </div>
        )}
        </>
    );
}

export default MyPage;