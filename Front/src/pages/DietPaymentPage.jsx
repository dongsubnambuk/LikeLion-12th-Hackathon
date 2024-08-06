import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const { price} = location.state; 

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
                    weeklyId: weeklyId, 
                }),
            });
    
            if (!orderResponse.ok) {
                throw new Error('주문내역 생성에 실패했습니다.');
            }
    
            const orderData = await orderResponse.json();
            if (orderData.result !== 'success') {
                throw new Error('주문내역 생성에 실패했습니다.');
            }
    
            const { orderId } = orderData.data; // 서버로부터 받은 주문번호 사용
            console.log(orderId);
    
            // Step 2: 결제 요청 진행
            const { IMP } = window; // 생략 가능
            IMP.init('imp77151582'); // 아임포트 관리자 콘솔에서 확인한 가맹점 식별코드
    
            const data = {
                pg: `html5_inicis.INIpayTest`, // PG사
                pay_method: 'card', // 결제수단
                merchant_uid: `${orderId}`, // 주문번호를 사용하여 고유한 merchant_uid 생성
                name: '당근 10kg',
                amount: 100, 
                buyer_email: email,
                buyer_name: '홍길동',
                buyer_tel: '010-1234-5678',
                buyer_addr: '서울특별시 강남구 삼성동',
                buyer_postcode: '123-456',
                m_redirect_url: 'http://nimn.store/dietpaymentverification', // 결제 후 리디렉션될 URL
            };
    
            IMP.request_pay(data, (response) => onVerification(response, orderId));
        } catch (error) {
            alert(`결제 요청 중 오류가 발생했습니다. ${error.message}`);
        }
    };
    
    return (
        <>
            <Header />
            <div className="DPPcontainer">
                <div className="DPPpayText">
                    결제금액 : {price.toLocaleString()}원<br /><br />
                    아래 '결제하기' 버튼을 눌러
                    <br />결제를 진행해주세요.
                </div>
                <div className="DPPpayBtn">
                    <button onClick={requestPay}>결제하기</button>
                </div>
            </div >

            <BottomNav />
        </>
    );
}

export default DietPaymentPage;
