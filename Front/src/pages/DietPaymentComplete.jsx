import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { CheckCircleOutlined } from '@ant-design/icons';
import '../CSS/DietPaymentVerificationPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentComplete() {
    const navigate = useNavigate();
    const location = useLocation();
    const { imp_uid, merchant_uid, paymentId } = location.state || {};

    return (
        <>
            <Header />
            <div className="payment-complete-container">
                <div className="payment-complete-content">
                    <div className="payment-complete-icon">
                        <CheckCircleOutlined style={{ fontSize: '80px', color: '#28a745' }} />
                    </div>
                    <div className="payment-complete-message">
                        결제에 성공하였습니다.
                    </div>
                    <div className="payment-complete-details">
                        결제 번호: {paymentId || 'N/A'}
                    </div>
                    <button className="payment-complete-button" onClick={() => navigate('/')}>
                        돌아가기
                    </button>
                </div>
            </div>
            <BottomNav />
        </>
    );
}

export default DietPaymentComplete;
