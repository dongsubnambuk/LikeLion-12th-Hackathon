// src/pages/DietPaymentComplete.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../CSS/DietPaymentComplete.css';

function DietPaymentComplete() {
    const navigate = useNavigate();
    const location = useLocation();
    const { paymentId, imp_uid, merchant_uid, totalPrice } = location.state || {};
    const [orderDetails, setOrderDetails] = useState(null);

    useEffect(() => {
        // 결제 완료 후 localStorage 정리
        const cleanupLocalStorage = () => {
            localStorage.removeItem("Meal");
            localStorage.removeItem("checkMealLoad");
            localStorage.removeItem("scrollIndex");
            localStorage.setItem("isPay", "true");
        };

        // 주문 정보 설정
        const setupOrderDetails = () => {
            const startDate = JSON.parse(localStorage.getItem("startDate"));
            const endDate = JSON.parse(localStorage.getItem("endDate"));
            
            if (startDate && endDate) {
                setOrderDetails({
                    startDate,
                    endDate,
                    paymentId: paymentId || 'N/A',
                    imp_uid: imp_uid || 'N/A',
                    merchant_uid: merchant_uid || 'N/A',
                    totalPrice: totalPrice || 0
                });
            }
        };

        cleanupLocalStorage();
        setupOrderDetails();
    }, [paymentId, imp_uid, merchant_uid, totalPrice]);

    const getDateRange = () => {
        if (orderDetails) {
            const startDate = new Date(orderDetails.startDate).toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
            const endDate = new Date(orderDetails.endDate).toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
            return `${startDate} ~ ${endDate}`;
        }
        return '';
    };

    const handleGoHome = () => {
        navigate('/');
    };

    const handleViewOrders = () => {
        navigate('/orderlist'); // 주문 내역 페이지로 이동
    };

    return (
        <div className="diet-payment-complete-container">
            {/* 성공 헤더 */}
            <div className="diet-payment-complete-header">
                <div className="diet-payment-complete-success-icon">
                    <div className="diet-payment-complete-simple-checkmark">
                        ✅
                    </div>
                </div>
                <h1 className="diet-payment-complete-title">결제 완료!</h1>
                <p className="diet-payment-complete-subtitle">주문이 성공적으로 처리되었습니다</p>
            </div>

            {/* 주문 정보 카드 */}
            <div className="diet-payment-complete-info-card">
                <h2 className="diet-payment-complete-section-title">📋 주문 정보</h2>
                
                <div className="diet-payment-complete-info-grid">
                    <div className="diet-payment-complete-info-item">
                        <span className="diet-payment-complete-info-label">주문 번호</span>
                        <span className="diet-payment-complete-info-value">{orderDetails?.merchant_uid || 'N/A'}</span>
                    </div>
                    <div className="diet-payment-complete-info-item">
                        <span className="diet-payment-complete-info-label">결제 번호</span>
                        <span className="diet-payment-complete-info-value">{orderDetails?.paymentId || 'N/A'}</span>
                    </div>
                    <div className="diet-payment-complete-info-item">
                        <span className="diet-payment-complete-info-label">식단 기간</span>
                        <span className="diet-payment-complete-info-value">{getDateRange()}</span>
                    </div>
                    <div className="diet-payment-complete-info-item">
                        <span className="diet-payment-complete-info-label">결제 금액</span>
                        <span className="diet-payment-complete-info-value highlight">
                            {orderDetails?.totalPrice ? orderDetails.totalPrice.toLocaleString() : '0'}원
                        </span>
                    </div>
                </div>
            </div>

            {/* 버튼 섹션 */}
            <div className="diet-payment-complete-button-section">
                <button 
                    className="diet-payment-complete-secondary-btn"
                    onClick={handleViewOrders}
                >
                    주문 내역 보기
                </button>
                <button 
                    className="diet-payment-complete-primary-btn"
                    onClick={handleGoHome}
                >
                    홈으로 돌아가기
                </button>
            </div>
        </div>
    );
}

export default DietPaymentComplete;
