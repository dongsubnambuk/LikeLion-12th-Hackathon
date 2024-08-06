import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/DietSelectionPage.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Spin } from 'antd';
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import { Navigation, Pagination } from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import SelectionMeals from "../components/SelectionMeals";

function DietSelectionPage() {
    const navigate = useNavigate();

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
                //console.log(result);
                // localStorage.setItem("Meal", JSON.stringify(result.dailyMealPlans));
                // localStorage.setItem("checkMealLoad", true);
                // setMealData(result.dailyMealPlans);
                const updatedMealPlans = result.dailyMealPlans.map(dayPlan => ({
                    ...dayPlan,
                    mealOptions: dayPlan.mealOptions.map(option => ({
                        ...option,
                        count: 1 // count 요소를 추가. 기본값 1로 세팅
                    }))
                }));
                setMealData(updatedMealPlans);
                localStorage.setItem("Meal", JSON.stringify(updatedMealPlans));
                localStorage.setItem("checkMealLoad", true);
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

    useEffect(() => {
        console.log("ㅇㅇㅇ", mealData);
        console.log("로컬 밀 : ", localStorage.getItem("Meal"));
        console.log("로컬 밀 체크 : ", localStorage.getItem("checkMealLoad"));
    }, [mealData]);

    if (!mealData || mealData.length === 0) {
        return <div style={{ width: "100%", height: "100%", display: 'flex', alignItems: "center", justifyContent: 'center' }}><Spin size="large" /></div>;
    }

    return (
        <>
            <Header />
            <div className="diet-selection-main-container">
                <div className='diet-selection-user-weekly-food-detail'>
                    <Swiper
                        spaceBetween={50}
                        slidesPerView={1}
                        navigation
                        pagination={{ clickable: true }}
                        modules={[Navigation, Pagination]}
                        className='weekly-food-slide'
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
