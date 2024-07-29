import React, { useState, useEffect } from 'react';
import SelectionMealCard from './SelectionMealCard';
import '../CSS/SelectionMeals.css';

const SelectionMeals = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);
    const [mealData, setMealData] = useState(null);

    useEffect(() => {
        if (isLoggedIn) {
            setMealData({
                date: '2024년 7월 20일 토요일',
                meals: [
                    {
                        mealType: '아침',
                        title: '김치찌개 정식',
                        description: '김치찌개, 시금치 나물, 가지볶음 ...',
                        imageSrc: '../images/logo.png',
                        carbs: 72,
                        protein: 32,
                        fat: 8,
                        count: 0,
                        price: '6,800원',
                    },
                    {
                        mealType: '점심',
                        title: '된장찌개 정식',
                        description: '된장찌개, 고등어 구이, 시금치 나물 ...',
                        imageSrc: 'path/to/image2.jpg',
                        carbs: 65,
                        protein: 30,
                        fat: 10,
                        count: 0,
                        price: '6,200원',
                    },
                    {
                        mealType: '저녁',
                        title: '불고기 정식',
                        description: '된장찌개, 고등어 구이, 시금치 나물 ...',
                        imageSrc: 'path/to/image3.jpg',
                        carbs: 80,
                        protein: 35,
                        fat: 12,
                        count: 0,
                        price: '7,300원',
                    },
                ],
            });
        } else {
            // 비회원 인기 식단 데이터 설정
            <h1>로그인을 해주세요</h1>
        }
    }, [isLoggedIn]);

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
            <p className="selection-meals-date">{mealData.date}</p>
            {mealData.meals.map((meal, index) => (
                <SelectionMealCard
                    meals={meal}
                    key={index}
                    onCountChange={(delta) => handleCountChange(index, delta, meal.count)}
                />
            ))}
        </div>
    );
};

export default SelectionMeals;
