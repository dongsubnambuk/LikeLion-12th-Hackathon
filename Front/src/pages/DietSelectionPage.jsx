// DietSelectionPage.jsx
import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/DietSelectionPage.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Spin } from 'antd';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Navigation, Pagination } from 'swiper/modules';
import SelectionMeals from "../components/SelectionMeals";

function DietSelectionPage() {
    const navigate = useNavigate();
    const swiperRef = useRef(null);

    const handleConfirmClick = () => {
        navigate(`/dietpaymentmain`);
    };

    const [mealData, setMealData] = useState([]);

    useEffect(() => {
        const handleGet = async () => {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://3.37.64.39:8000/api/meal/meal-plans/weekly`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json();

            if (response.status === 200) {
                console.log(result);

                const updatedMealPlans = result.dailyMealPlans.map(dayPlan => ({
                    ...dayPlan,
                    mealOptions: dayPlan.mealOptions.map(option => ({
                        ...option,
                        count: 1
                    }))
                }));
                setMealData(updatedMealPlans);
                localStorage.setItem("Meal", JSON.stringify(updatedMealPlans));
                localStorage.setItem("checkMealLoad", true);

                localStorage.setItem("startDate", JSON.stringify(result.startDate));
                localStorage.setItem("endDate", JSON.stringify(result.endDate));

            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };

        if (!localStorage.getItem("checkMealLoad")) {
            handleGet();
        } else {
            const storedMeal = localStorage.getItem("Meal");

            if (storedMeal) {
                setMealData(JSON.parse(storedMeal));
            }
        }
    }, []);

    // 스크롤 위치를 복원하는 useEffect
    useEffect(() => {
        const savedScrollIndex = localStorage.getItem("scrollIndex");
        if (swiperRef.current && savedScrollIndex !== null) {
            swiperRef.current.swiper.slideTo(Number(savedScrollIndex), 0);
        }
    }, []);

    const handleSlideChange = (swiper) => {
        localStorage.setItem("scrollIndex", swiper.activeIndex);
    };

    return (
        <>
            <Header />
            <div className="diet-selection-main-container">
                <div className='diet-selection-user-weekly-food-detail'>
                    <Swiper
                        ref={swiperRef}
                        spaceBetween={50}
                        slidesPerView={1}
                        navigation
                        pagination={{ clickable: true }}
                        modules={[Navigation, Pagination]}
                        className='weekly-food-slide'
                        onSlideChange={handleSlideChange}
                    >
                        {mealData.map((data, index) => (
                            <SwiperSlide key={index} className='slide-content1'>
                                <SelectionMeals mealCardData={data} dateIndex={index} />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
                <div className="selection-meal-confirm" onClick={handleConfirmClick}>
                    <p className="selection-meal-confirm-text">결제 화면으로 이동</p>
                </div>
            </div>
            <BottomNav />
        </>
    );
}

export default DietSelectionPage;
