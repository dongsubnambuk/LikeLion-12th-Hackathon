import React, { useState, useEffect } from "react";
import { useLocation } from 'react-router-dom';
import '../CSS/MenuSelectionPage.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css'; 
import 'swiper/css/navigation'; 
import 'swiper/css/pagination'; 
import { Navigation, Pagination } from 'swiper/modules'; 
import SelectionMenus from "../components/SelectionMenus";

const MenuSelectionPage = () => {
    const [mealData, setMealData] = useState([]);
    const location = useLocation();
    const { optionIndex, dateIndex } = location.state || {};

    useEffect(() => {
        const storedMeal = localStorage.getItem("Meal");
        if (storedMeal) {
            setMealData(JSON.parse(storedMeal));
        }
    }, []);

    if (!mealData.length) {
        return <div>Loading...</div>;
    }

    // 유효성 검사 추가
    if (!mealData[dateIndex] || !mealData[dateIndex].mealOptions) {
        return <div>잘못된 접근입니다.</div>;
    }

    return (
        <>
            <div className='user-weekly-food-detail'>
                <Swiper
                    spaceBetween={50} /* 슬라이드 간의 간격 조정 */
                    slidesPerView={1} /* 화면에 보일 슬라이드 수 설정 */
                    navigation
                    pagination={{ clickable: true }}
                    modules={[Navigation, Pagination]}
                    className='weekly-food-slide'
                >
                    <SwiperSlide className='slide-content1'>
                        <SelectionMenus mealCardData={mealData} optionIndex={optionIndex} dateIndex={dateIndex} />
                    </SwiperSlide>
                </Swiper>
            </div>
        </>
    );
};

export default MenuSelectionPage;
