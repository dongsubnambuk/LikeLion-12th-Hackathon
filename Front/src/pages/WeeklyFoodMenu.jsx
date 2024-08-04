import React, { useState, useEffect } from 'react';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
import '../CSS/WeeklyFoodMenu.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Spin } from 'antd';
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import { Navigation, Pagination } from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import Meals from "../components/Meals";

const WeeklyFoodMenu = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 관리
  const [userName, setUserName] = useState(''); // 하드코딩된 유저 이름
  const [userEmail, setUserEmail] = useState(''); // 유저 이메일
  const [userToken, setUserToken] = useState(''); // 유저 토큰
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
            console.log("로그인 성공 및 데이터 저장")
            setUserName(result.name);
            setIsLoggedIn(true);
            setUserEmail(email);
            setUserToken(token);
          } else {
            console.log("로그인 실패: ", result.message);
            alert("로그인 실패: " + result.message);
          }
        } catch (error) {
          console.error("Fetch error: ", error);
        }
      }
    };


    handleGetUser();
  }, []);


  // 개인 식단 조회
  useEffect(() => {

    const handleGetUserDiet = async () => {

      if (isLoggedIn) {
        console.log("로그인 됨")
        const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/read/${userEmail}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": userToken,
          }
        });

        const result = await response.json();

        if (response.status === 200) {
          console.log(result.dailyDiets);
          console.log("식단 불러오기 성공")
          setMealData(result.dailyDiets);
        } else {
          console.log("식단 불러오기 실패");
          alert("식단 실패: " + result.message);
        }
      }
      else {
        console.log("로그인 안 됨")
      }
    }
    console.log("유저 이메일 =", userEmail)

    if (isLoggedIn) {
      handleGetUserDiet();
    }
  }, [isLoggedIn, userEmail, userToken]);

  if (!mealData) {
    return <div style={{ width: "100%", height: "100%", display: 'flex', alignItems: "center", justifyContent: 'center' }}><Spin size="large" /></div>;
  }

  return (
    <>
      <Header />

      <div className='user-weekly-food-detail'>
        <Swiper
          spaceBetween={50} /* 슬라이드 간의 간격 조정 */
          slidesPerView={1} /* 화면에 보일 슬라이드 수 설정 */
          navigation
          pagination={{ clickable: true }}
          modules={[Navigation, Pagination]}
          className='weekly-food-slide'
        >

          {mealData.map((data, index) => (
            <SwiperSlide key={index} className="slide-content1">
              <Meals
                mealCardData={data}
                userName={userName}
                isLoggedIn={isLoggedIn}
              />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>

      <BottomNav />
    </>
  );
}

export default WeeklyFoodMenu;