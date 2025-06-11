import React, { useState,useRef ,useEffect} from "react";
import { useNavigate } from 'react-router-dom';
import {Avatar} from 'antd';
import Modal from "react-modal"; // 추가
import DaumPostcode from "react-daum-postcode";
import '../CSS/UserInfoUpdate.css';
import logo from '../images/logo.png'; 

function UserInfoUpdate(){
    const [Image, setImage] = useState("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
    const [ setFile] = useState('')
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [phoneNumber, setphoneNumber] = useState("");
    const [zipCode, setZipcode] = useState("");
    const [roadAddress, setRoadAddress] = useState("");
    const [detailAddress, setDetailAddress] = useState(""); 
    const [isOpen, setIsOpen] = useState(false); 
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const completeHandler = (data) => {
        setZipcode(data.zonecode);
        setRoadAddress(data.roadAddress);
        setIsOpen(false); // 추가
    };

    const customStyles = {
        overlay: {
            backgroundColor: "rgba(0, 0, 0, 0.6)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
            backdropFilter: "blur(4px)",
            animation: "fadeIn 0.3s ease-out"
        },
        content: {
            position: "relative",
            top: "auto",
            left: "auto",
            right: "auto",
            bottom: "auto",
            margin: "20px",
            width: "90%",
            maxWidth: "600px",
            height: "80%",
            maxHeight: "700px",
            padding: "0",
            border: "none",
            borderRadius: "20px",
            boxShadow: "0 25px 50px rgba(0, 0, 0, 0.25), 0 0 0 1px rgba(255, 255, 255, 0.05)",
            overflow: "hidden",
            display: "flex",
            flexDirection: "column",
            background: "linear-gradient(145deg, #ffffff 0%, #f8f9fa 100%)",
            animation: "slideUpIn 0.4s cubic-bezier(0.34, 1.56, 0.64, 1)"
        }
    };

    // 검색 클릭
    const toggle = () => {
        setIsOpen(!isOpen);
    };
;

 

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

       //정보 수정fetch
       const handlePUT = async () => {

        const token = localStorage.getItem("token");
        const email = localStorage.getItem("email");

        const response = await fetch('http://3.37.64.39:8000/api/users', {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token,
            },
            body: JSON.stringify({
                email: email,
                password: password ? password : null,
                phoneNumber: phoneNumber,
                roadAddress: roadAddress,
                detailAddress: detailAddress,
            }),
        });

        const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환
        

        if (response.status === 200) { // 응답 status가 201 OK 일 경우
            console.log(result);
            console.log("회원정보 수정 성공");
            alert("회원정보 수정 성공");
            navigate('/'); 
        } else {
            console.log("회원정보 수정 실패");
            alert("회원정보 수정 실패: " + result.message);
        }
    };
    
    useEffect(() => {
        const handleget = async () => {
            const token = localStorage.getItem("token");
            const email = localStorage.getItem("email");

      
          
            const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, { // 서버 URL을 실제 API 엔드포인트로 변경하세요
                method: "GET",
                headers: {
                    "Content-Type": "application/json", 
                    "Authorization": token,
                }
            });
      
            const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환
      
            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                // Store token in local storage
                
            
                setphoneNumber(result.phoneNumber)
                setRoadAddress(result.roadAddress);
                setDetailAddress(result.detailAddress);

            
      
            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };
        handleget();
    }, []);



    return(
        <>
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

        <div className="userinfoupdate_info-update">
            <div className="userinfoupdate_update-form-group">
                    <label htmlFor="phonenumber" className="userinfoupdate_update-form-group_label">연락처 수정</label>
                    <input
                type="text"
                id="phonenumber"
                value={phoneNumber}
                onChange={(e) => setphoneNumber(e.target.value)}
                className="userinfoupdate_update-form-group_input"
            />
                </div>
            <div className="userinfoupdate_update-form-group">
                    <label htmlFor="password" className="userinfoupdate_update-form-group_label">비밀번호 변경</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        className="userinfoupdate_update-form-group_input"
                        placeholder="비밀번호를 입력해주세요"
                        onChange={(e) => {
                            setPassword(e.target.value);
                            validatePasswords(e.target.value, confirmPassword);
                        }}
                        
                    />
                </div>

                <div className="userinfoupdate_update-form-group">
                    <label htmlFor="confirm-password" className="userinfoupdate_update-form-group_label">비밀번호 변경 - 재입력</label>
                    <input
                        type="password"
                        id="confirm-password"
                        value={confirmPassword}
                        placeholder="비밀번호를 다시 입력해 주세요"
                        onChange={(e) => {
                            setConfirmPassword(e.target.value);
                            validatePasswords(password, e.target.value);
                        }}
                        className="userinfoupdate_update-form-group_input"
                    />
                        {error && <div className="userinfoupdate_error-message">{error}</div>} {/* 에러 메시지 표시 */}
                </div>

                <div className="userinfoupdate_update-form-group">
                    <div className="userinfoupdate_update-address">
                
                        <label htmlFor="address" className="userinfoupdate_update-form-group_label">주소 변경</label>
                        <div className="userinfoupdate_update-address-serch">
                            <input value={zipCode} readOnly placeholder="우편번호" className="userinfoupdate_update-form-group_input"/>
                            <button onClick={toggle} className="userinfoupdate_update-address-serch_btn">주소 찾기</button>
                        </div>

                        <div className="userinfoupdate_update-address-detail">
                            <input value={roadAddress} readOnly placeholder="도로명 주소" className="userinfoupdate_update-form-group_input" />

                            <Modal isOpen={isOpen} ariaHideApp={false} style={customStyles}>
                            <button 
                                onClick={toggle} 
                                style={{ 
                                    alignSelf: 'center', 
                                    padding: '12px 24px', 
                                    fontSize: '16px', 
                                    marginTop: '20px',
                                    background: 'rgb(157, 146, 116)',
                                    color: 'white',
                                    border: 'none',
                                    borderRadius: '12px',
                                    fontWeight: '600',
                                    cursor: 'pointer',
                                    transition: 'all 0.3s ease',
                                    boxShadow: '0 3px 12px rgba(157, 146, 116, 0.25)',
                                    letterSpacing: '0.3px',
                                    minWidth: '80px'
                                }}
                                onMouseEnter={(e) => {
                                    e.target.style.background = 'rgb(127, 116, 86)';
                                    e.target.style.transform = 'translateY(-2px)';
                                    e.target.style.boxShadow = '0 6px 18px rgba(157, 146, 116, 0.35)';
                                }}
                                onMouseLeave={(e) => {
                                    e.target.style.background = 'rgb(157, 146, 116)';
                                    e.target.style.transform = 'translateY(0)';
                                    e.target.style.boxShadow = '0 3px 12px rgba(157, 146, 116, 0.25)';
                                }}
                                onMouseDown={(e) => {
                                    e.target.style.transform = 'translateY(0)';
                                }}
                            >
                                닫기
                            </button>                              
                            <DaumPostcode onComplete={completeHandler} height="100%" />
                            </Modal>
                            
                            <input
                                type="text"
                                onChange={(e) => setDetailAddress(e.target.value)}
                                value={detailAddress}
                                className="userinfoupdate_update-form-group_input"
                            />
                        </div>
                    </div>
                </div> 
                <button className="userinfoupdate_update-btn" onClick={handlePUT}>저장하기</button>
        </div>
        </>
    );
}

export default UserInfoUpdate;