import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/Notification.css';

function Notification(){

    return(
        <>
            <Header/>
            <div className="notification-container">
                <div className="notification-link">
                    <span>구독 잔액 부족 안내</span>
                </div>
            </div>
            <BottomNav/>
        </>
    );
}

export default Notification;