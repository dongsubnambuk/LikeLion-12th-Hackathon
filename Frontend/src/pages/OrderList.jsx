import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import '../CSS/OrderList.css';

function OrderList() {
    const [address, setAddress] = useState({ 
        roadAddress: '서울특별시 강남구 테헤란로 123', 
        detailAddress: '456호' 
    });
    const [payments, setPayments] = useState([]);
    const [mealData, setMealData] = useState(null);
    const navigate = useNavigate();

    // 예시 데이터로 초기화
    useEffect(() => {
        // 예시 결제 데이터
        const samplePayments = [
            {
                paymentId: "PAY20250613001",
                dateTime: "2025-06-10T14:30:00Z",
                totalPrice: 89000,
                weeklyId: "WEEK_HEALTHY_001"
            },
            {
                paymentId: "PAY20250613002", 
                dateTime: "2025-06-05T09:15:00Z",
                totalPrice: 95000,
                weeklyId: "WEEK_PROTEIN_002"
            },
            {
                paymentId: "PAY20250613003",
                dateTime: "2025-05-28T16:45:00Z", 
                totalPrice: 78000,
                weeklyId: "WEEK_VEGGIE_003"
            }
        ];
        
        // 날짜 기준으로 최신순 정렬 (내림차순)
        const sortedPayments = samplePayments.sort((a, b) => {
            return new Date(b.dateTime) - new Date(a.dateTime);
        });
        
        setPayments(sortedPayments);
    }, []);

    // 유저 정보 fetch (예시 데이터로 대체)
    useEffect(() => {
        const handleGetUser = async () => {
            // 실제 API 호출 대신 예시 데이터 설정
            console.log("사용자 정보 로드 완료 (예시 데이터)");
        };

        handleGetUser();
    }, []);

    useEffect(() => {
        const handlePaymentGet = async () => {
            // setPayments는 위의 useEffect에서 이미 설정됨
            console.log("결제내역 로드 완료 (예시 데이터)");
        };
    
        handlePaymentGet();
    }, []);
    
    useEffect(() => {
        const handleWeeklyId = async () => {
            console.log("주간 식사 계획 로드 완료 (예시 데이터)");
        };
    
        handleWeeklyId();
    }, []);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}년 ${month}월 ${day}일`;
    };

    useEffect(() => {
        const handleGetUserDiet = async () => {
            // 예시 식단 데이터
            const sampleMealData = {
                monday: "닭가슴살 샐러드, 현미밥",
                tuesday: "연어 스테이크, 퀴노아",
                wednesday: "두부 스테이크, 채소볶음"
            };
            setMealData(sampleMealData);
            console.log("식단 데이터 로드 완료 (예시 데이터)");
        };
        
        handleGetUserDiet();
    }, []); 

    const handleDetailClick = (weeklyId) => {
        navigate('/weeklyfoodmenu')
    };

    return (
        <>
            <div className="orderList_container">
                {payments.map((payment, index) => (
                    <div key={index} className="orderList_wrapper">
                        <div className="orderList_date">
                            {/* 날짜 표시 영역 */}
                        </div>
                        <div className="orderList_inner" onClick={() => handleDetailClick(payment.weeklyId)}>
                            <div className="orderList_header">
                                <div className="orderList_orderInfo">
                
                                    <div className="orderList_paymentId">주문 번호: {payment.paymentId}</div>
                                </div>
                            </div>
                            
                            <div className="orderList_content">
                                <div className="orderList_mainInfo">
                                    <div className="orderList_dateSection">
                                        <span className="orderList_label">결제 일시</span>
                                        <div className="orderList_dateValue">{formatDate(payment.dateTime)}</div>
                                    </div>
                                    <div className="orderList_priceSection">
                                        <span className="orderList_label">결제 금액</span>
                                        <div className="orderList_price">{payment.totalPrice.toLocaleString()}원</div>
                                    </div>
                                </div>
                                
                                <div className="orderList_infoGrid">
                                    <div className="orderList_infoItem address">
                                        <div className="orderList_infoTitle">배송 주소</div>
                                        <div className="orderList_infoContent">{address.roadAddress} {address.detailAddress}</div>
                                    </div>
                                    
                                    <div className="orderList_infoItem menu">
                                        <div className="orderList_infoTitle">주문 메뉴</div>
                                        <div className="orderList_infoContent">{payment.weeklyId}</div>
                                    </div>
                                </div>
                                
                                <div className="orderList_detail">
                                    <button className="orderList_detailButton">
                                        자세히 보기
                                        <span className="orderList_arrow">→</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
}

export default OrderList;