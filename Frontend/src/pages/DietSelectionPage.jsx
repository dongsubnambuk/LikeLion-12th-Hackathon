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
    // 🔥 추가된 부분: 에러 상태 관리
    const [error, setError] = useState(null);
    // 각 식사별 메뉴 변경 섹션의 열림/닫힘 상태 관리
    const [expandedMenus, setExpandedMenus] = useState({});
    // 모달 상태 관리
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedMealDetail, setSelectedMealDetail] = useState(null);

    const handleConfirmClick = () => {
        // 확인 메시지 추가
        const isConfirmed = window.confirm("7일간의 식단을 모두 선택하셨나요?\n\n선택하신 식단으로 결제 페이지로 이동합니다.");

        if (!isConfirmed) {
            return; // 사용자가 취소를 누르면 함수 종료
        }

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

    // 모달 열기 함수
    const openMealDetailModal = (mealDetail) => {
        setSelectedMealDetail(mealDetail);
        setIsModalOpen(true);
    };

    // 모달 닫기 함수
    const closeMealDetailModal = () => {
        setIsModalOpen(false);
        setSelectedMealDetail(null);
    };

    // 이미지 ID 추출 및 검증 함수
    const extractImageId = (imagePath) => {
        if (!imagePath) return null;
        
        // "/api/image/test28" 형태에서 "test28" 추출
        const match = imagePath.match(/\/api\/image\/(.+)$/);
        if (match && match[1]) {
            const imageId = match[1];
            // 숫자만 포함된 ID인지 확인 (test28 → 28)
            const numericId = imageId.replace(/\D/g, '');
            return numericId || imageId; // 숫자가 있으면 숫자만, 없으면 원본 반환
        }
        return null;
    };

    // 이미지 URL 생성 함수
    const getImageUrl = (imagePath) => {
        if (!imagePath) return foodImg;
        
        const imageId = extractImageId(imagePath);
        if (!imageId) return foodImg;
        
        // 숫자 ID로 이미지 URL 생성
        return `http://nimn.store/api/image/${imageId}`;
    };

    // 실제 API에서 받은 데이터를 내부 구조로 변환
    const transformApiData = (apiData) => {
        const mealTypeMapping = {
            'Breakfast': '아침',
            'Lunch': '점심',
            'Dinner': '저녁'
        };

        const transformedData = apiData.dailyFoodPlans.map(dayPlan => ({
            day: dayPlan.day,
            foodChoiceSets: dayPlan.foodChoiceSets.map(choiceSet => ({
                foodTime: choiceSet.foodTime,
                foodTimeLabel: mealTypeMapping[choiceSet.foodTime] || choiceSet.foodTime,
                foods: choiceSet.foods.map(food => ({
                    ...food,
                    // 이미지 URL 처리
                    image: getImageUrl(food.image),
                    originalImagePath: food.image
                })),
                selectedFood: {
                    ...choiceSet.foods[0], // 첫 번째 음식을 기본 선택
                    count: 1,
                    image: getImageUrl(choiceSet.foods[0].image),
                    originalImagePath: choiceSet.foods[0].image
                },
                selectedIndex: 0
            }))
        }));

        return transformedData;
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
            setError(null); // 🔥 추가된 부분: 요청 시작 시 에러 초기화

            try {
                const response = await fetch('http://nimn.store/api/foods/plans/weekly', {
                    method: "GET",
                    headers: {
                        "accept": "application/json",
                        "Content-Type": "application/json",
                    }
                });

                if (response.ok) {
                    const result = await response.json();

                    // 실제 API 데이터 사용
                    const transformedData = transformApiData(result);
                    setMealData(transformedData);

                    localStorage.setItem("checkMealLoad", true);
                    localStorage.setItem("startDate", JSON.stringify(result.startDate));
                    localStorage.setItem("endDate", JSON.stringify(result.endDate));
                } else {
                    throw new Error(`서버 오류: ${response.status} ${response.statusText}`);
                }

            } catch (error) {
                // 🔥 수정된 부분: 에러 상태 설정
                setError('식단 데이터를 불러오지 못했습니다. 잠시 후 다시 시도해주세요.');
            } finally {
                setIsLoading(false);
            }
        };

        if (!localStorage.getItem("checkMealLoad")) {
            handleGet();
        } else {
            // 저장된 데이터가 있어도 새로운 데이터로 갱신
            localStorage.removeItem("checkMealLoad");
            handleGet();
        }
    }, []);

    const handleSlideChange = (swiper) => {
        localStorage.setItem("scrollIndex", swiper.activeIndex);
    };

    // 로딩 상태 처리
    if (isLoading) {
        return (
            <div className="diet-selection-page-loading-container">
                <div className="diet-selection-page-loading-spinner"></div>
                <p>식단 데이터를 준비하는 중...</p>
                <small>실제 API 데이터를 불러오는 중입니다...</small>
            </div>
        );
    }

    // 🔥 추가된 부분: 에러 상태 처리
    if (error) {
        return (
            <div className="diet-selection-page-error-container">
                <div className="diet-selection-page-error-content">
                    <div className="diet-selection-page-error-icon">⚠️</div>
                    <h2 className="diet-selection-page-error-title">식단 데이터 로드 실패</h2>
                    <p className="diet-selection-page-error-message">{error}</p>
                    <button 
                        className="diet-selection-page-retry-btn"
                        onClick={() => {
                            localStorage.removeItem("checkMealLoad");
                            window.location.reload();
                        }}
                    >
                        다시 시도
                    </button>
                </div>
            </div>
        );
    }

    // 식사 시간에 따른 클래스 반환 함수
    const getMealClass = (foodTime) => {
        switch (foodTime.toLowerCase()) {
            case 'breakfast': return 'breakfast';
            case 'lunch': return 'lunch';
            case 'dinner': return 'dinner';
            default: return '';
        }
    };

    // 날짜 범위 계산
    const getDateRange = () => {
        if (mealData.length > 0) {
            const startDate = new Date(mealData[0].day).toLocaleDateString('ko-KR', {
                month: 'long',
                day: 'numeric'
            });
            const endDate = new Date(mealData[mealData.length - 1].day).toLocaleDateString('ko-KR', {
                month: 'long',
                day: 'numeric'
            });
            return `${startDate} ~ ${endDate}`;
        }
        return '';
    };

    return (
        <div className="diet-selection-page-main-container">
            {/* 안내 문구 섹션 */}
            <div className="diet-selection-page-guide-section">
                <div className="diet-selection-page-guide-header">
                    <h1 className="diet-selection-page-guide-title">🍽️ 주간 식단 선택</h1>
                    <p className="diet-selection-page-guide-period">{getDateRange()}까지의 식단을 선택해주세요</p>
                </div>

                <div className="diet-selection-page-guide-content">
                    <div className="diet-selection-page-guide-item">
                        <span className="diet-selection-page-guide-icon">👈👉</span>
                        <span className="diet-selection-page-guide-text">좌우로 슬라이드하여 각 날짜별 식단을 확인할 수 있습니다</span>
                    </div>

                    <div className="diet-selection-page-guide-item">
                        <span className="diet-selection-page-guide-icon">🔄</span>
                        <span className="diet-selection-page-guide-text">'다른 메뉴 선택' 버튼으로 원하는 메뉴로 변경 가능합니다</span>
                    </div>

                    <div className="diet-selection-page-guide-item">
                        <span className="diet-selection-page-guide-icon">🔢</span>
                        <span className="diet-selection-page-guide-text">+/- 버튼으로 각 식단의 수량을 조절해주세요</span>
                    </div>

                    <div className="diet-selection-page-guide-item">
                        <span className="diet-selection-page-guide-icon">💳</span>
                        <span className="diet-selection-page-guide-text">선택 완료 후 다음 페이지에서 최종 확인 및 결제를 진행합니다</span>
                    </div>
                </div>
            </div>

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
                                const isLastMeal = mealIndex === dayData.foodChoiceSets.length - 1;

                                return (
                                    <div
                                        key={mealIndex}
                                        className={`diet-selection-page-meal-section ${getMealClass(mealSet.foodTime)} ${!isLastMeal ? 'with-divider' : ''}`}
                                    >
                                        <h3 className="diet-selection-page-meal-type-title">
                                            {mealSet.foodTimeLabel}
                                        </h3>

                                        {/* 현재 선택된 식단 카드 */}
                                        <div className="diet-selection-page-selected-meal-card">
                                            <div className="diet-selection-page-meal-card-header">
                                                <h4 className="diet-selection-page-meal-name">{mealSet.selectedFood.name}</h4>
                                                <span className="diet-selection-page-meal-price">{mealSet.selectedFood.price}</span>
                                            </div>
                                            {/* 이미지 섹션 */}
                                            <div className="diet-selection-page-meal-image-section">
                                                <img
                                                    src={mealSet.selectedFood.image}
                                                    alt={mealSet.selectedFood.name}
                                                    className="diet-selection-page-meal-image"
                                                    onError={(e) => {
                                                        e.target.src = foodImg;
                                                    }}
                                                />
                                            </div>

                                            {/* 콘텐츠 섹션 */}
                                            <div className="diet-selection-page-meal-content-section">
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

                                                {/* 상세 정보 버튼 추가 */}
                                                <div className="diet-selection-page-detail-btn-section">
                                                    <button 
                                                        className="diet-selection-page-detail-btn"
                                                        onClick={() => openMealDetailModal(mealSet.selectedFood)}
                                                    >
                                                        📋 상세 정보 보기
                                                    </button>
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
                                                                onError={(e) => {
                                                                    e.target.src = foodImg;
                                                                }}
                                                            />
                                                            <div className="diet-selection-page-option-info">
                                                                <span className="diet-selection-page-option-name">{food.name}</span>
                                                                <span className="diet-selection-page-option-price">{food.price}</span>
                                                            </div>
                                                            {mealSet.selectedIndex === foodIndex && (
                                                                <div className="diet-selection-page-selected-indicator">✓</div>
                                                            )}
                                                            {/* 옵션 메뉴에도 상세 정보 버튼 추가 */}
                                                            <button 
                                                                className="diet-selection-page-option-detail-btn"
                                                                onClick={(e) => {
                                                                    e.stopPropagation();
                                                                    openMealDetailModal(food);
                                                                }}
                                                            >
                                                                ℹ️
                                                            </button>
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        </div>

                                        {/* 식사 구분선 */}
                                        {!isLastMeal && (
                                            <div className="diet-selection-page-meal-divider"></div>
                                        )}
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

            {/* 식단 상세 정보 모달 */}
            {isModalOpen && selectedMealDetail && (
                <div className="diet-selection-page-modal-overlay" onClick={closeMealDetailModal}>
                    <div className="diet-selection-page-modal-content" onClick={(e) => e.stopPropagation()}>
                        <div className="diet-selection-page-modal-header">
                            <h2 className="diet-selection-page-modal-title">{selectedMealDetail.name}</h2>
                            <button 
                                className="diet-selection-page-modal-close-btn"
                                onClick={closeMealDetailModal}
                            >
                                ✕
                            </button>
                        </div>
                        
                        <div className="diet-selection-page-modal-body">
                            <div className="diet-selection-page-modal-image-section">
                                <img 
                                    src={selectedMealDetail.image}
                                    alt={selectedMealDetail.name}
                                    className="diet-selection-page-modal-image"
                                    onError={(e) => {
                                        e.target.src = foodImg;
                                    }}
                                />
                                <div className="diet-selection-page-modal-price">
                                    {selectedMealDetail.price}
                                </div>
                            </div>
                            
                            <div className="diet-selection-page-modal-info-section">
                                <div className="diet-selection-page-modal-section">
                                    <h3 className="diet-selection-page-modal-section-title">🍽️ 구성 메뉴</h3>
                                    <div className="diet-selection-page-modal-menu-grid">
                                        <div className="diet-selection-page-modal-menu-item">
                                            <span className="diet-selection-page-modal-menu-label">메인 1</span>
                                            <span className="diet-selection-page-modal-menu-value">{selectedMealDetail.main1}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-menu-item">
                                            <span className="diet-selection-page-modal-menu-label">메인 2</span>
                                            <span className="diet-selection-page-modal-menu-value">{selectedMealDetail.main2}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-menu-item">
                                            <span className="diet-selection-page-modal-menu-label">반찬 1</span>
                                            <span className="diet-selection-page-modal-menu-value">{selectedMealDetail.side1}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-menu-item">
                                            <span className="diet-selection-page-modal-menu-label">반찬 2</span>
                                            <span className="diet-selection-page-modal-menu-value">{selectedMealDetail.side2}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-menu-item">
                                            <span className="diet-selection-page-modal-menu-label">반찬 3</span>
                                            <span className="diet-selection-page-modal-menu-value">{selectedMealDetail.side3}</span>
                                        </div>
                                    </div>
                                </div>
                                
                                <div className="diet-selection-page-modal-section">
                                    <h3 className="diet-selection-page-modal-section-title">📊 영양 정보</h3>
                                    <div className="diet-selection-page-modal-nutrition-grid">
                                        <div className="diet-selection-page-modal-nutrition-item">
                                            <span className="diet-selection-page-modal-nutrition-label">칼로리</span>
                                            <span className="diet-selection-page-modal-nutrition-value">{selectedMealDetail.calories}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-nutrition-item">
                                            <span className="diet-selection-page-modal-nutrition-label">탄수화물</span>
                                            <span className="diet-selection-page-modal-nutrition-value">{selectedMealDetail.carbohydrate}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-nutrition-item">
                                            <span className="diet-selection-page-modal-nutrition-label">단백질</span>
                                            <span className="diet-selection-page-modal-nutrition-value">{selectedMealDetail.protein}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-nutrition-item">
                                            <span className="diet-selection-page-modal-nutrition-label">지방</span>
                                            <span className="diet-selection-page-modal-nutrition-value">{selectedMealDetail.fat}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-nutrition-item">
                                            <span className="diet-selection-page-modal-nutrition-label">당분</span>
                                            <span className="diet-selection-page-modal-nutrition-value">{selectedMealDetail.sugar}</span>
                                        </div>
                                        <div className="diet-selection-page-modal-nutrition-item">
                                            <span className="diet-selection-page-modal-nutrition-label">나트륨</span>
                                            <span className="diet-selection-page-modal-nutrition-value">{selectedMealDetail.sodium}</span>
                                        </div>
                                    </div>
                                </div>
                                
                                <div className="diet-selection-page-modal-section">
                                    <h3 className="diet-selection-page-modal-section-title">🏷️ 기본 정보</h3>
                                    <div className="diet-selection-page-modal-basic-info">
                                        <div className="diet-selection-page-modal-basic-item">
                                            <span className="diet-selection-page-modal-basic-label">식단 ID</span>
                                            <span className="diet-selection-page-modal-basic-value">{selectedMealDetail.id}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default DietSelectionPage;
