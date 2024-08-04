import React from 'react';
import { useLocation } from 'react-router-dom';
import { Card, Typography, Divider } from 'antd';
import '../CSS/DietInfoPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';

const { Title, Paragraph } = Typography;

function DietInfoPage({ style }) {
    const location = useLocation();
    const { item } = location.state;

    return (
        <>
            <Header />
            <div className="DIPcontainer" style={style}>
                <Card className="DIPlistContainer">
                    <div className="DIPimageContainer">
                        <img alt="example" src={logo} className="DIPimage" />
                    </div>
                    <Title level={4} className="DIPtitle">{item.name}</Title>
                   
                    <Divider />
                    <Title level={4} className="DIPsectionTitle">메뉴 소개</Title>
                    <Paragraph>메인 메뉴1: {item.main1}</Paragraph>
                    <Paragraph>메인 메뉴2: {item.main2}</Paragraph>
                    <Paragraph>사이드 메뉴1: {item.side1}</Paragraph>
                    <Paragraph>사이드 메뉴2: {item.side2}</Paragraph>
                    <Paragraph>사이드 메뉴3: {item.side3}</Paragraph>
                    <Divider />
                    <Title level={4} className="DIPsectionTitle">영양성분</Title>
                    <div class="nutrientContainer">
                        <div class="nutrientListContainer">
                            <div class="nutrientList">
                                <div class="nutrientItem carbohydrates">탄수화물 {item.carbohydrate}</div>
                                <div class="nutrientItem protein">단백질 {item.protein}</div>
                                <div class="nutrientItem fat">지방 {item.fat}</div>
                                <div class="nutrientItem sugar">당류 {item.sugar}</div>
                                <div class="nutrientItem sodium">나트륨 {item.sodium}</div>
                            </div>
                        </div>
                    </div>
                    <Divider />
                    <div className="DIPfooter">
                    <span className="calories-icon-left">🔥</span>
                        <div className="DIPcalories">칼로리: {item.calories}</div>
                        <span className="calories-icon-right">🔥</span>
                        <div className="DIPprice">가격: {item.price}</div>
                    </div>
                </Card>
            </div>
            <BottomNav />
        </>
    );
}

export default DietInfoPage;
