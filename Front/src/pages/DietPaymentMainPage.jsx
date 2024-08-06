import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import '../CSS/DietPaymentMainPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';

function DietPaymentMainPage() {
    const navigate = useNavigate();

    const handlePaymentConfirmClick = () => {
        navigate(`/dietpayment`, { state: { price: allMealsPrice, orderDetails: mealData } });
    };

    const [isLoggedIn] = useState(true);
    const [mealData, setMealData] = useState(null);
    const [allMealsPrice, setAllMealsPrice] = useState(0);

    useEffect(() => {
        if (isLoggedIn) {
            const data = [
                {
                    date: '2024년 7월 15일 월요일',
                    meals: [
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                        { title: '김치찌개 정식', price: '3,840원', count: 2 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                    ],
                },
                {
                    date: '2024년 7월 16일 화요일',
                    meals: [
                        { title: '김치찌개 정식', price: '6,500원', count: 2 },
                        { title: '불고기 정식', price: '7,600원', count: 1 },
                        { title: '고등어 구이 정식', price: '5,800원', count: 2 },
                    ],
                },
                {
                    date: '2024년 7월 17일 수요일',
                    meals: [
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                        { title: '김치찌개 정식', price: '3,840원', count: 2 },
                        { title: '김치찌개 정식', price: '3,200원', count: 1 },
                    ],
                },
                {
                    date: '2024년 7월 18일 목요일',
                    meals: [
                        { title: '김치찌개 정식', price: '3,840원', count: 2 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                    ],
                },
                {
                    date: '2024년 7월 19일 금요일',
                    meals: [
                        { title: '김치찌개 정식', price: '3,840원', count: 2 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                    ],
                },
                {
                    date: '2024년 7월 20일 토요일',
                    meals: [
                        { title: '김치찌개 정식', price: '3,840원', count: 2 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                    ],
                },
                {
                    date: '2024년 7월 21일 일요일',
                    meals: [
                        { title: '김치찌개 정식', price: '3,840원', count: 2 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                        { title: '김치찌개 정식', price: '3,840원', count: 1 },
                    ],
                }
            ];
            setMealData(data);
            calculateTotalPrice(data);
        } else {
            // 비회원 인기 식단 데이터 설정
            <h1>로그인을 해주세요</h1>
        }
    }, [isLoggedIn]);

    const calculateTotalPrice = (data) => {
        const totalPrice = data.reduce((total, day) => {
            return total + day.meals.reduce((dayTotal, meal) => {
                // Convert price string to number
                const price = parseInt(meal.price.replace(/[^0-9]/g, ''), 10);
                return dayTotal + (price * meal.count);
            }, 0);
        }, 0);
        setAllMealsPrice(totalPrice);
    };

    const dateRange = mealData ? `${mealData[0].date} ~ ${mealData[mealData.length - 1].date}` : '';

    return (
        <>
            <Header />

            <div className="DPMPcontainer">
                <div className="DPMPdateRange">- {dateRange} -</div>
                <div className="DPMPlistContainer">
                    {mealData && mealData.map((day, dayIndex) => (
                        <div key={dayIndex} className="DPMPdayContainer">
                            <div className="DPMPdate">- {day.date} -</div>
                            {day.meals.map((meal, mealIndex) => (
                                <div key={mealIndex} className="DPMPitemCard" >
                                    <div className="DPMPitemTitle">{meal.title}</div>
                                    <div className="DPMPitemImage">
                                        <img src={logo} style={{ width: '100%', height: '100%' }} alt="logo" />
                                    </div>
                                    <div className="DPMPitemPrice">{meal.price} * {meal.count}</div>
                                </div>
                            ))}
                        </div>
                    ))}
                </div>
                <div className="DPMPitemAllPrice">
                    총 결제 금액 : {allMealsPrice.toLocaleString()}원
                </div>
                <div className="DPMPpaymentConfirm" onClick={handlePaymentConfirmClick}>
                    <p className="DPMPpaymentConfirmText">결제하기</p>
                </div>
            </div>

            <BottomNav />
        </>
    );
}

export default DietPaymentMainPage;
