import React from 'react';
import MealCard from './MealCard';
import '../CSS/Meals.css';


const Meals = () => {
  const mealData = {
    date: '2024년 7월 20일 토요일',
    meals: [
      {
        mealType: '아침',
        title: '김치찌개 정식',
        description: '김치찌개, 시금치 나물, 가지볶음 ...',
        imageSrc: '../images/logo.png', // 이미지 경로
        carbs: 72, // 탄수화물 정보
        protein: 32, // 단백질 정보
        fat: 8, // 지방 정보
      },
      {
        mealType: '점심',
        title: '된장찌개 정식',
        description: '된장찌개, 고등어 구이, 시금치 나물 ...',
        imageSrc: 'path/to/image2.jpg', // 이미지 경로
        carbs: 65, // 탄수화물 정보
        protein: 30, // 단백질 정보
        fat: 10, // 지방 정보
      },
      {
        mealType: '저녁',
        title: '불고기 정식',
        description: '불고기, 잡채, 무생채 ...',
        imageSrc: 'path/to/image3.jpg', // 이미지 경로
        carbs: 80, // 탄수화물 정보
        protein: 35, // 단백질 정보
        fat: 12, // 지방 정보
      },
    ],
  };

  return (
    <div className="meals-container">
      <h4>서동섭님의 식단</h4>
      <p>{mealData.date}</p>
      {mealData.meals.map((meal, index) => (
        <MealCard
          key={index}
          mealType={meal.mealType}
          title={meal.title}
          description={meal.description}
          imageSrc={meal.imageSrc} // 이미지 경로 전달
          carbs={meal.carbs} // 탄수화물 정보 전달
          protein={meal.protein} // 단백질 정보 전달
          fat={meal.fat} // 지방 정보 전달
        />
      ))}
    </div>
  );
};

export default Meals;
