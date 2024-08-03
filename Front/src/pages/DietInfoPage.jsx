import React from 'react';
import { useLocation } from 'react-router-dom';
import '../CSS/DietInfoPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';

function DietInfoPage({style}) {
    const location = useLocation();
    const { item } = location.state;
    //item.menu = "메인 메뉴 : 김치찌개\n\n반찬 1 : 시금치 나물\n\n반찬 2 : 가지볶음\n\n반찬 3 : 치킨너겟\n\n국: 미역국\n\n밥: 잡곡밥\n\n사이드: 배추김치, 김";
    //item.nutrient = "탄수화물 10g\n\n단백질 10g\n\n지방 8g";
    //item.kcal = "약 254Kcal";
    //item.price = "3,450원";

    return (
        <>
            <Header />

            <div className="DIPcontainer" style={style}>
                <div className="DIPlistContainer">
                    <div className="DIPheader">
                        <img src={logo} className="DIPtitleImage" alt="logo" />
                        <div className="DIPtitle">{item.name}</div>
                    </div>
                    <div className="DIPmenu">
                        <span style={{ fontWeight: 'bold', fontSize: 17 }}>&lt; 메뉴 소개 &gt;</span>
                        <br /><br />
                        메인 메뉴1 : {item.main1}
                        <br /><br />
                        메인 메뉴2 : {item.main2}
                        <br /><br />
                        사이드 메뉴1 : {item.side1}
                        <br /><br />
                        사이드 메뉴2 : {item.side2}
                        <br /><br />
                        사이드 메뉴3 : {item.side3}
                        <br /><br />
                    </div>
                    <div className="DIPnutrient">
                        <span style={{ fontWeight: 'bold', fontSize: 17 }}>&lt; 영양성분 &gt;</span>
                        <br /><br />
                        탄수화물 : {item.carbohydrate}
                        <br /><br />
                        단백질 : {item.protein}
                        <br /><br />
                        지방 : {item.fat}
                        <br /><br />
                        당류 : {item.sugar}
                        <br /><br />
                        나트륨 : {item.sodium}
                    </div>
                    <br /><br />
                    <span style={{ fontWeight: 600 }}>{item.calories}</span>
                    <div className="DIPprice">
                        <span style={{ fontSize: 18, fontWeight: 600 }}>가격 : {item.price}</span>
                    </div>
                </div>
            </div>

            <BottomNav />
        </>
    );
}

export default DietInfoPage;
