// SelectionMenuCard.jsx
import React, { useState, useEffect } from 'react';
import '../CSS/MealCard.css';
import '../CSS/SelectionMenuCard.css';
import { useNavigate, useSearchParams } from 'react-router-dom';

const SelectionMenuCard = ({ meals, index, optionIndex, dateIndex }) => {
    const navigate = useNavigate();
    const [mealData, setMealData] = useState([]);
    

    useEffect(() => {
        const storedMeal = localStorage.getItem("Meal");
        if (storedMeal) {
            setMealData(JSON.parse(storedMeal));
        }
    }, []);

    if (!mealData.length) {
        return <div>Loading...</div>;
    }

    const changeMenuIndex = (date, type, option) => {
        //console.log('옵션 선택한 것 : ', mealData[date].mealOptions[type].foodMenus[option])
        // mealData[date].mealOptions[type].foodMenus[option] 과 mealData[date].mealOptions[type].foodMenus[0]의 값을 교체

        const updatedMealData = [...mealData]; // 상태를 변경하기 전에 깊은 복사를 수행합니다.
        
        // 현재 메뉴와 교체할 메뉴를 선택합니다.
        const currentMenu = updatedMealData[date].mealOptions[type].foodMenus[option];
        const replacementMenu = updatedMealData[date].mealOptions[type].foodMenus[0];

        // 메뉴를 교체합니다.
        updatedMealData[date].mealOptions[type].foodMenus[option] = replacementMenu;
        updatedMealData[date].mealOptions[type].foodMenus[0] = currentMenu;

        // 변경된 데이터를 로컬스토리지에 저장합니다.
        localStorage.setItem("Meal", JSON.stringify(updatedMealData));
        setMealData(updatedMealData); // 상태 업데이트

    }

    const handleChoiceMenu = () => {


        // 전달할 데이터를 state로 설정
        //console.log('전달 할 값 : ', dateIndex, optionIndex, index - 1)
        changeMenuIndex(dateIndex, optionIndex, index - 1);
        navigate('/dietselection');

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
