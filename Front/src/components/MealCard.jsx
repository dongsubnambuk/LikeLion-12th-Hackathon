import React from 'react';
import '../CSS/MealCard.css';
import logo from '../images/logo.png'; //임시로 로고 임포트

const MealCard = ({ mealType, title, description, carbs, protein, fat, count }) => {
  return (
    <div className="meal-card">
      <div className='title'>
        <h2 className='mealtype'>[{mealType}]</h2>
      </div>
      <div className="count">
        <span className="count-title">메뉴 개수 : </span>
        <span className="count-value">{count}개</span>
      </div>

      <div className="meal-card-body">

        <div className="meal-card-image-container">
          <img src={logo} alt={title} className="meal-card-image" />
        </div>

        <div className="meal-card-content">

          <h3>[{title}]</h3>
          <p>{description}</p>
          <div className="meal-card-nutrition">
            <div className="nutrition-item">
              <span className="nutrition-label">탄수화물</span>
              <span className="nutrition-value">{carbs}g</span>
            </div>
            <div className="nutrition-item">
              <span className="nutrition-label">단백질</span>
              <span className="nutrition-value">{protein}g</span>
            </div>
            <div className="nutrition-item">
              <span className="nutrition-label">지방</span>
              <span className="nutrition-value">{fat}g</span>
            </div>

          </div>

        </div>

      </div>

    </div>
  );
};

export default MealCard;
