import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import SelectionMealCard from './SelectionMealCard';
import '../CSS/SelectionMeals.css';

const SelectionMeals = ({ mealCardData, dateIndex }) => {
    const [defaultMenuIndex, setDefaultMenuIndex] = useState(0);
    const [defaultDateIndex, setDefaultDateIndex] = useState(0);
    const [defaultoptionIndex, setDefaultOptionIndex] = useState(0);


    const location = useLocation();

    useEffect(() => {
        // location.state에서 전달된 값을 확인하고 상태를 설정합니다.
        if (location.state) {
            const { dateIndex: stateDateIndex, optionIndex: stateOptionIndex, menuIndex: stateMenuIndex } = location.state;
            console.log('전달값 : ', location.state);
            setDefaultMenuIndex(stateMenuIndex);
            setDefaultDateIndex(stateDateIndex);
            setDefaultOptionIndex(stateOptionIndex);
            //console.log('밀 데이터', mealCardData.mealOptions[defaultMenuIndex].foodMenus[defaultoptionIndex])
            console.log('밀 데이터', mealCardData);
        }
    }, [location.state]);

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
