import React from 'react';
import { useLocation } from 'react-router-dom';
import '../CSS/DietPaymentSuccessPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function DietPaymentSuccessPage() {

    return (
        <>
            <Header />

            <div>
                결제가 완료되었습니다!
            </div>

            <BottomNav />
        </>
    );
}

export default DietPaymentSuccessPage;