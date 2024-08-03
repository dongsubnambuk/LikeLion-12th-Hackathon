import React, { useState, useEffect } from 'react';
import SelectionMealCard from './SelectionMealCard';
import '../CSS/SelectionMeals.css';

const SelectionMeals = ({ mealCardData, dateIndex }) => {
    const defaultMenuIndex = 0

    return (
        <div className="selection-meals-container">
            <p className="selection-meals-date">{mealCardData.day}</p>
            {mealCardData.mealOptions.map((mealData, index) => (
                <SelectionMealCard
                    meals={mealData.foodMenus[defaultMenuIndex]}
                    mealType={mealData.mealType}
                    key={index}
                    optionIndex={index}
                    dateIndex={dateIndex}
                />
            ))}
        </div>
    );
};

export default SelectionMeals;
