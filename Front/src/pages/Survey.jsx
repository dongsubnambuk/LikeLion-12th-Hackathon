import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/Survey.css';

function Survey(){
    const navigate = useNavigate();
    return(
        <>
        <Header/>
        <div className="survey-link-container">
        <div className="survey-link" onClick={() => navigate('/survey-detail')}>
                <span>2024년 8월 1일 설문</span>
        
            </div>


        </div>
        
        <BottomNav/>
        </>
    );
}

export default Survey;