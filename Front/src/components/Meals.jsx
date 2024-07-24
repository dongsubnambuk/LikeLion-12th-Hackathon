import React, { useState, useEffect } from 'react';
import MealCard from './MealCard';
import '../CSS/Meals.css';

const Meals = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(true); // 로그인 상태 관리
  const [userName, setUserName] = useState('서동섭'); // 하드코딩된 유저 이름
  const [mealData, setMealData] = useState(null); // 식단 데이터

  useEffect(() => {
    if (isLoggedIn) {
      // 로그인 유저의 식단 데이터 설정
      setMealData({
        date: '2024년 7월 20일 토요일',
        meals: [
          {
            mealType: '아침',
            title: '김치찌개 정식',
            description: '김치찌개, 시금치 나물, 가지볶음 ...',
            imageSrc: '../images/logo.png',
            carbs: 72,
            protein: 32,
            fat: 8,
          },
          {
            mealType: '점심',
            title: '된장찌개 정식',
            description: '된장찌개, 고등어 구이, 시금치 나물 ...',
            imageSrc: 'path/to/image2.jpg',
            carbs: 65,
            protein: 30,
            fat: 10,
          },
          {
            mealType: '저녁',
            title: '불고기 정식',
            description: '불고기, 잡채, 무생채 ...',
            imageSrc: 'path/to/image3.jpg',
            carbs: 80,
            protein: 35,
            fat: 12,
          },
        ],
      });
    } else {
      // 비회원 인기 식단 데이터 설정
      setMealData({
        date: '2024년 7월 20일 토요일',
        meals: [
          {
            mealType: '아침',
            title: '비회원 인기 김치찌개 정식',
            description: '김치찌개, 시금치 나물, 가지볶음 ...',
            imageSrc: '../images/logo.png',
            carbs: 70,
            protein: 30,
            fat: 9,
          },
          {
            mealType: '점심',
            title: '비회원 인기 된장찌개 정식',
            description: '된장찌개, 고등어 구이, 시금치 나물 ...',
            imageSrc: 'path/to/image2.jpg',
            carbs: 68,
            protein: 28,
            fat: 11,
          },
          {
            mealType: '저녁',
            title: '비회원 인기 불고기 정식',
            description: '불고기, 잡채, 무생채 ...',
            imageSrc: 'path/to/image3.jpg',
            carbs: 78,
            protein: 34,
            fat: 13,
          },
        ],
      });
    }
  }, [isLoggedIn]);

  if (!mealData) {
    return <div>Loading...</div>; // 데이터 로딩 중 표시
  }

  return (
    <div className="meals-container">
      <h4>{isLoggedIn ? `${userName}님의 식단` : '가장 많이 선택한 식단'}</h4>
      <p>{mealData.date}</p> {/* 식단 날짜 */}
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
