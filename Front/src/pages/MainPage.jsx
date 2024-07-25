import React from 'react';
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
import cat2 from '../images/cat2.png';
import food from '../images/food.png';

function MainPage() {
  return (
    <>
      <Header/>
    <div className="swiper-container">
    <Swiper
      spaceBetween={50}
      slidesPerView={1}
      navigation
      pagination={{ clickable: true }}
      autoplay={{ delay: 3000, disableOnInteraction: false }} // Autoplay 설정 추가
      modules={[Navigation, Pagination, Autoplay]} // Swiper에서 사용할 모듈을 추가
      style={{ height: '25vh' }} // 원하는 높이를 설정
    >
      <SwiperSlide className="slide-content">  <img src={logo} className="logoImage" alt="logo" /></SwiperSlide>
      <SwiperSlide className="slide-content"> <img src={cat} className="cat" alt="cat" /> </SwiperSlide>
      <SwiperSlide className="slide-content"><img src={cat} className="cat" alt="cat" /> </SwiperSlide>
      <SwiperSlide className="slide-content"><img src={food} className="cat" alt="food" /> </SwiperSlide>
    </Swiper>
     
  </div>

  <div className='user-food-detail'>
  <Swiper
          spaceBetween={50} /* 슬라이드 간의 간격 조정 */
          slidesPerView={1} /* 화면에 보일 슬라이드 수 설정 */
          navigation
          pagination={{ clickable: true }}
          modules={[Navigation, Pagination]}
          className='food-slide'
        >
          <SwiperSlide className='slide-content1'><Meals /></SwiperSlide>
          <SwiperSlide className='slide-content1'><Meals /></SwiperSlide>
          <SwiperSlide className='slide-content1'><Meals /></SwiperSlide>
        </Swiper>
  </div>
  <BottomNav/>
  </>
 
  );
}

export default MainPage;
