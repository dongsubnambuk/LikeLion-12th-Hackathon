import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { CheckCircleOutlined } from '@ant-design/icons';
import '../CSS/DietPaymentVerificationPage.css';

function DietPaymentComplete() {
    const navigate = useNavigate();
    const location = useLocation();
    const { paymentId } = location.state || {};


    // 식단 생성
    useEffect(() => {
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

                const result = await response;

                if (response.status === 200) {
                    console.log(result);
                    console.log("식단 생성 성공");
                    localStorage.setItem("isPay", "true");
                    console.log("식단 생성 유무 : ", localStorage.getItem("isPay"));
                    localStorage.setItem("isPay", true);

                } else {
                    console.log("식단 생성 실패");
                    alert("식단 생성 실패: " + result.message);
                }
            } else {
                console.log("식단 : 로그인 안 됨");
            }
        };

        // handleCreatUserDiet();

    }, []);


    return (
        <>
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
        </>
    );
}

export default DietPaymentComplete;
