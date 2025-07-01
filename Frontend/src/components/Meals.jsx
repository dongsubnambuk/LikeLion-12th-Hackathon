import React, { useState, useEffect } from 'react';
import MealCard from './MealCard';
import '../CSS/Meals.css';

const Meals = ({ isLoggedIn, userName, mealCardData, dateIndex }) =>  {
  return (
    <div className="meals-container">
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
