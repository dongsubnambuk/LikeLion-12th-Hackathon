import React from 'react';
import { useNavigate } from "react-router-dom";
import '../CSS/MealCard.css';
import '../CSS/SelectionMenuCard.css';
import logo from '../images/logo.png';

const SelectionMenuCard = ({ meals, onChangeMenu }) => {

    const handleChoiceMenu = () => {
        onChangeMenu(meals);
    }

    return (
        <div className="selection-menu-card">
            <h2 className="selection-menu-meal-option">[{meals.mealOption}]</h2>
            <div className="selection-menu-card-body">
                <div className="selection-menu-card-image-container">
                    <img src={logo} alt={meals.title} className="selection-menu-card-image" />
                </div>
                <div className="selection-menu-card-content">
                    <div className="selection-menu-card-info-content">
                        <h3>[{meals.title}]</h3>
                        <p>{meals.description}</p>
                        <div className="selection-menu-card-nutrition">
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
                    </div>
                    <div className="selection-menu-choice-area">
                        <div className="selection-menu-choice-btn" onClick={handleChoiceMenu}>
                            선택<br />하기
                        </div>
                        <p className="selection-meal-price">{meals.price}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SelectionMenuCard;
