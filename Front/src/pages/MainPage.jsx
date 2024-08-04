import React, { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import '../CSS/MainPage.css';
import '../components/Meals';
import { Navigation, Pagination ,Autoplay} from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import Meals from '../components/Meals';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';
import cat from '../images/cat.png';
import food from '../images/food.png';

const MainPage = () => {
  const [mealData, setMealData] = useState(null);

  useEffect(() => {
    // 서버로부터 식단 데이터를 가져오는 부분
    // 예시로 하드코딩된 데이터를 사용
    setMealData([
      {
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
      },
      {
        date: '2024년 7월 21일 일요일',
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
      }
    ]);
  }, []);

  if (!mealData) {
    return <div>Loading...</div>;
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

      <div className="user-food-detail">
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
              <Meals mealData={data} />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
      <BottomNav />
    </>
  );
}

export default MainPage;
