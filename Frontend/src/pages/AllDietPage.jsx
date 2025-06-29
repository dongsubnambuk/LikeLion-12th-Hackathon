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
    const [mealData, setMealData] = useState([]);
    const [loading, setLoading] = useState(true);

    const handleItemClick = (item) => {
        navigate(`/dietinfo`, { state: { item } });
    };

    // 음식 목록 조회 API
    const getAllFoodsAPI = async () => {
        try {
            const response = await fetch('http://nimn.store/api/foods/plans', {
                method: "GET",
                credentials: 'include',
            });
      
            
            if (response.ok) {
                const data = await response.json();
                return data;
            } else {
                console.error('음식 목록 조회 실패:', response.status);
                return [];
            }
        } catch (error) {
            console.error('음식 목록 조회 에러:', error);
            return [];
        }
    };

    // 이미지 URL 생성 함수
    const getImageUrl = (imageId) => {
        if (!imageId) return cat; // 기본 이미지
        return `http://nimn.store/api/image/${imageId}`;
    };

    // 영양 정보 포맷팅 함수 (문자열 처리)
    const formatNutrition = (food) => {
        const calories = food.calories ? `${food.calories}` : '';
        const protein = food.protein ? ` ${food.protein}` : '';
        const carbs = food.carbohydrate ? ` ${food.carbohydrate}` : '';
        const fat = food.fat ? ` ${food.fat}` : '';
        
        return { calories, protein, carbs, fat };
    };

    // 문자열을 숫자로 안전하게 변환하는 함수
    const parseNutritionValue = (value) => {
        if (!value) return 0;
        const parsed = parseFloat(value);
        return isNaN(parsed) ? 0 : parsed;
    };

    // 가격 포맷팅 함수
    const formatPrice = (price) => {
        if (!price) return '가격 정보 없음';
        return `${parseInt(price).toLocaleString()}원`;
    };

    // 카테고리 매핑 함수
    const getCategoryName = (main1, main2) => {
        if (main1) return main1;
        if (main2) return main2;
        return '기타';
    };

    // 설명 생성 함수
    const generateDescription = (food) => {
        const parts = [];
        if (food.main1) parts.push(food.main1);
        if (food.main2) parts.push(food.main2);
        if (food.side1) parts.push(food.side1);
        if (food.side2) parts.push(food.side2);
        if (food.side3) parts.push(food.side3);
        
        return parts.join(', ') || '상세 정보 없음';
    };

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
                
                // 실제 API 호출
                const result = await getAllFoodsAPI();
                
                if (result && result.length > 0) {
                    // API 응답을 컴포넌트에서 사용하는 형태로 변환
                    const formattedData = result.map(food => {
                        const nutrition = formatNutrition(food);
                        
                        return {
                            id: food.id,
                            name: food.name || '메뉴명 없음',
                            price: formatPrice(food.price),
                            image: getImageUrl(food.image), // 이미지 API 연결
                            category: getCategoryName(food.main1, food.main2),
                            description: generateDescription(food),
                            
                            // 표시용 영양정보 (기존 호환성 유지)
                            calories: nutrition.calories,
                            protein: nutrition.protein,
                            
                            // 모든 영양성분 정보 (상세페이지용)
                            nutritionInfo: {
                                // 원본 문자열 값
                                caloriesString: food.calories || '0',
                                proteinString: food.protein || '0',
                                carbohydrateString: food.carbohydrate || '0',
                                fatString: food.fat || '0',
                                sugarString: food.sugar || '0',
                                sodiumString: food.sodium || '0',
                                
                                // 숫자로 변환된 값
                                calories: parseNutritionValue(food.calories),
                                protein: parseNutritionValue(food.protein),
                                carbohydrate: parseNutritionValue(food.carbohydrate),
                                fat: parseNutritionValue(food.fat),
                                sugar: parseNutritionValue(food.sugar),
                                sodium: parseNutritionValue(food.sodium),
                                
                                // 포맷된 버전 (단위 포함)
                                formattedCalories: nutrition.calories,
                                formattedProtein: nutrition.protein,
                                formattedCarbs: nutrition.carbs,
                                formattedFat: nutrition.fat,
                                formattedSugar: food.sugar ? ` ${food.sugar}` : '',
                                formattedSodium: food.sodium ? ` ${food.sodium}` : ''
                            },
                            
                            // 메뉴 구성 정보
                            menuComponents: {
                                main1: food.main1 || '',
                                main2: food.main2 || '',
                                side1: food.side1 || '',
                                side2: food.side2 || '',
                                side3: food.side3 || ''
                            },
                            
                            // 원본 데이터도 포함 (추가 정보가 필요할 경우)
                            originalData: food
                        };
                    });
                    
                    setMealData(formattedData);
                } else {
                    console.log("음식 데이터가 없습니다.");
                    setMealData([]);
                }

            } catch (error) {
                console.error("Error fetching data:", error);
                alert("데이터를 가져오는 중 오류가 발생했습니다.");
                setMealData([]);
            } finally {
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
                                <img 
                                    src={item.image} 
                                    alt={item.name} 
                                    className="allDietPage_meal_image_content"
                                    onError={(e) => {
                                        e.target.src = cat; // 이미지 로드 실패 시 기본 이미지
                                    }}
                                />
                            </div>
                            <div className="allDietPage_meal_info">
                                <h3 className="allDietPage_meal_info_name">{item.name}</h3>
                                <p className="allDietPage_meal_info_description">{item.description}</p>
                                <div className="allDietPage_nutrition_info">
                                    {item.calories && <span className="allDietPage_nutrition_info1">{item.calories}</span>}
                                    {item.protein && <span className="allDietPage_nutrition_info2">{item.protein}</span>}
                                </div>
                                <div className="allDietPage_price_info">
                                    <span className="allDietPage_price">{item.price}</span>
                                </div>
                            </div>
                        </div>
                    ))}
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

                {mealData.length === 0 && !loading && (
                    <div className="allDietPage_no_data">
                        <p>등록된 음식이 없습니다.</p>
                    </div>
                )}
            </div>
        </>
    );
}

export default AllDietPage;