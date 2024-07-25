import React, { useState,useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
import {Avatar, Flex} from 'antd';
import Modal from "react-modal"; // 추가
import DaumPostcode from "react-daum-postcode";
import '../CSS/UserInfoUpdate.css';

function UserInfoUpdate(){
    const [Image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
    const [File, setFile] = useState('')
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [phoneNumber, setphoneNumber] = useState("");
    const [zipCode, setZipcode] = useState("");
    const [roadAddress, setRoadAddress] = useState("");
    const [detailAddress, setDetailAddress] = useState(""); 
    const [isOpen, setIsOpen] = useState(false); 
    const [error, setError] = useState("");

    const completeHandler = (data) => {
        setZipcode(data.zonecode);
        setRoadAddress(data.roadAddress);
        setIsOpen(false); // 추가
    };

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

 

    const fileInput = useRef(null)
    const onChange = (e) => {
        if(e.target.files[0]){
                setFile(e.target.files[0])
            }else{ //업로드 취소할 시
                setImage("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
                return
            }
        //화면에 프로필 사진 표시
            const reader = new FileReader();
            reader.onload = () => {
                if(reader.readyState === 2){
                    setImage(reader.result)
                }
            }
            reader.readAsDataURL(e.target.files[0])
        }

        const validatePasswords = (password, confirmPassword) => {
            if (password !== confirmPassword) {
                setError("비밀번호가 일치하지 않습니다.");
            } else {
                setError("");
            }
        }

    return(
        <>
        <Header/>
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div className="user-image" style={{ textAlign: 'center' }}>
                <Avatar 
                    src={Image || "default-image-path.jpg"} // 기본 이미지 경로 또는 수정된 이미지
                    style={{ margin: '35px', cursor: 'pointer' }} 
                    size={90} 
                    onClick={() => { fileInput.current.click(); }}
                />
                <input 
                    type='file' 
                    style={{ display: 'none' }}
                    accept='image/jpg,image/png,image/jpeg' 
                    name='profile_img'
                    onChange={onChange}
                    ref={fileInput}
                />
            </div>
        </div>

        <div className="info-update">
            <div className="update-form-group">
                    <label htmlFor="phonenumber">연락처 수정</label>
                    <input
                        type="text"
                        id="phonenumber"
                        value={phoneNumber}
                        placeholder="예)01012345678"
                        onChange={(e) => setphoneNumber(e.target.value)}
                    />
                </div>
            <div className="update-form-group">
                    <label htmlFor="password">비밀번호 변경</label>
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

                <div className="update-form-group">
                    <label htmlFor="confirm-password">비밀번호 변경 - 재입력</label>
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

                <div className="update-form-group">
                    <div className="update-address">
                
                        <label htmlFor="address">주소 변경</label>
                        <div className="update-address-serch">
                            <input value={zipCode} readOnly placeholder="우편번호" />
                            <button onClick={toggle}>주소 찾기</button>
                        </div>
                        <div className="update-address-detail">
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
                <button className="update-btn">저장하기</button>
        </div>
      
        <BottomNav/>
        </>
    );
}

export default UserInfoUpdate;