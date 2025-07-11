import React from 'react';
import { useLocation } from 'react-router-dom';
import { Card, Typography, Divider } from 'antd';
import '../CSS/DietInfoPage.css';

const { Title, Text } = Typography;

function DietInfoPage({ style }) {
    const location = useLocation();
    const { item } = location.state;

    return (
        <div className="dietInfoPage_container" style={style}>
            <div className="dietInfoPage_card">
                {/* 이미지 */}
                <div className="dietInfoPage_imageSection">
                    <img src={item.image} className="dietInfoPage_image" alt={item.name} />
                </div>

                {/* 메뉴명 */}
                <div className="dietInfoPage_header">
                    <h1 className="dietInfoPage_title">{item.name}</h1>
                </div>

                {/* 메뉴 구성 */}
                <div className="dietInfoPage_section">
                    <h3 className="dietInfoPage_sectionTitle">메뉴 구성</h3>
                    <div className="dietInfoPage_menuList">
                        <div className="dietInfoPage_menuGroup">
                            <span className="dietInfoPage_menuType">메인</span>
                            <div className="dietInfoPage_items">
                                <span>{item.main1}</span>
                                <span>{item.main2}</span>
                            </div>
                        </div>
                        <div className="dietInfoPage_menuGroup">
                            <span className="dietInfoPage_menuType">사이드</span>
                            <div className="dietInfoPage_items">
                                <span>{item.side1}</span>
                                <span>{item.side2}</span>
                                <span>{item.side3}</span>
                            </div>
                        </div>
                    </div>
                </div>

                {/* 영양성분 */}
                <div className="dietInfoPage_section">
                    <h3 className="dietInfoPage_sectionTitle">영양성분</h3>
                    <div className="dietInfoPage_nutritionGrid">
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">탄수화물</span>
                            <span className="dietInfoPage_nutritionValue">{item.carbohydrate}</span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">단백질</span>
                            <span className="dietInfoPage_nutritionValue">{item.protein}</span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">지방</span>
                            <span className="dietInfoPage_nutritionValue">{item.fat}</span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">당류</span>
                            <span className="dietInfoPage_nutritionValue">{item.sugar}</span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">나트륨</span>
                            <span className="dietInfoPage_nutritionValue">{item.sodium}</span>
                        </div>
                    </div>
                </div>

                {/* 칼로리 & 가격 */}
                <div className="dietInfoPage_footer">
                    <div className="dietInfoPage_info">
                        <span className="dietInfoPage_label">칼로리</span>
                        <span className="dietInfoPage_value">{item.calories}</span>
                    </div>
                    <div className="dietInfoPage_info">
                        <span className="dietInfoPage_label">가격</span>
                        <span className="dietInfoPage_value">{item.price}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DietInfoPage;