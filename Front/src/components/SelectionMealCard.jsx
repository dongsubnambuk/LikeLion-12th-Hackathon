import React from 'react';
import { useNavigate } from "react-router-dom";
import '../CSS/MealCard.css';
import '../CSS/SelectionMealCard.css';
import logo from '../images/logo.png';

const SelectionMealCard = ({ meals, mealType, title, description, carbs, protein, fat, count, onCountChange, price }) => {
    const navigate = useNavigate();

    const handleIncrement = (e) => {
        e.stopPropagation(); // 이벤트 버블링 방지
        onCountChange(1);
    };

    const handleDecrement = (e) => {
        e.stopPropagation(); // 이벤트 버블링 방지
        onCountChange(-1);
    };

    const handleChangeMenu = () => {
        navigate(`/menuselection`);
    }

    return (
        <div className="selection-meal-card">
            <div className='title'>
                <h2 className='mealtype'>&lt;{meals.mealType}&gt;</h2>
            </div>
            <div className="selection-meal-card-body">
                <div className="selection-meal-card-image-container">
                    <img src={logo} alt={meals.title} className="selection-meal-card-image" />
                </div>
                <div className="selection-meal-card-content">
                    <div className="selection-meal-card-info-content" onClick={handleChangeMenu}>
                        <h3>[{meals.title}]</h3>
                        <p>{meals.description}</p>
                        <div className="selection-meal-card-nutrition">
                            <div className="nutrition-item">
                                <span className="nutrition-label">탄수화물</span>
                                <span className="nutrition-value">{meals.carbs}g</span>
                            </div>
                            <div className="nutrition-item">
                                <span className="nutrition-label">단백질</span>
                                <span className="nutrition-value">{meals.protein}g</span>
                            </div>
                            <div className="nutrition-item">
                                <span className="nutrition-label">지방</span>
                                <span className="nutrition-value">{meals.fat}g</span>
                            </div>
                        </div>
                        <span className="selection-meal-click-info-text">i : 식단 정보를 터치하여 다른 식단 선택</span>
                    </div>
                    <div className="selection-count">
                        <p className="selection-meal-count-btn" onClick={handleIncrement}>+</p>
                        <span className="selection-count-value">{meals.count}</span>
                        <p className="selection-meal-count-btn" onClick={handleDecrement}>-</p>
                        <p className="selection-meal-price">{meals.price}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SelectionMealCard;
