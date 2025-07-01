import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentMainPage.css';
import logo from '../images/logo.png';

function DietPaymentMainPage() {
    const navigate = useNavigate();
    
    const handlePaymentConfirmClick = () => {
        navigate('/dietpayment', { state: { price: allMealsPrice, orderDetails: mealData } });
    };

    const [mealData, setMealData] = useState(null);
    const [allMealsPrice, setAllMealsPrice] = useState(0);
    const [dateSet, setDateSet] = useState([]);

    useEffect(() => {
        const storedMeal = localStorage.getItem('Meal');
        if (storedMeal) {
            const parsedMealData = JSON.parse(storedMeal);
            setMealData(parsedMealData);
            setDateSet([parsedMealData[0].day, parsedMealData[6].day])

        }
    }, []);

    const dateRange = mealData ? `${mealData[0].date} ~ ${mealData[mealData.length - 1].date}` : '';

    return (
        <>
            <div className="DPMPcontainer">
                <div className="DPMPdateRange">- {dateRange} -</div>
                <div className="DPMPlistContainer">
                    {dateSet.map((day, dayIndex) => (
                        <div key={dayIndex} className="DPMPdayContainer">
                            <div className="DPMPdate">- {day} -</div>
                            {mealData[dayIndex].mealOptions.map((meal, mealIndex) => (
                                <div key={mealIndex} className="DPMPitemCard">
                                    <div className="DPMPitemTitle">{meal.title}</div>
                                    <div className="DPMPitemImage">
                                        <img src={logo} style={{ width: '100%', height: '100%' }} alt="logo" />
                                    </div>
                                    <div className="DPMPitemPrice">
                                        {meal.mealOptions.map((option, optionIndex) => (
                                            <div key={optionIndex}>
                                                {option.foodMenus[0].price} * {option.count}
                                            </div>
                                        ))}
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
