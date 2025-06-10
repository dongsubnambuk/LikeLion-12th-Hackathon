import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import '../CSS/DietPaymentMainPage.css';

function DietPaymentMainPage() {
    const navigate = useNavigate();

    const [isLoggedIn] = useState(true); // 실제 로그인 상태에 따라 변경 필요
    const [mealData, setMealData] = useState([]);
    const [allMealsPrice, setAllMealsPrice] = useState(0);

    useEffect(() => {
        const storedMeal = localStorage.getItem("Meal");
        if (storedMeal) {
            const mealData = JSON.parse(storedMeal);
            // 데이터 변환
            const transformedData = mealData.map(dayData => ({
                date: dayData.day,
                meals: dayData.mealOptions.map(option => ({
                    ...option.foodMenus[0], // foodMenus의 첫 번째 항목
                    count: option.count // count를 포함
                }))
            }));
            setMealData(transformedData);
            calculateTotalPrice(transformedData);
        }
    }, []);

    useEffect(() => {
        if (isLoggedIn) {
            // 로그인 상태에 따라 추가 작업 필요
        } else {
            // 비회원 상태 처리
        }
    }, [isLoggedIn]);

    const calculateTotalPrice = (data) => {
        const totalPrice = data.reduce((total, day) => {
            return total + day.meals.reduce((dayTotal, meal) => {
                // 가격 문자열을 숫자로 변환
                const price = parseInt(meal.price.replace(/[^0-9]/g, ''), 10);
                // 가격과 count를 곱하여 총액 계산
                return dayTotal + (price * meal.count);
            }, 0);
        }, 0);
        setAllMealsPrice(totalPrice);
    };

    const handlePaymentConfirmClick = () => {
        navigate(`/dietpayment`, { state: { price: allMealsPrice, orderDetails: mealData } });
    };

    const dateRange = mealData.length ? `${mealData[0].date} ~ ${mealData[mealData.length - 1].date}` : '';

    return (
        <>
            <div className="DPMPcontainer">
                <div className="DPMPdateRange">- {dateRange} -</div>
                <div className="DPMPlistContainer">
                    {mealData.map((day, dayIndex) => (
                        <div key={dayIndex} className="DPMPdayContainer">
                            <div className="DPMPdate">- {day.date} -</div>
                            {day.meals.map((meal, mealIndex) => (
                                <div key={mealIndex} className="DPMPitemCard">
                                    <div className="DPMPitemTitle">{meal.name}</div>
                                    <div className="DPMPitemImage">
                                        <img src={meal.image} style={{ width: '100%', height: '100%' }} alt={meal.name} />
                                    </div>
                                    <div className="DPMPitemPrice">
                                        {meal.price} * {meal.count}
                                    </div>
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
        </>
    );
}

export default DietPaymentMainPage;
