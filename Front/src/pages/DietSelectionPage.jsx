import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
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

    const updateMealData = (index, newMealCardData) => {
        setMealData(prevMealData => {
            const newMealData = [...prevMealData];
            newMealData[index] = newMealCardData;
            return newMealData;
        });
    };

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
                setMealData(result.dailyMealPlans);
            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };
        handleGet();

        console.log("ss", mealData);
    }, []);

    const location = useLocation();

    const swapArrayElements = (arr, index1, index2) => {
        const newArr = [...arr]; // 원본 배열을 복사해서 새로운 배열을 만듦
        const temp = newArr[index1]; // 첫 번째 요소를 임시 변수에 저장
        newArr[index1] = newArr[index2]; // 첫 번째 위치에 두 번째 요소를 할당
        newArr[index2] = temp; // 두 번째 위치에 첫 번째 요소를 할당
        return newArr;
    }

    useEffect(() => {
        if (location.state) {
            const { dateIndex: stateDateIndex, optionIndex: stateOptionIndex, menuIndex: stateMenuIndex } = location.state;
            console.log('전달값 : ', location.state);
            console.log('전달값12 : ', mealData);
    
            // mealData가 업데이트된 후의 값을 반영하도록 대기
            const updatedFoodMenus = mealData[stateDateIndex]?.mealOptions[stateOptionIndex]?.foodMenus
                ? swapArrayElements(mealData[stateDateIndex].mealOptions[stateOptionIndex].foodMenus, 0, stateMenuIndex)
                : [];
    
            const updatedMealCardData = {
                ...mealData,
                [stateDateIndex]: {
                    ...mealData[stateDateIndex],
                    mealOptions: mealData[stateDateIndex]?.mealOptions.map((option, idx) => 
                        idx === stateOptionIndex ? { ...option, foodMenus: updatedFoodMenus } : option
                    )
                }
            };
    
            updateMealData(updatedMealCardData);
        }
    }, [mealData]); 

    useEffect(() => {
        console.log("ㅇㅇㅇ",mealData);
    }, [mealData]);

    if (!mealData) {
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
                                <SelectionMeals mealCardData={data} dateIndex={index} 
                                 updateMealData={(newMealCardData) => updateMealData(index, newMealCardData)}
                                />
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
