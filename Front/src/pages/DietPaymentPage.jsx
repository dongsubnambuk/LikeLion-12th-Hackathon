import React from 'react';
import { useLocation } from 'react-router-dom';
import '../CSS/DietPaymentPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentPage() {
    const location = useLocation();
    const { price } = location.state;

    return (
        <>
            <Header />

            <div>
                결제금액 : {price.toLocaleString()}원
            </div>

            <BottomNav />
        </>
    );
}

export default DietPaymentPage;
