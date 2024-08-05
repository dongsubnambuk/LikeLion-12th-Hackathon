import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/OrderList.css';

function OrderList() {
    const [address, setAddress] = useState({ roadAddress: '', detailAddress: '' });
    const [payments, setPayments] = useState([]);
    const navigate = useNavigate();

    // 유저 정보 fetch
    useEffect(() => {
        const handleGetUser = async () => {
            const token = localStorage.getItem("token");
            const email = localStorage.getItem("email");

            const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환

            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                setAddress({
                    roadAddress: result.roadAddress,
                    detailAddress: result.detailAddress,
                });
            } else {
                console.log("사용자 정보 불러오기 실패");
                alert("사용자 정보 불러오기 실패: " + result.message);
            }
        };

        handleGetUser();
    }, []); // 빈 배열을 두 번째 인자로 전달하여 컴포넌트가 마운트될 때 한 번만 실행

    // 결제내역 fetch
    useEffect(() => {
        const handlePaymentGet = async () => {
            const token = localStorage.getItem("token");
            const purchaser = localStorage.getItem("email");

            const response = await fetch(`http://3.37.64.39:8000/api/payment/purchaser/${purchaser}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json(); // 응답이 JSON 형식일 경우 이를 JavaScript 객체로 변환
            console.log(result);

            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                setPayments(result.data);
            } else {
                console.log("결제내역 불러오기 실패");
                alert("결제내역 불러오기 실패: " + result.message);
            }
        };

        handlePaymentGet();
    }, []); // 빈 배열을 두 번째 인자로 전달하여 컴포넌트가 마운트될 때 한 번만 실행

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}년 ${month}월 ${day}일`;
    };

    return (
        <>
            <Header />
            <div className="order-detail-container">
                {payments.map((payment, index) => (
                    <div key={index} className="order-wrapper">
                        <div className="order-date">
                        <p>식단 주차: {formatDate(payment.dateTime)}</p>
                        </div>
                        <div className="order-detail-inner" onClick={() => navigate('/weeklyfoodmenu')}>
                            <p>결제 승인 번호: {payment.paymentId}</p>
                            <p>결제 일시: {formatDate(payment.dateTime)}</p>
                            <p>금액: {payment.totalPrice}</p>
                            <p>배송지: {address.roadAddress} {address.detailAddress}</p>
                            <p>메뉴: {payment.weeklyId}</p>
                            <div className="order-list-detail">
                                <span>자세히 보기</span>
                                <span className="arrow">&gt;</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <BottomNav />
        </>
    );
}

export default OrderList;
