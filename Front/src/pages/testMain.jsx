import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentMainPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';

function DietPaymentMainPage() {
    const navigate = useNavigate();
    
    const handlePaymentConfirmClick = () => {
        navigate('/dietpayment', { state: { price: allMealsPrice, orderDetails: mealData } });
    };

    const [mealData, setMealData] = useState(null);
    const [allMealsPrice, setAllMealsPrice] = useState(0);
    const [startDate, setStartDate] = useState(0);
    const [lastDate, setLaseDate] = useState(0);
    const [dateSet, setDateSet] = useState([]);

    useEffect(() => {
        const storedMeal = localStorage.getItem('Meal');
        if (storedMeal) {
            const parsedMealData = JSON.parse(storedMeal);
            setMealData(parsedMealData);
            //calculateTotalPrice(parsedMealData);

            //console.log(localStorage.getItem('Meal'));


            // console.log(parsedMealData[0].day);
            // console.log(parsedMealData[6].day);
            // setStartDate(parsedMealData[0].day);
            // setStartDate(parsedMealData[6].day);

            console.log([parsedMealData[0].day, parsedMealData[6].day]);
            setDateSet([parsedMealData[0].day, parsedMealData[6].day])

        }
    }, []);

    const calculateTotalPrice = (data) => {
        const totalPrice = data.reduce((total, day) => {
            return total + day.meals.reduce((dayTotal, meal) => {
                const mealPrice = meal.mealOptions.reduce((mealTotal, option) => {
                    const optionPrice = parseInt(option.foodMenus[0].price.replace(/[^0-9]/g, ''), 10);
                    return mealTotal + (optionPrice * option.count);
                }, 0);
                return dayTotal + mealPrice;
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

            <BottomNav />
        </>
    );
}

export default DietPaymentMainPage;
