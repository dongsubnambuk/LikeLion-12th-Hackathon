import React, { useState } from 'react';
import MealCard from './MealCard';
import './Slideshow.css';

const Slideshow = ({ meals }) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const handlePrev = () => {
    setCurrentIndex((prevIndex) => (prevIndex > 0 ? prevIndex - 1 : meals.length - 1));
  };

  const handleNext = () => {
    setCurrentIndex((prevIndex) => (prevIndex < meals.length - 1 ? prevIndex + 1 : 0));
  };

  return (
    <div className="slideshow">
      <button onClick={handlePrev} className="slideshow-button">
        &lt;
      </button>
      <div className="slideshow-content">
        {meals.map((meal, index) => (
          <MealCard
            key={index}
            mealType={meal.mealType}
            title={meal.title}
            description={meal.description}
            style={{ display: currentIndex === index ? 'block' : 'none' }}
          />
        ))}
      </div>
      <button onClick={handleNext} className="slideshow-button">
        &gt;
      </button>
    </div>
  );
};

export default Slideshow;
