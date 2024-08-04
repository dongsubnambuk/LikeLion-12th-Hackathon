import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import '../CSS/MealCard.css';
import '../CSS/SelectionMealCard.css';
import logo from '../images/logo.png';

const SelectionMealCard = ({ meals, mealType, optionIndex, dateIndex }) => {
    const navigate = useNavigate();

    const [mealCount, setMealCount] = useState(1);

    const handleIncrement = () => {
        if (mealCount <= 10) {
            setMealCount(mealCount+1);
        }
    };

    const handleDecrement = () => {
        if (mealCount >= 1) {
            setMealCount(mealCount-1);
        }
    };

    const handleChangeMenu = () => {
        navigate(`/menuselection`, { state: { optionIndex, dateIndex } });
    }

    return (
        <div className="selection-meal-card">
            <div className='title'>
                <h2 className='mealtype'>&lt;{mealType}&gt;</h2>
            </div>
            <div className="selection-meal-card-body">
                <div className="selection-meal-card-image-container">
                    <img src={meals.image} alt={meals.name} className="selection-meal-card-image" />
                </div>
                <div className="selection-meal-card-content">
                    <div className="selection-meal-card-info-content" onClick={handleChangeMenu}>
                        <h3>[{meals.name}]</h3>
                        <p>{meals.main1}, {meals.main2}, {meals.side1}, {meals.side2}, {meals.side3}</p>
                        <div className="selection-meal-card-nutrition">
                            <div className="nutrition-item">
                                <span className="nutrition-label">탄수화물</span>
                                <span className="nutrition-value">{meals.carbohydrate}</span>
                            </div>
                            <div className="nutrition-item">
                                <span className="nutrition-label">단백질</span>
                                <span className="nutrition-value">{meals.protein}</span>
                            </div>
                            <div className="nutrition-item">
                                <span className="nutrition-label">지방</span>
                                <span className="nutrition-value">{meals.fat}</span>
                            </div>
                        </div>
                        <span className="selection-meal-click-info-text">i : 식단 정보를 터치하여 다른 식단 선택</span>
                    </div>
                    <div className="selection-count">
                        <p className="selection-meal-count-btn" onClick={handleIncrement}>+</p>
                        <span className="selection-count-value">{mealCount}</span>
                        <p className="selection-meal-count-btn" onClick={handleDecrement}>-</p>
                        <p className="selection-meal-price">{meals.price}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SelectionMealCard;
