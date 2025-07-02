import { useLocation, useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentPage.css';

function DietPaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const { price, orderDetails } = location.state; 

    const mealCount = orderDetails.reduce((total, day) => total + day.meals.length, 0);
    const firstMealName = orderDetails[0].meals[0].name || orderDetails[0].meals[0].title;
    const additionalMealsCount = mealCount - 1;
    const orderedMealNames = additionalMealsCount > 0 
        ? `${firstMealName} 외 ${additionalMealsCount}개` 
        : firstMealName;

    let weeklyId = 1;

    const getUserEmail = async () => {
        try {
            const response = await fetch('http://nimn.store/api/users', {
                method: 'GET',
                credentials: 'include'
            });
            if (!response.ok) {
                throw new Error('사용자 정보를 가져올 수 없습니다.');
            }
            const userData = await response.json();
            return userData.email;
        } catch (error) {
            throw new Error('로그인이 필요합니다.');
        }
    };

    // localStorage 데이터를 API 형식에 맞게 변환하는 함수
    const transformMealDataToApiFormat = (mealData, userEmail) => {
        return mealData.map(dayData => ({
            dailyDietId: 0,
            date: dayData.day,
            userEmail: userEmail,
            foodSelections: dayData.mealOptions.map(option => ({
                foodSelectionId: 0,
                foodTime: option.mealType,
                foodId: option.foodMenus[0].id,
                count: option.count,
                userEmail: userEmail
            }))
        }));
    };

    const onVerification = async (response, orderId) => {
        if (response.error_code != null) {
            return alert(`결제에 실패하였습니다. 에러 내용: ${response.error_msg}`);
        }

        try {
            const paymentRequestData = {
                paymentUid: response.imp_uid,
                orderId: String(orderId)
            };

            const paymentResponse = await fetch('http://nimn.store/api/payment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify(paymentRequestData),
            });

            if (!paymentResponse.ok) {
                if (paymentResponse.status === 500) {
                    alert('결제 검증 중 서버 오류가 발생했습니다. 고객센터로 문의해주세요.');
                } else {
                    alert(`결제 내역 생성에 실패했습니다. 상태 코드: ${paymentResponse.status}`);
                }
                return;
            }

            const responseText = await paymentResponse.text();
            let paymentData;
            try {
                paymentData = JSON.parse(responseText);
            } catch (parseError) {
                alert('서버 응답을 처리하는 중 오류가 발생했습니다.');
                return;
            }

            const paymentId = paymentData.id;
            if (!paymentId) {
                throw new Error('결제 내역 생성 중 오류가 발생했습니다. paymentId를 찾을 수 없습니다.');
            }
            
            navigate(`/dietpaymentcomplete`, { 
                state: { 
                    imp_uid: response.imp_uid, 
                    merchant_uid: response.merchant_uid,
                    paymentId: paymentId,
                    totalPrice: paymentData.totalPrice
                } 
            });

        } catch (error) {
            alert(`결제 내역 생성 중 오류가 발생했습니다. ${error.message}`);
        }
    };

    const requestPay = async () => {

        const handleCreatUserDiet = async () => {
            try {
                const userEmail = await getUserEmail();

                const startDate = localStorage.getItem("startDate");
                const endDate = localStorage.getItem("endDate");
                const dailyDiets = localStorage.getItem("Meal");

                if (!dailyDiets) {
                    throw new Error('식단 데이터가 없습니다.');
                }

                const parsedMealData = JSON.parse(dailyDiets);
                const transformedDailyDiets = transformMealDataToApiFormat(parsedMealData, userEmail);

                const response = await fetch(`http://nimn.store/api/diet/weekly/create`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        "weeklyId": 0,
                        "startDate": JSON.parse(startDate),
                        "endDate": JSON.parse(endDate),
                        "dailyDiets": transformedDailyDiets,
                        "userEmail": userEmail
                    })
                });

                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`식단 생성 실패: ${response.status}`);
                }

                const result = await response.json();
                localStorage.setItem("isPay", "true");
                weeklyId = result.weeklyId;

            } catch (error) {
                alert("식단 생성 중 오류가 발생했습니다: " + error.message);
                throw error;
            }
        };

        try {
            await handleCreatUserDiet();

            // 실제 유저 정보 가져오기
            const userData = await fetch('http://nimn.store/api/users', {
                method: 'GET',
                credentials: 'include'
            });
            
            if (!userData.ok) {
                throw new Error('사용자 정보를 가져올 수 없습니다.');
            }
            
            const userInfo = await userData.json();
            
            const orderResponse = await fetch('http://nimn.store/api/order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    purchaser: userInfo.email,
                    weeklyDietId: weeklyId,
                }),
            });

            if (!orderResponse.ok) {
                const errorText = await orderResponse.text();
                throw new Error('주문내역 생성에 실패했습니다.');
            }
    
            const orderData = await orderResponse.json();
            const orderId = orderData.id;
            const totalPrice = orderData.totalPrice;
    
            localStorage.setItem("weeklyId", weeklyId);

            const { IMP } = window;
            IMP.init('imp77151582');
    
            // 실제 사용자 데이터로 수정
            const data = {
                pg: `html5_inicis.INIpayTest`,
                pay_method: 'card',
                merchant_uid: `${orderId}`,
                name: orderedMealNames,
                amount: totalPrice,
                buyer_email: userInfo.email,
                buyer_name: userInfo.name,
                buyer_tel: userInfo.phone_number,
                buyer_addr: `${userInfo.road_address} ${userInfo.detail_address}`,
                buyer_postcode: '123-456',
                m_redirect_url: 'http://nimn.store/dietpaymentcomplete',
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
