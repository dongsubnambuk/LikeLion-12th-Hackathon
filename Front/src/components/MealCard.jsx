import React from 'react';
import '../CSS/MealCard.css';
import logo from '../images/logo.png'; //임시로 로고 임포트

const MealCard = ({ mealType, title, description, carbs, protein, fat, count }) => {
  return (
    <div className="meal-card">

      <h2 className='mealtype'>&lt;{mealType}&gt;</h2>

      <div className="meal-card-body">

        <div className="meal-card-image-container">
          <img src={logo} alt={title} className="meal-card-image" />
        </div>

        <div className="meal-card-content">
          <div className="title-and-count">
            <h3>[{title}]</h3>
            <h2>{count}인분</h2>
          </div>
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
