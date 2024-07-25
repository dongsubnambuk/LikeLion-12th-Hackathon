import React from 'react';
import '../CSS/MealCard.css';
import logo from '../images/logo.png'; //임시로 로고 임포트

const MealCard = ({ mealType, title, description, imageSrc, carbs, protein, fat }) => {
  return (
    <div className="meal-card">
      <div className="meal-card-body">
        <div className="meal-card-image-container">
          <img src={logo} alt={title} className="meal-card-image" />
        </div>
        <div className="meal-card-content">
          <h3>{title}</h3>
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
