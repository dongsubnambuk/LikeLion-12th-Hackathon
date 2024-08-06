import React, { useState, useEffect } from 'react';
import MealCard from './MealCard';
import '../CSS/Meals.css';

const Meals = ({ isLoggedIn, userName, mealCardData, dateIndex }) =>  {
  // useEffect(() => {
  // console.log("밀 카드 데이터 : ", mealCardData);
  // })

  return (
    <div className="meals-container">
      {/* <h4>{isLoggedIn ? `${userName}님의 식단` : '가장 많이 선택한 식단'}</h4> */}
      <p style={{ marginBottom: 10 }}>{mealCardData.day}</p> {/* 식단 날짜 */}
      {mealCardData.mealOptions.map((meal, index) => (
        <MealCard
          key={index}
          meals={meal}
          isLoggedIn={isLoggedIn} // 로그인 상태 전달
        />
      ))}
    </div>
  );
};

export default Meals;
