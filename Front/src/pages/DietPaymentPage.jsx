import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../CSS/DietPaymentPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const { price } = location.state;

    const onSuccess = async (response) => {
        console.log('결제 성공', response);

        if (response.error_code != null) {
            return alert(`결제에 실패하였습니다. 에러 내용: ${response.error_msg}`);
            // 실패시 실패 페이지로 이동
        }

        // 검증 완료시 완료 페이지로 이동
        //navigate(`/dietpaymentverification`);

        // 백으로 코드 주소 검증 확인 요청

        // 고객사 서버에서 /payment/complete 엔드포인트를 구현해야 합니다.
        // (다음 목차에서 설명합니다)
        const notified = await fetch(`http://localhost:3000/dietpaymentverification`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            // imp_uid와 merchant_uid, 주문 정보를 서버에 전달합니다
            body: JSON.stringify({
                //imp_uid: response.imp_uid,
                //merchant_uid: response.merchant_uid,
                // 주문 정보...
            }),
        });

    };

    const requestPay = () => {
        const { IMP } = window; // 생략 가능
        IMP.init('imp87540676'); // 아임포트 관리자 콘솔에서 확인한 가맹점 식별코드

        const data = {
            pg: 'html5_inicis', // PG사
            pay_method: 'card', // 결제수단
            merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
            name: '주문명: 결제테스트',
            amount: price, // 금액
            buyer_email: 'example@example.com',
            buyer_name: '홍길동',
            buyer_tel: '010-1234-5678',
            buyer_addr: '서울특별시 강남구 삼성동',
            buyer_postcode: '123-456',
            m_redirect_url: 'http://localhost:3000/dietpaymentverification', // 결제 후 리디렉션될 URL
        };

        IMP.request_pay(data, onSuccess);
    };

    return (
        <>
            <Header />

            <div>
                <button onClick={requestPay}>결제하기</button>
            </div>
            <div>
                결제금액 : {price.toLocaleString()}원
            </div>

            <BottomNav />
        </>
    );
}

export default DietPaymentPage;