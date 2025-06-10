import React, { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/autoplay';
import { Pagination, Autoplay } from 'swiper/modules';
import '../CSS/MainPage.css';
import { useNavigate } from 'react-router-dom';

import slideImg1 from '../images/mainSlideImg1.jpeg';
import slideImg2 from '../images/mainSlideImg2.jpeg'; 
import slideImg3 from '../images/mainSlideImg3.png';
import cardImg1 from '../images/mainCardImg1.jpeg';
import cardImg2 from '../images/mainCardImg2.jpeg';
import cardImg3 from '../images/mainCardImg3.jpeg';

const MainPage = () => {
  const [user, setUser] = useState(null);
  const [todayMeals, setTodayMeals] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // 오늘의 식단 샘플 데이터
  const sampleTodayMeals = {
    breakfast: {
      name: "건강한 아침 정식",
      image: cardImg1,
      calories: "520kcal",
      protein: "18g",
      carbs: "45g",
      description: "현미밥, 된장찌개, 김치, 시금치나물"
    },
    lunch: {
      name: "프로틴 파워볼",
      image: cardImg2,
      calories: "480kcal",
      protein: "35g",
      carbs: "25g",
      description: "그릴드 치킨, 퀴노아, 아보카도, 견과류"
    },
    dinner: {
      name: "연어 스테이크 세트",
      image: cardImg3,
      calories: "620kcal",
      protein: "32g",
      carbs: "42g",
      description: "구운 연어, 현미밥, 아스파라거스, 방울토마토"
    }
  };

  const heroSlides = [
    {
      title: "바쁜 당신을 위한\n영양사의 한 끼",
      subtitle: "AI 영양사가 추천하는 맞춤 식단을\n새벽에 받아보세요",
      image: slideImg1,
      cta: "구독 시작하기"
    },
    {
      title: "매일 새벽 6시,\n문 앞까지",
      subtitle: "하루 세 끼를 한 번에 받아\n직장에서도 건강하게",
      image: slideImg2,
      cta: "배송 지역 확인"
    },
    {
      title: "21가지 메뉴 중\n자유롭게",
      subtitle: "일주일간 다양한 메뉴를\n취향에 맞게 선택하세요",
      image: slideImg3,
      cta: "메뉴 보기"
    }
  ];

  const features = [
    {
      title: "새벽 배송",
      description: "매일 새벽 6시 문 앞까지 배송",
      highlight: "출근 전 수령 가능"
    },
    {
      title: "AI 맞춤 추천",
      description: "개인 취향과 영양 필요량 분석한 맞춤 식단",
      highlight: "개인별 최적화"
    },
    {
      title: "영양 균형",
      description: "전문 영양사 감수 균형잡힌 영양 구성",
      highlight: "건강한 한 끼"
    },
    {
      title: "합리적 가격",
      description: "한 끼 6,500원부터 무료 배송",
      highlight: "부담 없는 가격"
    }
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        const storedIsLoggedIn = localStorage.getItem("isLoggedIn") === "true";
        
        if (storedIsLoggedIn) {
          setUser({ name: "김영희", email: "user@example.com" });
          setIsLogin(true);
        }

        setTodayMeals(sampleTodayMeals);
      } catch (error) {
        console.error("Error fetching data: ", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleSubscribeClick = () => {
    if (isLogin) {
      navigate('/dietselection');
    } else {
      navigate('/login');
    }
  };

  const handleMenuClick = () => {
    navigate('/alldiet');
  };

  if (loading) {
    return (
      <div className="main-page-loading-container">
        <div className="main-page-loading-spinner"></div>
        <p>영양 데이터를 불러오는 중...</p>
      </div>
    );
  }

  return (
    <div className="main-page-main-container">
      {/* Hero Slider Section */}
      <section className="main-page-hero-slider">
        <Swiper
          spaceBetween={0}
          slidesPerView={1}
          pagination={{ clickable: true }}
          autoplay={{ delay: 4000, disableOnInteraction: false, pauseOnMouseEnter: true }}
          modules={[Pagination, Autoplay]}
          className="main-page-hero-swiper"
        >
          {heroSlides.map((slide, index) => (
            <SwiperSlide key={index}>
              <div className="main-page-hero-slide">
                <div className="main-page-hero-background">
                  <img src={slide.image} alt={slide.title} />
                  <div className="main-page-hero-overlay"></div>
                </div>
                <div className="main-page-hero-content">
                  <h1>{slide.title}</h1>
                  <p>{slide.subtitle}</p>
                  <button 
                    className="main-page-hero-cta-button"
                    onClick={index === 0 ? handleSubscribeClick : handleMenuClick}
                  >
                    {slide.cta}
                  </button>
                </div>
              </div>
            </SwiperSlide>
          ))}
        </Swiper>
      </section>

      {/* Today's Meals Section */}
      <section className="main-page-today-meals-section">
        <div className="main-page-section-header">
          <h2>
            {isLogin ? `${user?.name}님의 오늘 식단` : '오늘의 추천 식단'}
          </h2>
          <p>영양사가 추천하는 균형잡힌 하루 세 끼</p>
        </div>
        
        <div className="main-page-meals-grid">
          <div className="main-page-meal-card main-page-breakfast">
            <div className="main-page-meal-time">
              <span className="main-page-time-label">아침</span>
            </div>
            <div className="main-page-meal-image">
              <img src={todayMeals.breakfast.image} alt={todayMeals.breakfast.name} />
            </div>
            <div className="main-page-meal-info">
              <h3>{todayMeals.breakfast.name}</h3>
              <p>{todayMeals.breakfast.description}</p>
              <div className="main-page-nutrition-info">
                <span>{todayMeals.breakfast.calories}</span>
                <span>단백질 {todayMeals.breakfast.protein}</span>
              </div>
            </div>
          </div>

          <div className="main-page-meal-card main-page-lunch">
            <div className="main-page-meal-time">
              <span className="main-page-time-label">점심</span>
            </div>
            <div className="main-page-meal-image">
              <img src={todayMeals.lunch.image} alt={todayMeals.lunch.name} />
            </div>
            <div className="main-page-meal-info">
              <h3>{todayMeals.lunch.name}</h3>
              <p>{todayMeals.lunch.description}</p>
              <div className="main-page-nutrition-info">
                <span>{todayMeals.lunch.calories}</span>
                <span>단백질 {todayMeals.lunch.protein}</span>
              </div>
            </div>
          </div>

          <div className="main-page-meal-card main-page-dinner">
            <div className="main-page-meal-time">
              <span className="main-page-time-label">저녁</span>
            </div>
            <div className="main-page-meal-image">
              <img src={todayMeals.dinner.image} alt={todayMeals.dinner.name} />
            </div>
            <div className="main-page-meal-info">
              <h3>{todayMeals.dinner.name}</h3>
              <p>{todayMeals.dinner.description}</p>
              <div className="main-page-nutrition-info">
                <span>{todayMeals.dinner.calories}</span>
                <span>단백질 {todayMeals.dinner.protein}</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="main-page-features-section">
        <div className="main-page-section-header">
          <h2>우리 동네 영양사만의 <br/> 특별함</h2>
          <p>건강하고 편리한 식사 솔루션을 제공합니다</p>
        </div>
        
        <div className="main-page-features-grid">
          {features.map((feature, index) => (
            <div key={index} className="main-page-feature-card">
              <h3>{feature.title}</h3>
              <p>{feature.description}</p>
              <span className="main-page-feature-highlight">{feature.highlight}</span>
            </div>
          ))}
        </div>
      </section>

      {/* CTA Section */}
      <section className="main-page-cta-section">
        <div className="main-page-cta-content">
          <h2>건강한 식습관,<br/>지금 시작하세요!</h2>
          <p>바쁜 일상 속에서도 균형잡힌 영양을 챙기세요</p>
          <button 
            className="main-page-cta-button"
            onClick={handleSubscribeClick}
          >
            구독 시작하기
          </button>
        </div>
      </section>
    </div>
  );
};

export default MainPage;
