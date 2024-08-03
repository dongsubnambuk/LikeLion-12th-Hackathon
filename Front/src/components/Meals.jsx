import React, { useState, useEffect } from 'react';
import MealCard from './MealCard';
import '../CSS/Meals.css';

const Meals = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 관리
  const [userName, setUserName] = useState(''); // 하드코딩된 유저 이름
  const [mealData, setMealData] = useState(null); // 식단 데이터

  useEffect(() => {
    const handleGetUser = async () => {
      const token = localStorage.getItem("token");
      const email = localStorage.getItem("email");

      if (token && email) {
        try {
          const response = await fetch(`http://3.37.64.39:8000/users?email=${email}`, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "Authorization": token,
            }
          });

          const result = await response.json();

          if (response.status === 200) {
            setUserName(result.name);
            setIsLoggedIn(true);
          } else {
            console.log("로그인 실패: ", result.message);
            alert("로그인 실패: " + result.message);
          }
        } catch (error) {
          console.error("Fetch error: ", error);
        }
      } else {
        setIsLoggedIn(false);
      }
    };

    handleGetUser();
  }, []);

  useEffect(() => {
    if (isLoggedIn) {
      // 로그인 유저의 식단 데이터 설정
      setMealData({
        date: '2024년 7월 20일 토요일',
        meals: [
          {
            mealType: '아침',
            title: '김치찌개 정식',
            description: '김치찌개, 시금치 나물, 가지볶음,김치찌개, 시금치 나물, 가지볶음 ...',
            imageSrc: '../images/logo.png',
            carbs: 72,
            protein: 32,
            fat: 8,
            count: 2,
          },
          {
            mealType: '점심',
            title: '된장찌개 정식',
            description: '된장찌개, 고등어 구이, 시금치 나물 ...',
            imageSrc: 'path/to/image2.jpg',
            carbs: 65,
            protein: 30,
            fat: 10,
            count: 3,
          },
          {
            mealType: '저녁',
            title: '불고기 정식',
            description: '된장찌개, 고등어 구이, 시금치 나물 ...',
            imageSrc: 'path/to/image3.jpg',
            carbs: 80,
            protein: 35,
            fat: 12,
            count: 1,
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
            description: '김치찌개, 시금치 나물, 가지볶음,김치찌개, 시금치 나물, 가지볶음 ...',
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
            description: '된장찌개, 고등어 구이, 시금치 나물 ...',
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
      <p style={{marginTop: 10, marginBottom: 10}}>{mealData.date}</p> {/* 식단 날짜 */}
      {mealData.meals.map((meal, index) => (
        <MealCard
          key={index}
          meals={meal}
          isLoggedIn={isLoggedIn} // 로그인 상태 전달
        />
      ))}
    </div>
  );
};

export default Meals;
