// DietSelectionPage.jsx
import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import '../CSS/DietSelectionPage.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Navigation, Pagination } from 'swiper/modules';
import foodImg from '../images/food.png';

function DietSelectionPage() {
    const navigate = useNavigate();
    const swiperRef = useRef(null);
    const [isLoading, setIsLoading] = useState(true);
    const [mealData, setMealData] = useState([]);
    // 각 식사별 메뉴 변경 섹션의 열림/닫힘 상태 관리
    const [expandedMenus, setExpandedMenus] = useState({});

    const handleConfirmClick = () => {
        // DietPaymentMainPage가 기대하는 데이터 구조로 변환
        const transformedData = mealData.map(dayData => ({
            day: dayData.day,
            mealOptions: dayData.foodChoiceSets.map(choiceSet => ({
                mealType: choiceSet.foodTimeLabel,
                foodMenus: [choiceSet.selectedFood], // 선택된 음식만 배열로 전달
                count: choiceSet.selectedFood.count
            }))
        }));

        // 로컬스토리지에 변환된 데이터 저장
        localStorage.setItem("Meal", JSON.stringify(transformedData));
        navigate(`/dietpaymentmain`);
    };

    // 메뉴 변경 섹션 토글 함수
    const toggleMenuSection = (dayIndex, mealIndex) => {
        const key = `${dayIndex}-${mealIndex}`;
        setExpandedMenus(prev => ({
            ...prev,
            [key]: !prev[key]
        }));
    };

    // 풍부한 더미 데이터 생성
    const getDummyData = () => {
        const days = [
            "2025-06-30", "2025-07-01", "2025-07-02",
            "2025-07-03", "2025-07-04", "2025-07-05", "2025-07-06"
        ];

        const mealTypes = [
            { key: "Breakfast", label: "아침" },
            { key: "Lunch", label: "점심" },
            { key: "Dinner", label: "저녁" }
        ];

        const sampleFoods = [
            {
                id: 1, name: "김치찌개 정식", image: foodImg,
                price: "8000원", main1: "김치찌개", main2: "계란후라이", side1: "김치", side2: "단무지", side3: "밥",
                calories: "450kcal", carbohydrate: "60g", protein: "20g", fat: "15g", sugar: "5g", sodium: "800mg"
            },
            {
                id: 2, name: "된장찌개 정식", image: foodImg,
                price: "7500원", main1: "된장찌개", main2: "계란후라이", side1: "김치", side2: "콩나물", side3: "밥",
                calories: "420kcal", carbohydrate: "55g", protein: "18g", fat: "12g", sugar: "4g", sodium: "750mg"
            },
            {
                id: 3, name: "불고기 정식", image: foodImg,
                price: "12000원", main1: "불고기", main2: "계란찜", side1: "김치", side2: "콩나물", side3: "밥",
                calories: "580kcal", carbohydrate: "65g", protein: "35g", fat: "18g", sugar: "8g", sodium: "900mg"
            },
            {
                id: 4, name: "생선구이 정식", image: foodImg,
                price: "10000원", main1: "생선구이", main2: "된장찌개", side1: "김치", side2: "시금치나물", side3: "밥",
                calories: "520kcal", carbohydrate: "58g", protein: "30g", fat: "16g", sugar: "6g", sodium: "720mg"
            },
            {
                id: 5, name: "미역국 정식", image: foodImg,
                price: "7000원", main1: "미역국", main2: "계란후라이", side1: "김치", side2: "멸치볶음", side3: "밥",
                calories: "380kcal", carbohydrate: "50g", protein: "15g", fat: "10g", sugar: "3g", sodium: "650mg"
            },
            {
                id: 6, name: "닭갈비 정식", image: foodImg,
                price: "11000원", main1: "닭갈비", main2: "계란찜", side1: "김치", side2: "콩나물", side3: "밥",
                calories: "600kcal", carbohydrate: "70g", protein: "40g", fat: "20g", sugar: "10g", sodium: "950mg"
            }
        ];

        return {
            startDate: "2025-06-30",
            endDate: "2025-07-06",
            dailyFoodPlans: days.map((day, dayIndex) => ({
                day: day,
                foodChoiceSets: mealTypes.map((mealType, mealIndex) => {
                    const availableFoods = [
                        { ...sampleFoods[(dayIndex + mealIndex) % sampleFoods.length] },
                        { ...sampleFoods[(dayIndex + mealIndex + 1) % sampleFoods.length] },
                        { ...sampleFoods[(dayIndex + mealIndex + 2) % sampleFoods.length] }
                    ];

                    return {
                        foodTime: mealType.key,
                        foodTimeLabel: mealType.label,
                        foods: availableFoods,
                        selectedFood: { ...availableFoods[0], count: 1 }, // 첫 번째 음식을 기본 선택
                        selectedIndex: 0 // 선택된 음식의 인덱스
                    };
                })
            }))
        };
    };

    // 메뉴 변경 핸들러
    const handleMenuChange = (dayIndex, mealIndex, foodIndex) => {
        const updatedMealData = [...mealData];
        const selectedFood = updatedMealData[dayIndex].foodChoiceSets[mealIndex].foods[foodIndex];

        updatedMealData[dayIndex].foodChoiceSets[mealIndex].selectedFood = {
            ...selectedFood,
            count: updatedMealData[dayIndex].foodChoiceSets[mealIndex].selectedFood.count
        };
        updatedMealData[dayIndex].foodChoiceSets[mealIndex].selectedIndex = foodIndex;

        setMealData(updatedMealData);
    };

    // 수량 변경 핸들러
    const handleQuantityChange = (dayIndex, mealIndex, change) => {
        const updatedMealData = [...mealData];
        const currentCount = updatedMealData[dayIndex].foodChoiceSets[mealIndex].selectedFood.count;
        const newCount = Math.max(1, Math.min(10, currentCount + change));

        updatedMealData[dayIndex].foodChoiceSets[mealIndex].selectedFood.count = newCount;
        setMealData(updatedMealData);
    };

    useEffect(() => {
        const handleGet = async () => {
            setIsLoading(true);

            console.log('🔄 더미 데이터 모드로 실행합니다.');

            // 로딩 시뮬레이션
            await new Promise(resolve => setTimeout(resolve, 1000));

            try {
                const dummyResult = getDummyData();
                setMealData(dummyResult.dailyFoodPlans);

                localStorage.setItem("checkMealLoad", true);
                localStorage.setItem("startDate", JSON.stringify(dummyResult.startDate));
                localStorage.setItem("endDate", JSON.stringify(dummyResult.endDate));

                console.log('✅ 더미 데이터 로드 완료:', dummyResult.dailyFoodPlans);

            } catch (error) {
                console.error('더미 데이터 로드 실패:', error);
            } finally {
                setIsLoading(false);
            }
        };

        if (!localStorage.getItem("checkMealLoad")) {
            handleGet();
        } else {
            // 저장된 데이터가 있으면 로드하지만, 새로운 구조로 초기화
            handleGet();
        }
    }, []);

    // 스크롤 위치를 복원하는 useEffect
    useEffect(() => {
        const savedScrollIndex = localStorage.getItem("scrollIndex");
        if (swiperRef.current && savedScrollIndex !== null && mealData.length > 0) {
            setTimeout(() => {
                swiperRef.current.swiper.slideTo(Number(savedScrollIndex), 0);
            }, 100);
        }
    }, [mealData]);

    const handleSlideChange = (swiper) => {
        localStorage.setItem("scrollIndex", swiper.activeIndex);
    };

    // 로딩 상태 처리
    if (isLoading) {
        return (
            <div className="diet-selection-page-loading-container">
                <div className="diet-selection-page-loading-spinner"></div>
                <p>식단 데이터를 준비하는 중...</p>
                <small>더미 데이터로 실행됩니다</small>
            </div>
        );
    }

    return (
        <div className="diet-selection-page-main-container">

            {/* 주간 식단 상세 영역 */}
            <div className='diet-selection-page-user-weekly-food-detail'>
                <Swiper
                    ref={swiperRef}
                    spaceBetween={50}
                    slidesPerView={1}
                    navigation
                    pagination={{ clickable: true }}
                    modules={[Navigation, Pagination]}
                    className='diet-selection-page-weekly-food-slide'
                    onSlideChange={handleSlideChange}
                >
                    {mealData.map((dayData, dayIndex) => (
                        <SwiperSlide key={dayIndex} className='diet-selection-page-slide-content'>
                            {/* 날짜 헤더 */}
                            <div className="diet-selection-page-date-header">
                                {new Date(dayData.day).toLocaleDateString('ko-KR', {
                                    month: 'long',
                                    day: 'numeric',
                                    weekday: 'long'
                                })}
                            </div>

                            {/* 식사 시간별 섹션 */}
                            {dayData.foodChoiceSets.map((mealSet, mealIndex) => {
                                const menuKey = `${dayIndex}-${mealIndex}`;
                                const isExpanded = expandedMenus[menuKey];

                                return (
                                    <div key={mealIndex} className="diet-selection-page-meal-section">
                                        <h3 className="diet-selection-page-meal-type-title">
                                            {mealSet.foodTimeLabel}
                                        </h3>

                                        {/* 현재 선택된 식단 카드 */}
                                        <div className="diet-selection-page-selected-meal-card">
                                            <div className="diet-selection-page-meal-card-header">
                                                <h4 className="diet-selection-page-meal-name">{mealSet.selectedFood.name}</h4>
                                                <span className="diet-selection-page-meal-price">{mealSet.selectedFood.price}</span>
                                            </div>

                                            <img
                                                src={mealSet.selectedFood.image}
                                                alt={mealSet.selectedFood.name}
                                                className="diet-selection-page-meal-image"
                                            />

                                            <div className="diet-selection-page-meal-description">
                                                {mealSet.selectedFood.main1}, {mealSet.selectedFood.main2}, {mealSet.selectedFood.side1}, {mealSet.selectedFood.side2}, {mealSet.selectedFood.side3}
                                            </div>

                                            <div className="diet-selection-page-nutrition-info">
                                                <div className="diet-selection-page-nutrition-item">
                                                    <span className="diet-selection-page-nutrition-label">칼로리</span>
                                                    <span className="diet-selection-page-nutrition-value">{mealSet.selectedFood.calories}</span>
                                                </div>
                                                <div className="diet-selection-page-nutrition-item">
                                                    <span className="diet-selection-page-nutrition-label">탄수화물</span>
                                                    <span className="diet-selection-page-nutrition-value">{mealSet.selectedFood.carbohydrate}</span>
                                                </div>
                                                <div className="diet-selection-page-nutrition-item">
                                                    <span className="diet-selection-page-nutrition-label">단백질</span>
                                                    <span className="diet-selection-page-nutrition-value">{mealSet.selectedFood.protein}</span>
                                                </div>
                                            </div>

                                            {/* 수량 조절 */}
                                            <div className="diet-selection-page-quantity-control">
                                                <button
                                                    className="diet-selection-page-quantity-btn"
                                                    onClick={() => handleQuantityChange(dayIndex, mealIndex, -1)}
                                                >
                                                    -
                                                </button>
                                                <span className="diet-selection-page-quantity-value">
                                                    {mealSet.selectedFood.count}
                                                </span>
                                                <button
                                                    className="diet-selection-page-quantity-btn"
                                                    onClick={() => handleQuantityChange(dayIndex, mealIndex, 1)}
                                                >
                                                    +
                                                </button>
                                            </div>
                                        </div>

                                        {/* 메뉴 변경 토글 버튼 */}
                                        <div className="diet-selection-page-menu-toggle">
                                            <button
                                                className="diet-selection-page-menu-toggle-btn"
                                                onClick={() => toggleMenuSection(dayIndex, mealIndex)}
                                            >
                                                <span>다른 메뉴 선택</span>
                                                <span className={`diet-selection-page-toggle-icon ${isExpanded ? 'expanded' : ''}`}>
                                                    ▼
                                                </span>
                                            </button>
                                        </div>

                                        {/* 메뉴 변경 섹션 (접고 펼치기) */}
                                        <div className={`diet-selection-page-menu-change-section ${isExpanded ? 'expanded' : 'collapsed'}`}>
                                            <div className="diet-selection-page-menu-change-content">
                                                <div className="diet-selection-page-menu-options">
                                                    {mealSet.foods.map((food, foodIndex) => (
                                                        <div
                                                            key={foodIndex}
                                                            className={`diet-selection-page-menu-option ${mealSet.selectedIndex === foodIndex ? 'selected' : ''}`}
                                                            onClick={() => handleMenuChange(dayIndex, mealIndex, foodIndex)}
                                                        >
                                                            <img
                                                                src={food.image}
                                                                alt={food.name}
                                                                className="diet-selection-page-option-image"
                                                            />
                                                            <div className="diet-selection-page-option-info">
                                                                <span className="diet-selection-page-option-name">{food.name}</span>
                                                                <span className="diet-selection-page-option-price">{food.price}</span>
                                                            </div>
                                                            {mealSet.selectedIndex === foodIndex && (
                                                                <div className="diet-selection-page-selected-indicator">✓</div>
                                                            )}
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                );
                            })}
                        </SwiperSlide>
                    ))}
                </Swiper>
            </div>

            {/* 확인 버튼 */}
            <div className="diet-selection-page-confirm-section">
                <button className="diet-selection-page-confirm-btn" onClick={handleConfirmClick}>
                    결제 화면으로 이동
                </button>
            </div>
        </div>
    );
}

export default DietSelectionPage;
