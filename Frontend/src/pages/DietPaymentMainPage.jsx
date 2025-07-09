import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import '../CSS/DietPaymentMainPage.css';
import logo from '../images/logo.png';

function DietPaymentMainPage() {
    const navigate = useNavigate();
    const [mealData, setMealData] = useState([]);
    const [allMealsPrice, setAllMealsPrice] = useState(0);
    const [totalMeals, setTotalMeals] = useState(0);
    const [error, setError] = useState(null);

    // 안전한 가격 변환 함수
    const safeParsePrice = (price) => {
        try {
            if (typeof price === 'number') {
                return price;
            }
            if (typeof price === 'string') {
                // 문자열에서 숫자만 추출
                const numericPrice = parseInt(price.replace(/[^0-9]/g, ''), 10);
                return isNaN(numericPrice) ? 0 : numericPrice;
            }
            // price가 null, undefined, 또는 다른 타입인 경우
            return 0;
        } catch (error) {
            return 0;
        }
    };

    useEffect(() => {
        try {
            const storedMeal = localStorage.getItem("Meal");
            if (storedMeal) {
                const mealData = JSON.parse(storedMeal);
                
                // 데이터 검증 및 변환
                const transformedData = mealData.map(dayData => ({
                    date: dayData.day || dayData.date || new Date().toISOString().split('T')[0],
                    meals: (dayData.mealOptions || []).map(option => {
                        const foodMenu = option.foodMenus && option.foodMenus[0] ? option.foodMenus[0] : {};
                        return {
                            ...foodMenu,
                            name: foodMenu.name || '식단명 없음',
                            price: foodMenu.price || '0원',
                            image: foodMenu.image || logo,
                            count: option.count || 1,
                            mealType: option.mealType || '식사',
                            main1: foodMenu.main1 || '',
                            main2: foodMenu.main2 || '',
                            side1: foodMenu.side1 || ''
                        };
                    })
                }));
                
                setMealData(transformedData);
                calculateTotalPrice(transformedData);
                calculateTotalMeals(transformedData);
            } else {
                setError('저장된 식단 데이터가 없습니다.');
            }
        } catch (error) {
            setError('식단 데이터를 불러오는 중 오류가 발생했습니다.');
        }
    }, []);

    const calculateTotalPrice = (data) => {
        try {
            const totalPrice = data.reduce((total, day) => {
                return total + day.meals.reduce((dayTotal, meal) => {
                    const price = safeParsePrice(meal.price);
                    const count = typeof meal.count === 'number' ? meal.count : 1;
                    return dayTotal + (price * count);
                }, 0);
            }, 0);
            setAllMealsPrice(totalPrice);
        } catch (error) {
            setAllMealsPrice(0);
        }
    };

    const calculateTotalMeals = (data) => {
        try {
            const total = data.reduce((total, day) => {
                return total + day.meals.reduce((dayTotal, meal) => {
                    const count = typeof meal.count === 'number' ? meal.count : 1;
                    return dayTotal + count;
                }, 0);
            }, 0);
            setTotalMeals(total);
        } catch (error) {
            setTotalMeals(0);
        }
    };

    const handlePaymentConfirmClick = () => {
        try {
            navigate(`/dietpayment`, { state: { price: allMealsPrice, orderDetails: mealData } });
        } catch (error) {
            alert('결제 페이지로 이동하는 중 오류가 발생했습니다.');
        }
    };

    const getDateRange = () => {
        try {
            if (mealData.length > 0) {
                const startDate = new Date(mealData[0].date).toLocaleDateString('ko-KR', {
                    month: 'long',
                    day: 'numeric'
                });
                const endDate = new Date(mealData[mealData.length - 1].date).toLocaleDateString('ko-KR', {
                    month: 'long',
                    day: 'numeric'
                });
                return `${startDate} ~ ${endDate}`;
            }
            return '';
        } catch (error) {
            return '';
        }
    };

    const getMealTypeIcon = (mealType) => {
        // 영어를 한국어로 변환
        let koreanMealType = mealType;
        if (mealType === 'Breakfast' || mealType === 'BREAKFAST') {
            koreanMealType = '아침';
        } else if (mealType === 'Lunch' || mealType === 'LUNCH') {
            koreanMealType = '점심';
        } else if (mealType === 'Dinner' || mealType === 'DINNER') {
            koreanMealType = '저녁';
        }

    };

    const getMealTypeText = (mealType) => {
        if (mealType === 'Breakfast' || mealType === 'BREAKFAST') {
            return '아침';
        } else if (mealType === 'Lunch' || mealType === 'LUNCH') {
            return '점심';
        } else if (mealType === 'Dinner' || mealType === 'DINNER') {
            return '저녁';
        }
        return mealType; // 이미 한국어인 경우 그대로 반환
    };

    const calculateDayTotal = (meals) => {
        try {
            return meals.reduce((total, meal) => {
                const price = safeParsePrice(meal.price);
                const count = typeof meal.count === 'number' ? meal.count : 1;
                return total + (price * count);
            }, 0);
        } catch (error) {
            return 0;
        }
    };

    const calculateMealTotal = (meal) => {
        try {
            const price = safeParsePrice(meal.price);
            const count = typeof meal.count === 'number' ? meal.count : 1;
            return price * count;
        } catch (error) {
            return 0;
        }
    };

    // 에러 상태 처리
    if (error) {
        return (
            <div className="diet-payment-main-page-error-container">
                <div className="diet-payment-main-page-error-content">
                    <div className="diet-payment-main-page-error-icon">⚠️</div>
                    <h2 className="diet-payment-main-page-error-title">데이터 로드 오류</h2>
                    <p className="diet-payment-main-page-error-message">{error}</p>
                    <button 
                        className="diet-payment-main-page-retry-btn"
                        onClick={() => {
                            setError(null);
                            window.location.reload();
                        }}
                    >
                        다시 시도
                    </button>
                </div>
            </div>
        );
    }

    // 데이터가 없는 경우
    if (!mealData || mealData.length === 0) {
        return (
            <div className="diet-payment-main-page-empty-container">
                <div className="diet-payment-main-page-empty-content">
                    <h2 className="diet-payment-main-page-empty-title">주문할 식단이 없습니다</h2>
                    <p className="diet-payment-main-page-empty-message">식단을 선택한 후 다시 시도해주세요.</p>
                    <button 
                        className="diet-payment-main-page-back-btn"
                        onClick={() => navigate('/dietselection')}
                    >
                        식단 선택하러 가기
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="diet-payment-main-page-container">
            {/* 헤더 섹션 */}
            <div className="diet-payment-main-page-header">
                <h1 className="diet-payment-main-page-title">주문 확인</h1>
                <p className="diet-payment-main-page-period">{getDateRange()} 식단 주문서</p>
            </div>

            {/* 주문 요약 섹션 */}
            <div className="diet-payment-main-page-summary">
                <div className="diet-payment-main-page-summary-item">
                    <span className="diet-payment-main-page-summary-label">총 주문 기간</span>
                    <span className="diet-payment-main-page-summary-value">{mealData.length}일</span>
                </div>
                <div className="diet-payment-main-page-summary-item">
                    <span className="diet-payment-main-page-summary-label">총 식단 수량</span>
                    <span className="diet-payment-main-page-summary-value">{totalMeals}개</span>
                </div>
                <div className="diet-payment-main-page-summary-item">
                    <span className="diet-payment-main-page-summary-label">총 결제 금액</span>
                    <span className="diet-payment-main-page-summary-value">{allMealsPrice.toLocaleString()}원</span>
                </div>
            </div>

            {/* 주문 상세 내역 */}
            <div className="diet-payment-main-page-content">
                <h2 className="diet-payment-main-page-section-title">주문 상세 내역</h2>
                
                <div className="diet-payment-main-page-meal-list">
                    {mealData.map((day, dayIndex) => (
                        <div key={dayIndex} className="diet-payment-main-page-day-section">
                            {/* 날짜 헤더 */}
                            <div className="diet-payment-main-page-date-header">
                                <h3 className="diet-payment-main-page-date-title">
                                    {new Date(day.date).toLocaleDateString('ko-KR', {
                                        month: 'long',
                                        day: 'numeric',
                                        weekday: 'long'
                                    })}
                                </h3>
                                <span className="diet-payment-main-page-day-total">
                                    {calculateDayTotal(day.meals).toLocaleString()}원
                                </span>
                            </div>

                            {/* 식단 카드들 */}
                            <div className="diet-payment-main-page-meals-grid">
                                {day.meals.map((meal, mealIndex) => (
                                    <div key={mealIndex} className="diet-payment-main-page-meal-card">
                                        <div className="diet-payment-main-page-meal-header">
                                            <div className="diet-payment-main-page-meal-type">
                                                <span className="diet-payment-main-page-meal-icon">
                                                    {getMealTypeIcon(meal.mealType)}
                                                </span>
                                                <span className="diet-payment-main-page-meal-type-text">
                                                    {getMealTypeText(meal.mealType)}
                                                </span>
                                            </div>
                                            <div className="diet-payment-main-page-meal-quantity">
                                                {meal.count}개
                                            </div>
                                        </div>

                                        <div className="diet-payment-main-page-meal-image-container">
                                            <img 
                                                src={meal.image} 
                                                alt={meal.name}
                                                className="diet-payment-main-page-meal-image"
                                                onError={(e) => {
                                                    e.target.src = logo;
                                                }}
                                            />
                                        </div>

                                        <div className="diet-payment-main-page-meal-info">
                                            <h4 className="diet-payment-main-page-meal-name">{meal.name}</h4>
                                            <div className="diet-payment-main-page-meal-description">
                                                {[meal.main1, meal.main2, meal.side1].filter(Boolean).join(', ') || '구성 메뉴 정보 없음'}
                                            </div>
                                            <div className="diet-payment-main-page-meal-price">
                                                <span className="diet-payment-main-page-unit-price">{meal.price}</span>
                                                <span className="diet-payment-main-page-multiply">×</span>
                                                <span className="diet-payment-main-page-count">{meal.count}</span>
                                                <span className="diet-payment-main-page-total-price">
                                                    = {calculateMealTotal(meal).toLocaleString()}원
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <div className="diet-payment-main-page-payment-section">
                <div className="diet-payment-main-page-final-amount">
                    <span className="diet-payment-main-page-final-label">최종 결제 금액</span>
                    <span className="diet-payment-main-page-final-price">{allMealsPrice.toLocaleString()}원</span>
                </div>
                <button 
                    className="diet-payment-main-page-payment-btn" 
                    onClick={handlePaymentConfirmClick}
                >
                    결제하기
                </button>
            </div>
        </div>
    );
}

export default DietPaymentMainPage;
