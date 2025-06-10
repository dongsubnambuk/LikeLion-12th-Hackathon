import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Card, Spin } from 'antd';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import '../CSS/AllDietPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

const { Meta } = Card;

function AllDietPage() {
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState(0);

    const handleItemClick = (item) => {
        navigate(`/dietinfo`, { state: { item } });
    };

    const [mealData, setMealData] = useState([]);
    const [loading, setLoading] = useState(true);

    // 예시 데이터
    const mockData = [
        {
            id: 1,
            name: "불고기 정식",
            price: "12,000원",
            image: "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400&h=300&fit=crop",
            category: "한식",
            description: "구운 소고기, 쌀밥, 김치, 된장국",
            calories: "580kcal",
            protein: "단백질 42g"
        },
        {
            id: 2,
            name: "김치찌개",
            price: "8,500원",
            image: "https://images.unsplash.com/photo-1582049165295-cb3e3d86c1c6?w=400&h=300&fit=crop",
            category: "한식",
            description: "김치, 돼지고기, 두부, 쌀밥",
            calories: "420kcal",
            protein: "단백질 28g"
        },
        {
            id: 3,
            name: "스파게티 카르보나라",
            price: "15,000원",
            image: "https://images.unsplash.com/photo-1551892374-ecf8754cf8b0?w=400&h=300&fit=crop",
            category: "양식",
            description: "파스타, 베이컨, 달걀, 파마산 치즈",
            calories: "650kcal",
            protein: "단백질 35g"
        },
        {
            id: 4,
            name: "초밥 세트",
            price: "18,000원",
            image: "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=400&h=300&fit=crop",
            category: "일식",
            description: "연어, 참치, 새우, 간장, 와사비",
            calories: "520kcal",
            protein: "단백질 45g"
        },
        {
            id: 5,
            name: "치킨 샐러드",
            price: "11,000원",
            image: "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400&h=300&fit=crop",
            category: "샐러드",
            description: "그릴드 치킨, 퀴노아, 아보카도, 견과류",
            calories: "480kcal",
            protein: "단백질 35g"
        },
        {
            id: 6,
            name: "마르게리타 피자",
            price: "16,000원",
            image: "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400&h=300&fit=crop",
            category: "양식",
            description: "토마토 소스, 모짜렐라, 바질",
            calories: "720kcal",
            protein: "단백질 32g"
        },
        {
            id: 7,
            name: "비빔밥",
            price: "9,000원",
            image: "https://images.unsplash.com/photo-1553978297-833d09932d77?w=400&h=300&fit=crop",
            category: "한식",
            description: "나물, 쌀밥, 고추장, 계란",
            calories: "450kcal",
            protein: "단백질 18g"
        },
        {
            id: 8,
            name: "새우볶음밥",
            price: "10,500원",
            image: "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400&h=300&fit=crop",
            category: "중식",
            description: "새우, 쌀밥, 달걀, 채소",
            calories: "520kcal",
            protein: "단백질 26g"
        },
        {
            id: 9,
            name: "연어 스테이크",
            price: "22,000원",
            image: "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400&h=300&fit=crop",
            category: "양식",
            description: "그릴드 연어, 아스파라거스, 레몬",
            calories: "380kcal",
            protein: "단백질 48g"
        },
        {
            id: 10,
            name: "된장찌개",
            price: "7,500원",
            image: "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400&h=300&fit=crop",
            category: "한식",
            description: "된장, 두부, 파, 쌀밥",
            calories: "380kcal",
            protein: "단백질 22g"
        },
        {
            id: 11,
            name: "팟타이",
            price: "13,000원",
            image: "https://images.unsplash.com/photo-1559847844-d0c8655b8ea6?w=400&h=300&fit=crop",
            category: "아시아",
            description: "쌀국수, 새우, 땅콩, 숙주",
            calories: "540kcal",
            protein: "단백질 30g"
        },
        {
            id: 12,
            name: "치킨 버거",
            price: "8,000원",
            image: "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=300&fit=crop",
            category: "패스트푸드",
            description: "치킨 패티, 양상추, 토마토, 감자튀김",
            calories: "680kcal",
            protein: "단백질 38g"
        }
    ];

    // 슬라이드 기능을 위한 설정
    const itemsPerPage = 10;
    const totalPages = Math.ceil(mealData.length / itemsPerPage);
    const showSlideControls = mealData.length > 10;

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
                <Spin size="large" />
            </div>
        );
    }

    return (
        <>
            <Header />

            <div className="allDietPage_container">
                <div className="allDietPage_list_container">
                    {getCurrentPageItems().map((item, index) => (
                        <Card
                            key={item.id}
                            hoverable
                            className="allDietPage_item_card"
                            onClick={() => handleItemClick(item)}
                            cover={
                                <div className="allDietPage_item_image_container">
                                    <img 
                                        src={item.image} 
                                        alt={item.name} 
                                        className="allDietPage_item_image" 
                                    />
                                    <div className="allDietPage_category_tag">{item.category}</div>
                                </div>
                            }
                        >
                            <div className="allDietPage_item_meta">
                                <div className="allDietPage_item_title">{item.name}</div>
                                <div className="allDietPage_item_description">{item.description}</div>
                                <div className="allDietPage_item_nutrition">
                                    <span className="allDietPage_calories">{item.calories}</span>
                                    <span className="allDietPage_protein">{item.protein}</span>
                                </div>
                            </div>
                        </Card>
                    ))}
                    
                    {/* 빈 공간 채우기 (10개 미만일 때) */}
                    {getCurrentPageItems().length < 10 && 
                        Array.from({ length: 10 - getCurrentPageItems().length }, (_, index) => (
                            <div key={`empty-${index}`} className="allDietPage_empty_card"></div>
                        ))
                    }
                </div>

                {/* 슬라이드 컨트롤 (10개 이상일 때만 표시) */}
                {showSlideControls && (
                    <div className="allDietPage_slide_controls">
                        {/* 왼쪽: 이전 버튼 */}
                        <button 
                            className="allDietPage_nav_button allDietPage_prev_button"
                            onClick={prevPage}
                            disabled={currentPage === 0}
                        >
                            <LeftOutlined />
                        </button>

                        {/* 가운데: 페이지 인디케이터 */}
                        <div className="allDietPage_indicators">
                            {Array.from({ length: totalPages }, (_, index) => (
                                <div
                                    key={index}
                                    className={`allDietPage_indicator ${index === currentPage ? 'active' : ''}`}
                                    onClick={() => goToPage(index)}
                                />
                            ))}
                        </div>

                        {/* 오른쪽: 다음 버튼 */}
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

            <BottomNav />
        </>
    );
}

export default AllDietPage;