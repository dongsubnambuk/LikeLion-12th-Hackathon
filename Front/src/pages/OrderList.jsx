import React, { useState,useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav"
import'../CSS/OrderList.css';

function OrderList(){
    const navigate = useNavigate();

    
    return(
        <> 
            <Header/>
            <div className="order-date">
                <p>2024년 7월 29일 ~ 2024년 8월 4일</p>
            </div>
            <div className="order-detail-container">
                <div className="order-detail-inner" onClick={() => navigate('/weeklyfoodmenu')}>
                    <p>결제 승인 번호: 123456</p>
                    <p>결제 일시: 2024년 7월 26일 오후 3시 3분</p>
                    <p>결제 방식: 카카오 페이</p>
                    <p>금액: 84,700원</p>
                    <p>배송지: 대구 달서구 계대동문로 3안길 13-5</p>
                    <p>메뉴: 김치찌개 정식 , 고등어 구이 정식, 불고기 정식</p>

                    <div className="order-list-detail">
                    <span>자세히 보기</span>
                    <span className="arrow">&gt;</span>
                </div>
                </div>
               
            </div>


            <BottomNav/>
        </>
    );
}

export default OrderList;