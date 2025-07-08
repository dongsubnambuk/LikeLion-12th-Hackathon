import React from 'react';
import { useLocation } from 'react-router-dom';
import '../CSS/DietInfoPage.css';
import logo from '../images/logo.png';

function DietInfoPage({ style }) {
    const location = useLocation();
    const { item } = location.state;

    return (
        <div className="dietInfoPage_container" style={style}>
            <div className="dietInfoPage_card">
                {/* 이미지 */}
                <div className="dietInfoPage_imageSection">
                    <img 
                        src={item.image} 
                        className="dietInfoPage_image" 
                        alt={item.name}
                        onError={(e) => {
                            e.target.src = logo;
                        }}
                    />
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
                                {item.menuComponents?.main1 && <span>{item.menuComponents.main1}</span>}
                                {item.menuComponents?.main2 && <span>{item.menuComponents.main2}</span>}
                            </div>
                        </div>
                        <div className="dietInfoPage_menuGroup">
                            <span className="dietInfoPage_menuType">사이드</span>
                            <div className="dietInfoPage_items">
                                {item.menuComponents?.side1 && <span>{item.menuComponents.side1}</span>}
                                {item.menuComponents?.side2 && <span>{item.menuComponents.side2}</span>}
                                {item.menuComponents?.side3 && <span>{item.menuComponents.side3}</span>}
                            </div>
                        </div>
                    </div>
                </div>

                {/* 영양성분 */}
                <div className="dietInfoPage_section">
                    <h3 className="dietInfoPage_sectionTitle">영양성분</h3>
                    <div className="dietInfoPage_nutritionGrid">
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">칼로리</span>
                            <span className="dietInfoPage_nutritionValue">
                                {item.nutritionInfo?.formattedCalories || `${item.nutritionInfo?.caloriesString || '0'}kcal`}
                            </span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">탄수화물</span>
                            <span className="dietInfoPage_nutritionValue">
                                {item.nutritionInfo?.formattedCarbs || `${item.nutritionInfo?.carbohydrateString || '0'}g`}
                            </span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">단백질</span>
                            <span className="dietInfoPage_nutritionValue">
                                {item.nutritionInfo?.formattedProtein || `${item.nutritionInfo?.proteinString || '0'}g`}
                            </span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">지방</span>
                            <span className="dietInfoPage_nutritionValue">
                                {item.nutritionInfo?.formattedFat || `${item.nutritionInfo?.fatString || '0'}g`}
                            </span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">당류</span>
                            <span className="dietInfoPage_nutritionValue">
                                {item.nutritionInfo?.formattedSugar || `${item.nutritionInfo?.sugarString || '0'}g`}
                            </span>
                        </div>
                        <div className="dietInfoPage_nutritionItem">
                            <span className="dietInfoPage_nutritionLabel">나트륨</span>
                            <span className="dietInfoPage_nutritionValue">
                                {item.nutritionInfo?.formattedSodium || `${item.nutritionInfo?.sodiumString || '0'}mg`}
                            </span>
                        </div>
                    </div>
                </div>

                {/* 가격 */}
                <div className="dietInfoPage_footer">
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
