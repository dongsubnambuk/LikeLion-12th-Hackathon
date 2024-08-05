// SelectionMenuCard.jsx
import React from 'react';
import '../CSS/MealCard.css';
import '../CSS/SelectionMenuCard.css';
import { useNavigate } from 'react-router-dom';

const SelectionMenuCard = ({ meals, index, optionIndex, dateIndex, onMenuChoice }) => {
    const navigate = useNavigate();

    const handleChoiceMenu = () => {
        // 전달할 데이터를 state로 설정
        console.log('전달 할 값 : ', dateIndex, optionIndex, index - 1)
        navigate('/dietselection', {
            state: {
                dateIndex,
                optionIndex,
                menuIndex: index - 1
            }
        });
    }

    return (
        <div className="selection-menu-card">
            <h2 className="selection-menu-meal-option">&lt;옵션{index}&gt;</h2>
            <div className="selection-menu-card-body">
                <div className="selection-menu-card-image-container">
                    <img src={meals.image} alt={meals.name} className="selection-menu-card-image" />
                </div>
                <div className="selection-menu-card-content">
                    <div className="selection-menu-card-info-content">
                        <h3>[{meals.name}]</h3>
                        <p>{meals.main1}, {meals.main2}, {meals.side1}, {meals.side2}, {meals.side3}</p>
                        <div className="selection-menu-card-nutrition">
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
