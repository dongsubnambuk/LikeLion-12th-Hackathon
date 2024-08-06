import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import SelectionMealCard from './SelectionMealCard';
import '../CSS/SelectionMeals.css';

const SelectionMeals = ({ mealCardData, dateIndex }) => {

    return (
        <div className="selection-meals-container">
            <p className="selection-meals-date">{mealCardData.day}</p>
            {mealCardData.mealOptions.map((mealData, index) => (
                <SelectionMealCard
                    meals={mealData.foodMenus[0]}
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
