// src/pages/DietPaymentPage.jsx
import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentPage.css';

function DietPaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const { price, orderDetails } = location.state; 

    // Generate a string with the name of the first ordered meal and indicate the number of additional meals
    const mealCount = orderDetails.reduce((total, day) => total + day.meals.length, 0);
    const firstMealName = orderDetails[0].meals[0].title;
    const additionalMealsCount = mealCount - 1;
    const orderedMealNames = additionalMealsCount > 0 
        ? `${firstMealName} 외 ${additionalMealsCount}개` 
        : firstMealName;

    const weeklyId = 1;

    const onVerification = async (response, orderId) => {
        const token = localStorage.getItem("token");
    
        console.log('결제 성공', response);
    
        if (response.error_code != null) {
            return alert(`결제에 실패하였습니다. 에러 내용: ${response.error_msg}`);
        }
    
        try {
            // Step 2: 결제 내역 생성 API 호출
            const paymentResponse = await fetch('http://3.37.64.39:8000/api/payment/newPayment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization": token,
                },
                body: JSON.stringify({
                    orderId: String(response.merchant_uid), // 주문내역 응답에서 받은 orderId를 문자열로 변환
                    paymentUid: response.imp_uid,
                }),
            });
            const paymentData = await paymentResponse.json();

            // Log the entire response to check the structure
            console.log(paymentData);
            if (!paymentResponse.ok) {
                throw new Error('결제 내역 생성에 실패했습니다.');
            }

            const paymentId = paymentData.data?.paymentId;
            if (!paymentId) {
                throw new Error('결제 내역 생성 중 오류가 발생했습니다. paymentId를 찾을 수 없습니다.');
            }
            
            navigate(`/dietpaymentcomplete`, { 
                state: { 
                    imp_uid: response.imp_uid, 
                    merchant_uid: response.merchant_uid,
                    paymentId: paymentId 
                } 
            });
    
        } catch (error) {
            alert(`결제 내역 생성 중 오류가 발생했습니다. ${error.message}`);
        }
    };
    
    const requestPay = async () => {

        const handleCreatUserDiet = async () => {
            const storedIsLoggedIn = localStorage.getItem("isLoggedIn") === "true";
            const userToken = localStorage.getItem("token");
            const userEmail = localStorage.getItem("email");

            const startDate = localStorage.getItem("startDate");
            const endDate = localStorage.getItem("endDate");

            const dailyDiets = localStorage.getItem("Meal");

            if (storedIsLoggedIn) {
                console.log("로그인 됨, 식단 생성");
                console.log("유저 이메일 =", userEmail);
                const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/create`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": userToken,
                    },
                    body: JSON.stringify({
                        "startDate": startDate,
                        "endDate": endDate,
                        "userEmail": userEmail,
                        "dailyMealPlans": JSON.parse(dailyDiets) // 문자열을 객체로 변환
                    })
                });

                const result = await response.json();

                if (response.status === 200) {
                    console.log(result);
                    console.log("식단 생성 성공");
                    localStorage.setItem("isPay", "true");
                    console.log("식단 생성 유무 : ", localStorage.getItem("isPay"));
                    localStorage.setItem("isPay", true);

                    weeklyId = result.weeklyId;

                } else {
                    console.log("식단 생성 실패");
                    alert("식단 생성 실패: " + result.message);
                }
            } else {
                console.log("식단 : 로그인 안 됨");
            }
        };

        handleCreatUserDiet();

        const token = localStorage.getItem("token");
        const email = localStorage.getItem("email");
        try {
            // Step 1: 주문내역 생성 API 호출
            const orderResponse = await fetch('http://3.37.64.39:8000/api/payment/order/newOrder', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization": token,
                },
                body: JSON.stringify({
                    purchaser: email,
                    totalPrice: 100,
                    weeklyId: 5000, 
                }),
            });

            if (!orderResponse.ok) {
                throw new Error('주문내역 생성에 실패했습니다.');
            }
    
            const orderData = await orderResponse.json();
            if (orderData.result !== 'success') {
                throw new Error('주문내역 생성에 실패했습니다.');
            }
            const { orderId, weeklyId } = orderData.data; // 서버로부터 받은 주문번호와 weeklyId 사용
            console.log(orderId);
    
                // weeklyId를 로컬 스토리지에 저장
            localStorage.setItem("weeklyId", weeklyId);

            // Step 2: 결제 요청 진행
            const { IMP } = window; // 생략 가능
            IMP.init('imp77151582'); // 아임포트 관리자 콘솔에서 확인한 가맹점 식별코드
    
            const data = {
                pg: `html5_inicis.INIpayTest`, // PG사
                pay_method: 'card', // 결제수단
                merchant_uid: `${orderId}`, // 주문번호를 사용하여 고유한 merchant_uid 생성
                name: orderedMealNames, // Use the generated meal names string
                amount: 100,
                // amount: price, 임의로 100원으로 설정
                buyer_email: email,
                buyer_name: '홍길동',
                buyer_tel: '010-1234-5678',
                buyer_addr: '서울특별시 강남구 삼성동',
                buyer_postcode: '123-456',
                m_redirect_url: 'http://nimn.store/dietpaymentcomplete', // 결제 후 리디렉션될 URL
            };
    
            IMP.request_pay(data, (response) => onVerification(response, orderId));
        } catch (error) {
            alert(`결제 요청 중 오류가 발생했습니다. ${error.message}`);
        }
    };

    // 주문 요약 정보 계산
    const getTotalMeals = () => {
        return orderDetails.reduce((total, day) => total + day.meals.length, 0);
    };

    const getDateRange = () => {
        if (orderDetails.length > 0) {
            const startDate = new Date(orderDetails[0].date).toLocaleDateString('ko-KR', {
                month: 'long',
                day: 'numeric'
            });
            const endDate = new Date(orderDetails[orderDetails.length - 1].date).toLocaleDateString('ko-KR', {
                month: 'long',
                day: 'numeric'
            });
            return `${startDate} ~ ${endDate}`;
        }
        return '';
    };
    
    return (
        <div className="diet-payment-page-container">
            {/* 헤더 섹션 */}
            <div className="diet-payment-page-header">
                <h1 className="diet-payment-page-title">💳 결제하기</h1>
                <p className="diet-payment-page-subtitle">주문 내역을 확인하고 결제를 진행해주세요</p>
            </div>

            {/* 결제 정보 카드 */}
            <div className="diet-payment-page-payment-card">
                <div className="diet-payment-page-order-summary">
                    <h2 className="diet-payment-page-section-title">📋 주문 요약</h2>
                    
                    <div className="diet-payment-page-summary-grid">
                        <div className="diet-payment-page-summary-item">
                            <span className="diet-payment-page-summary-label">주문 기간</span>
                            <span className="diet-payment-page-summary-value">{getDateRange()}</span>
                        </div>
                        <div className="diet-payment-page-summary-item">
                            <span className="diet-payment-page-summary-label">총 식단 수</span>
                            <span className="diet-payment-page-summary-value">{getTotalMeals()}개</span>
                        </div>
                        <div className="diet-payment-page-summary-item">
                            <span className="diet-payment-page-summary-label">주문 상품</span>
                            <span className="diet-payment-page-summary-value">{orderedMealNames}</span>
                        </div>
                    </div>
                </div>

                <div className="diet-payment-page-price-section">
                    <div className="diet-payment-page-price-breakdown">
                        <div className="diet-payment-page-price-item">
                            <span className="diet-payment-page-price-label">상품 금액</span>
                            <span className="diet-payment-page-price-value">{price.toLocaleString()}원</span>
                        </div>
                        <div className="diet-payment-page-price-item">
                            <span className="diet-payment-page-price-label">배송비</span>
                            <span className="diet-payment-page-price-value">무료</span>
                        </div>
                        <div className="diet-payment-page-price-divider"></div>
                        <div className="diet-payment-page-total-price">
                            <span className="diet-payment-page-total-label">최종 결제 금액</span>
                            <span className="diet-payment-page-total-value">{price.toLocaleString()}원</span>
                        </div>
                    </div>
                </div>

                <div className="diet-payment-page-notice-section">
                    <h3 className="diet-payment-page-notice-title">⚠️ 결제 안내</h3>
                    <ul className="diet-payment-page-notice-list">
                        <li>결제 완료 후 주문 취소는 고객센터를 통해 가능합니다.</li>
                        <li>결제 오류 발생 시 고객센터로 문의해주세요.</li>
                    </ul>
                </div>
            </div>

            {/* 결제 버튼 섹션 */}
            <div className="diet-payment-page-button-section">
                <button 
                    className="diet-payment-page-back-btn"
                    onClick={() => navigate(-1)}
                >
                    이전으로
                </button>
                <button 
                    className="diet-payment-page-pay-btn"
                    onClick={requestPay}
                >
                    💳 {price.toLocaleString()}원 결제하기
                </button>
            </div>
        </div>
    );
}

export default DietPaymentPage;
