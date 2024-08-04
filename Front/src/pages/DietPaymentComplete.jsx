import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { CheckCircleOutlined } from '@ant-design/icons';
import '../CSS/DietPaymentVerificationPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentComplete() {
    const navigate = useNavigate();
    const location = useLocation();
    const {  paymentId } = location.state || {};


    // // 식단 생성
    // useEffect(() => {
    //   const handleCreatUserDiet = async () => {
    //     if (isLoggedIn) {
    //       console.log("로그인 됨, 식단 생성");
    //       console.log("유저 이메일 =", userEmail);
    //       const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/create`, {
    //         method: "POST",
    //         headers: {
    //           "Content-Type": "application/json",
    //           "Authorization": userToken,
    //         },
    //         body: JSON.stringify({
    //           "startDate": "2024-08-05",
    //           "endDate": "2024-08-11",
    //           "dailyDiets": [
    //             {
    //               "date": "2024-08-05",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 1,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 2,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 3,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             },
    //             {
    //               "date": "2024-08-06",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 4,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 5,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 6,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             },
    //             {
    //               "date": "2024-08-07",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 7,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 8,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 9,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             },
    //             {
    //               "date": "2024-08-08",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 10,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 11,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 12,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             },
    //             {
    //               "date": "2024-08-09",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 13,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 14,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 15,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             },
    //             {
    //               "date": "2024-08-10",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 16,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 17,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 18,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             },
    //             {
    //               "date": "2024-08-11",
    //               "mealSelections": [
    //                 {
    //                   "mealTime": "아침",
    //                   "foodMenuId": 19,
    //                   "count": 2,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "점심",
    //                   "foodMenuId": 20,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 },
    //                 {
    //                   "mealTime": "저녁",
    //                   "foodMenuId": 21,
    //                   "count": 1,
    //                   "userEmail": "user@naver.com"
    //                 }
    //               ],
    //               "userEmail": "user@naver.com"
    //             }
    //           ],
    //           "userEmail": "user@naver.com"
    //         }),
    //       });

    //       const result = await response.json();

    //       if (response.status === 200) {
    //         console.log(result);
    //         console.log("식단 생성 성공");
    //       } else {
    //         console.log("식단 생성 실패");
    //         alert("식단 생성 실패: " + result.message);
    //       }
    //     } else {
    //       console.log("식단 : 로그인 안 됨");
    //     }
    //   };

    //   if (isLoggedIn) {
    //     handleCreatUserDiet();
    //   }
    // }, [isLoggedIn, userEmail, userToken]);


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
