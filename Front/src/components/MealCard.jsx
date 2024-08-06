import React from 'react';
import '../CSS/MealCard.css';

const MealCard = ({ meals, isLoggedIn }) => {
  return (
    <div className="meal-card">

      <h2 className='mealtype'>&lt;{meals.mealType}&gt;</h2>

      <div className="meal-card-body">
        <div className="meal-card-image-container">
          <img src={meals.foodMenu.image} alt={meals.foodMenu.name} className="meal-card-image" />
        </div>
        <div className="meal-card-content">
          <div className="title-and-count">
            <h3>[{meals.foodMenu.name}]</h3>
            {isLoggedIn && (
              <h2>{meals.count}인분</h2>
            )}
          </div>
          <p>{meals.foodMenu.main1}, {meals.foodMenu.main2}, {meals.foodMenu.side1}, {meals.foodMenu.side2}, {meals.foodMenu.side3}</p>
          <div className="meal-card-nutrition">
            <div className="nutrition-item">
              <span className="nutrition-label">탄수화물</span>
              <span className="nutrition-value">{meals.foodMenu.carbohydrate}</span>
            </div>
            <div className="nutrition-item">
              <span className="nutrition-label">단백질</span>
              <span className="nutrition-value">{meals.foodMenu.protein}</span>
            </div>
            <div className="nutrition-item">
              <span className="nutrition-label">지방</span>
              <span className="nutrition-value">{meals.foodMenu.fat}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MealCard;
