import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Avatar } from 'antd';
import '../CSS/Mypage.css';
import Cookies from 'js-cookie';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faReceipt, faSignOutAlt, faUserMinus, faChevronRight, faMapMarkerAlt, faPhone } from "@fortawesome/free-solid-svg-icons";

function MyPage(){

const [Image] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")

const [userName, setUserName] = useState(''); 
const [address, setAddress] = useState({ roadAddress: '', detailAddress: '' });
const [phoneNumber, setphoneNumber] = useState(''); 
const navigate = useNavigate();

    // 로그아웃 함수
    const handleLogout = async () => {
        try {
            const response = await fetch('http://nimn.store/api/users/logout', {
                method: "POST",
                credentials: 'include', // 쿠키에 있는 토큰 자동 전송
            });
            
            if (response.ok) {
                navigate("/");
            } else {
                try {
                    const result = await response.json();
                    if (result.message === "토큰소멸") {
                        alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                        navigate('/login');
                        return;
                    }
                } catch (e) {
                    console.error("로그아웃 응답 파싱 실패");
                }
                
                console.error("로그아웃 실패");
                navigate("/");
            }
        } catch (error) {
            console.error("로그아웃 오류:", error);
            navigate("/");
        }
    };

   

    useEffect(() => {
        const handleget = async () => {
            try {
                const response = await fetch('http://nimn.store/api/users', {
                    method: "GET",
                    credentials: 'include',
                });

                console.log(Cookies.get('token')); 
          
                if (response.status === 200) {
                    const result = await response.json();
                    
                    // 토큰 만료 체크
                    if (result.message === "토큰소멸") {
                        alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                        navigate('/login');
                        return;
                    }
                    
                    setUserName(result.name);
                    setphoneNumber(result.phoneNumber)
                    setAddress({
                        roadAddress: result.roadAddress,
                        detailAddress: result.detailAddress,
                    });
                } else {
                    console.log("사용자 정보 조회 실패", response.status);
                    
                    try {
                        const result = await response.json();
                        
                        // 토큰 만료 체크
                        if (result.message === "토큰소멸") {
                            alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                            navigate('/login');
                            return;
                        }
                        
                        if (response.status === 403) {
                            alert("접근 권한이 없습니다. 다시 로그인해주세요.");
                        } else {
                            alert("사용자 정보 조회 실패: " + result.message);
                        }
                    } catch (e) {
                        alert("사용자 정보 조회 실패");
                    }
                }
            } catch (error) {
                console.error("사용자 정보 조회 오류:", error);
                alert("사용자 정보 조회 중 오류가 발생했습니다.");
            }
        };
        handleget();
    }, [navigate])

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
                                <span className="mypage_menu_subtitle">주문 내역 전체 보기</span>
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
                </div>
            </div>
        </div>
        </>
    );
}

export default MyPage;