import React, { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { useLocation } from "react-router";
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import '../CSS/MainPage.css';
import '../components/Meals';
import { Spin } from 'antd';
import { Navigation, Pagination, Autoplay } from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import Meals from '../components/Meals';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';
import cat from '../images/cat.png';
import food from '../images/food.png';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const MainPage = () => {
  const [user, setUser] = useState(null);
  const [mealData, setMealData] = useState(null); // 식단 데이터
  const [nowDate, setNowDate] = useState('');
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    const storedIsLoggedIn = localStorage.getItem("isLoggedIn") === "true";
    const storedIsPay = localStorage.getItem("isPay") === "true";
    const token = localStorage.getItem("token");
    const email = localStorage.getItem("email");

    console.log("메인 결제 유무 ",storedIsPay);

    if (storedIsLoggedIn && token && email && storedIsPay) {
      handleGetUser(token, email);
      setIsLogin(true);
    } else {
      handleOfflineLogin();
      handleGetOfflineDiet();
    }

    const getNowDate = () => {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      setNowDate(`${year}-${month}-${day}`);
    };

    getNowDate();
  }, []);

  useEffect(() => {
    if (user) {
      handleGetUserDiet(user.name, user.email, user.token);
    }
  }, [user]);
  useEffect(() => {
    const email = localStorage.getItem("email");
    if (!email) return;

    const socket = new SockJS('http://nutrihub.kro.kr:14000/ws');
    const client = Stomp.over(socket);

    client.connect({}, () => {
      console.log("Connected to WebSocket");

      const dietSubscription = client.subscribe(`/topic/notification/diet/${email}`, message => {
        console.log("Diet Notification: ", message.body);
      });

      const paymentSubscription = client.subscribe(`/topic/notification/payment/${email}`, message => {
        console.log("Payment Notification: ", message.body);
      });

      return () => {
        dietSubscription.unsubscribe();
        paymentSubscription.unsubscribe();
        client.disconnect(() => {
          console.log('Disconnected');
        });
      };
    }, error => {
      console.error('Connection error: ', error);
    });

    return () => {
      if (client && client.connected) {
        client.disconnect(() => {
          console.log('Disconnected');
        });
      }
    };
  }, []);


  const handleGetUser = async (token, email) => {

    try {
      const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        }
      });

      const result = await response.json();

      if (response.status === 200) {
        //console.log("로그인 성공 및 데이터 저장")
        setUser({ name: result.name, email, token });
      } else {
        console.log("로그인 실패: ", result.message);
        alert("로그인 실패: " + result.message);
      }
    } catch (error) {
      console.error("Fetch error: ", error);
    }
  };


  // 개인 식단 조회
  const handleGetUserDiet = async (userName, userEmail, userToken) => {

    try {
      //console.log("로그인 됨")
      const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/read/${userEmail}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": userToken,
        }
      });

      const result = await response.json();

      if (response.status === 200) {
        //console.log(result.dailyDiets);
        //console.log("식단 불러오기 성공")
        setMealData(result.dailyDiets);
      } else {
        console.log("식단 불러오기 실패");
        alert("식단 실패: " + result.message);
      }
    } catch (error) {
      console.error("Fetch error: ", error);
    }
  }

  const handleOfflineLogin = async () => {

    //console.log("오프라인 로그인 시도")

    const response = await fetch('http://3.37.64.39:8000/api/users/login', { // 서버 URL을 실제 API 엔드포인트로 변경하세요
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: "user@naver.com",
        password: "123456",
    }),
    });

    const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환

    if (response.status === 200) { // 응답 status가 200 OK 일 경우
      // Store token in local storage
      localStorage.setItem("token", result.token);  // 로그인 성공 시 보내주는 토큰 localStorage에 저장
      //console.log("오프라인 로그인 성공")

    } else {
      console.log("로그인 실패");
      alert("로그인 실패: " + result.message);
    }

  }

  const handleGetOfflineDiet = async () => {

    //console.log("오프라인 식단을 불러옵니다.")

    const token = localStorage.getItem("token");

    try {
      const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/read/user@naver.com`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        }
      });

      const result = await response.json();

      if (response.status === 200) {
        //console.log("오프라인 식단 1 : ", result.dailyDiets);
        //console.log("오프라인 식단 불러오기 성공")
        setMealData(result.dailyDiets);
      } else {
        console.log("오프라인 식단 불러오기 실패");
        alert("오프라인 식단 실패: " + result.message);
      }
    } catch (error) {
      console.error("Fetch error: ", error);
    }
  }




  // 로그인 안 하면 주석처리, 하면 주석 해제해야 오류 안 남
  if (!mealData) {
    return (
      <div style={{ width: "100%", height: "100%", display: 'flex', alignItems: "center", justifyContent: 'center' }}>
        <Spin size="large" />
      </div>
    );
  }


  return (
    <>
      <Header />
      <div className="swiper-container">
        <Swiper
          spaceBetween={50}
          slidesPerView={1}
          navigation
          pagination={{ clickable: true }}
          autoplay={{ delay: 3000, disableOnInteraction: false }}
          modules={[Navigation, Pagination, Autoplay]}
          style={{ height: '23vh' }}
        >
          <SwiperSlide className="slide-content">
            <img src={logo} className="logoImage" alt="logo" />
          </SwiperSlide>
          <SwiperSlide className="slide-content">
            <img src={cat} className="cat" alt="cat" />
          </SwiperSlide>
          <SwiperSlide className="slide-content">
            <img src={cat} className="cat" alt="cat" />
          </SwiperSlide>
          <SwiperSlide className="slide-content">
            <img src={food} className="cat" alt="food" />
          </SwiperSlide>
        </Swiper>
      </div>

      {/* 비회원이거나 식단을 신청하지 않은 사람 */}
      {isLogin == false ?
        <div className="user-food-detail">
          <h4 style={{ textAlign: 'center', marginTop: 15 }}>가장 많이 선택한 식단</h4>
          <Swiper
            spaceBetween={50}
            slidesPerView={1}
            navigation
            pagination={{ clickable: true }}
            modules={[Navigation, Pagination]}
            className="food-slide"
          >
            {mealData.map((data, index) => (
              <SwiperSlide key={index} className="slide-content1">
                <Meals
                  mealCardData={data}
                  isLoggedIn={isLogin}
                />
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
        :
        <div className="user-food-detail">
          <h4 style={{ textAlign: 'center', marginTop: 15 }}>오늘 {user.name}님의 식단</h4>
          {mealData.map((data, index) => (
            <div>
              {data.date == nowDate ?
                <SwiperSlide key={index} className="slide-content1">
                  <Meals
                    mealCardData={data}
                    userName={user.name}
                    isLoggedIn={isLogin}
                  />
                </SwiperSlide> :
                <>
                </>
              }
            </div>
          ))}
        </div>
      }

      <BottomNav />
    </>
  );
}

export default MainPage;
