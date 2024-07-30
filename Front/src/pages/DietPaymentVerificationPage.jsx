import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentVerificationPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentVerificationPage() {
    const navigate = useNavigate();
    const [verificationResult, setVerificationResult] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const verifyPayment = async () => {
            try {
                const response = await fetch(`http://localhost:3000/dietpaymentverification`, {
                    method: "GET",
                    headers: { "Content-Type": "application/json" },
                });
                const result = await response.json();
                setVerificationResult(result);
            } catch (error) {
                console.error('결제 검증 실패', error);
                setVerificationResult({ success: false, message: '결제 검증에 실패하였습니다.' });
            } finally {
                setLoading(false);
            }
        };

        verifyPayment();
    }, []);

    const handleBackToHome = () => {
        navigate('/');
    };

    return (
        <>
            <Header />
            <div className="DPVPcontainer">
                {loading ? (
                    <div>결제 검증 중...</div>
                ) : (
                    <>
                        {verificationResult.success ? (
                            <div className="DPVPmainText">
                                <h2>결제 검증 성공</h2>
                                <br />
                                <p>{verificationResult.message}</p>
                            </div>
                        ) : (
                            <div className="DPVPmainText">
                                <h2>결제 검증 실패</h2>
                                <br />
                                <p>{verificationResult.message}</p>
                            </div>
                        )}
                            <button onClick={handleBackToHome} className="DPVPBtn">홈으로 돌아가기</button>
                    </>
                )}
            </div>
            <BottomNav />
        </>
    );
}

export default DietPaymentVerificationPage;
