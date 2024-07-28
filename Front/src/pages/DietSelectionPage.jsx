import React, { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
import '../CSS/DietSelectionPage.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import { Navigation, Pagination } from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import SelectionMeals from "../components/SelectionMeals";

function DietSelectionpPage() {
    const navigate = useNavigate();

    const handleConfirmClick = (item) => {
        navigate(`/dietpaymentmain`);
    };
    return (
        <>
            <Header />
            <div className="diet-selection-main-container">
                <div className='diet-selection-user-weekly-food-detail'>
                    <Swiper
                        spaceBetween={50} /* 슬라이드 간의 간격 조정 */
                        slidesPerView={1} /* 화면에 보일 슬라이드 수 설정 */
                        navigation
                        pagination={{ clickable: true }}
                        modules={[Navigation, Pagination]}
                        className='weekly-food-slide'
                    >

                        <SwiperSlide className='slide-content1'><SelectionMeals /></SwiperSlide>
                        <SwiperSlide className='slide-content1'><SelectionMeals /></SwiperSlide>
                        <SwiperSlide className='slide-content1'><SelectionMeals /></SwiperSlide>
                    </Swiper>
                </div>
                <div className="selection-meal-confirm" onClick={() => handleConfirmClick()}>
                    <p className="selection-meal-confirm-text">결제 화면으로 이동</p>
                </div>
            </div>
            <BottomNav />
        </>
    );
}

export default DietSelectionpPage;