import React, { useState, useRef, useEffect } from "react";
import { useNavigate,useLocation } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
import '../CSS/MenuSelectionPage.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import { Navigation, Pagination } from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import SelectionMenus from "../components/SelectionMenus";

const MenuSelectionpPage = () => {
    const [mealData, setMealData] = useState([]);
    const location = useLocation();
    const { optionIndex, dateIndex } = location.state || {};

    useEffect(() => {
        const handleget = async () => {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://3.37.64.39:8000/api/meal/meal-plans/weekly`, { // 서버 URL을 실제 API 엔드포인트로 변경하세요
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json();

            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                //console.log(result)
                setMealData(result.dailyMealPlans)
            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };
        handleget();
    }, []);

    // useEffect(() => {
    //     console.log(mealData)
    // })

    if (!mealData) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <Header />

            <div className='user-weekly-food-detail'>
                {dateIndex}, {optionIndex}
                <Swiper
                    spaceBetween={50} /* 슬라이드 간의 간격 조정 */
                    slidesPerView={1} /* 화면에 보일 슬라이드 수 설정 */
                    navigation
                    pagination={{ clickable: true }}
                    modules={[Navigation, Pagination]}
                    className='weekly-food-slide'
                >
                        <SwiperSlide className='slide-content1'><SelectionMenus mealCardData={mealData} optionIndex={optionIndex} dateIndex={dateIndex} /></SwiperSlide>
                </Swiper>
            </div>

            <BottomNav />
        </>
    );
};

export default MenuSelectionpPage;