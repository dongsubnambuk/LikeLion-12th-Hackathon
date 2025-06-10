import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import '../CSS/AllDietPage.css';
import cat from '../images/cat2.png';

function AllDietPage() {
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState(0);
    const [isAnimating, setIsAnimating] = useState(false);
    const [animationClass, setAnimationClass] = useState('');

    const handleItemClick = (item) => {
        navigate(`/dietinfo`, { state: { item } });
    };


    const [mealData, setMealData] = useState([]);
    const [loading, setLoading] = useState(true);

    const animatePageTransition = (direction, callback) => {
        if (isAnimating) return;
        
        setIsAnimating(true);
        
        const outClass = direction === 'next' ? 'slide-out-left' : 'slide-out-right';
        setAnimationClass(outClass);
        
        setTimeout(() => {
            callback();
            const inClass = direction === 'next' ? 'slide-in-right' : 'slide-in-left';
            setAnimationClass(inClass);
            
            setTimeout(() => {
                setAnimationClass('slide-in-center');
                setTimeout(() => {
                    setAnimationClass('');
                    setIsAnimating(false);
                }, 500);
            }, 50);
        }, 250);
    };

    // 예시 데이터
    const mockData = [
        {
            id: 1,
            name: "건강한 아침 정식",
            price: "12,000원",
            image: cat,
            category: "아침",
            description: "현미밥, 된장찌개, 김치, 시금치나물",
            calories: "520kcal",
            protein: "단백질 18g"
        },
        {
            id: 2,
            name: "프로틴 파워볼",
            price: "8,500원",
            image: cat,
            category: "점심",
            description: "그릴드 치킨, 퀴노아, 아보카도, 견과류",
            calories: "480kcal",
            protein: "단백질 35g"
        },
        {
            id: 3,
            name: "스파게티 카르보나라",
            price: "15,000원",
            image: cat,
            category: "저녁",
            description: "파스타, 베이컨, 달걀, 파마산 치즈",
            calories: "650kcal",
            protein: "단백질 35g"
        },
        {
            id: 4,
            name: "초밥 세트",
            price: "18,000원",
            image: cat,
            category: "저녁",
            description: "연어, 참치, 새우, 간장, 와사비",
            calories: "520kcal",
            protein: "단백질 45g"
        },
        {
            id: 5,
            name: "치킨 샐러드 볼",
            price: "11,000원",
            image: cat,            
            category: "점심",
            description: "그릴드 치킨, 아보카도, 견과류, 방울토마토",
            calories: "420kcal",
            protein: "단백질 32g"
        },
        {
            id: 6,
            name: "마르게리타 피자",
            price: "16,000원",
            image: cat,            
            category: "저녁",
            description: "토마토 소스, 모짜렐라, 바질",
            calories: "720kcal",
            protein: "단백질 32g"
        },
        {
            id: 7,
            name: "비빔밥",
            price: "9,000원",
            image: cat,            
            category: "점심",
            description: "나물, 쌀밥, 고추장, 계란",
            calories: "450kcal",
            protein: "단백질 18g"
        },
        {
            id: 8,
            name: "새우볶음밥",
            price: "10,500원",
            image: cat,            
            category: "점심",
            description: "새우, 쌀밥, 달걀, 채소",
            calories: "520kcal",
            protein: "단백질 26g"
        },
        {
            id: 9,
            name: "연어 스테이크",
            price: "22,000원",
            image: cat,            
            category: "저녁",
            description: "그릴드 연어, 아스파라거스, 레몬",
            calories: "380kcal",
            protein: "단백질 48g"
        },
        {
            id: 10,
            name: "된장찌개",
            price: "7,500원",
            image: cat,            
            category: "점심",
            description: "된장, 두부, 파, 쌀밥",
            calories: "380kcal",
            protein: "단백질 22g"
        }
    ];

    // 슬라이드 기능을 위한 설정
    const itemsPerPage = 4; 
    const totalPages = Math.ceil(mealData.length / itemsPerPage);
    const showSlideControls = mealData.length > 4;

    const getCurrentPageItems = () => {
        if (!showSlideControls) return mealData;
        
        const startIndex = currentPage * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        return mealData.slice(startIndex, endIndex);
    };

    const nextPage = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage(currentPage + 1);
        }
    };

    const prevPage = () => {
        if (currentPage > 0) {
            setCurrentPage(currentPage - 1);
        }
    };

    const goToPage = (pageIndex) => {
        setCurrentPage(pageIndex);
    };

    useEffect(() => {
        const handleGet = async () => {
            try {
                setLoading(true);
                
                // 실제 API 호출 (주석 처리)
                /*
                const token = localStorage.getItem("token");
                const response = await fetch(`http://3.37.64.39:8000/api/meal/food`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": token,
                    }
                });

                if (response.status === 403) {
                    console.error("403 Forbidden: Access denied.");
                    alert("접근이 거부되었습니다. 인증 토큰을 확인하세요.");
                    return;
                }

                const result = await response.json();

                if (response.ok) {
                    setMealData(result);
                } else {
                    console.log("실패");
                    alert("실패: " + result.message);
                }
                */

                // 목 데이터 사용 (개발용)
                setTimeout(() => {
                    setMealData(mockData);
                    setLoading(false);
                }, 1000);

            } catch (error) {
                console.error("Error fetching data:", error);
                alert("데이터를 가져오는 중 오류가 발생했습니다.");
                setLoading(false);
            }
        };
        handleGet();
    }, []);

    if (loading) {
        return (
            <div className="allDietPage_loading_container">
                <div className="allDietPage_loading_spinner"></div>
                <p className="allDietPage_loading_spinner_text">영양 데이터를 불러오는 중...</p>
            </div>
        );
    }

    const currentItem = mealData[currentPage];

    return (
        <>
            <div className="allDietPage_container">
                <div className="allDietPage_meals_grid">
                    {getCurrentPageItems().map((item) => (
                        <div key={item.id} className="allDietPage_meal_card" onClick={() => handleItemClick(item)}>
                            <div className="allDietPage_meal_time">
                                <span className="allDietPage_time_label">{item.category}</span>
                            </div>
                            <div className="allDietPage_meal_image">
                                <img src={item.image} alt={item.name} className="allDietPage_meal_image_content"/>
                            </div>
                            <div className="allDietPage_meal_info">
                                <h3 className="allDietPage_meal_info_name">{item.name}</h3>
                                <p className="allDietPage_meal_info_description">{item.description}</p>
                                <div className="allDietPage_nutrition_info">
                                    <span className="allDietPage_nutrition_info1">{item.calories}</span>
                                    <span className="allDietPage_nutrition_info2">{item.protein}</span>
                                </div>
                                <div className="allDietPage_price_info">
                                    <span className="allDietPage_price">{item.price}</span>
                                </div>
                            </div>
                        </div>
                    ))}
                    
                    {/* 빈 공간 채우기 (8개 미만일 때) */}
                    {getCurrentPageItems().length < 8 && 
                        Array.from({ length: 8 - getCurrentPageItems().length }, (_, index) => (
                            <div key={`empty-${index}`} className="allDietPage_empty_card"></div>
                        ))
                    }
                </div>

                
                {showSlideControls && (
                    <div className="allDietPage_slide_controls">
                        <button 
                            className="allDietPage_nav_button allDietPage_prev_button"
                            onClick={prevPage}
                            disabled={currentPage === 0}
                        >
                            <LeftOutlined />
                        </button>

                        <div className="allDietPage_indicators">
                            {Array.from({ length: totalPages }, (_, index) => (
                                <div
                                    key={index}
                                    className={`allDietPage_indicator ${index === currentPage ? 'active' : ''}`}
                                    onClick={() => goToPage(index)}
                                />
                            ))}
                        </div>

                        <button 
                            className="allDietPage_nav_button allDietPage_next_button"
                            onClick={nextPage}
                            disabled={currentPage === totalPages - 1}
                        >
                            <RightOutlined />
                        </button>
                    </div>
                )}
            </div>
        </>
    );
}

export default AllDietPage;