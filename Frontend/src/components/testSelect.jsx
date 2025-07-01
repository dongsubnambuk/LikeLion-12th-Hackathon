import React, { useState, useEffect } from 'react';
import SelectionMealCard from './SelectionMealCard';
import '../CSS/SelectionMeals.css';

const SelectionMeals = () => {

    const [mealData, setMealData] = useState(null);

    useEffect(() => {
        const handleget = async () => {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://3.37.64.39:8000/api/meal/meal-plans/weekly`, { // 서버 URL을 실제 API 엔드포인트로 변경하세요
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json();

            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                console.log(result)
                setMealData(result.dailyMealPlans)
            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };
        handleget();
    }, []);

    const handleCountChange = (index, delta, prevcount) => {
        setMealData(prevData => {
            const newMeals = [...prevData.meals];
            newMeals[index].count = Math.max(prevcount + delta, 0); // count는 0 이하로 내려가지 않도록 설정
            return {
                ...prevData,
                meals: newMeals
            };
        });
    };

    if (!mealData) {
        return <div>Loading...</div>;
    }

    return (
        <div className="selection-meals-container">
            {mealData.map((dayPlan, dayIndex) => (
                <div key={dayIndex} className="day-plan">
                    <p className="selection-meals-date">{dayPlan.day}</p>
                    {dayPlan.mealOptions.map((mealOption, optionIndex) => (
                        <div className="meal-option">
                            {mealOption.foodMenus.map((meal, mealIndex) => (
                                <SelectionMealCard
                                    key={mealIndex}
                                    meals={meal}
                                    onCountChange={(delta) => handleCountChange(mealIndex, delta)}
                                />
                            ))}
                        </div>
                    ))}
                </div>
            ))}
        </div >


    );
};

export default SelectionMeals;
