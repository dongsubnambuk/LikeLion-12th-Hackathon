import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/SurveyDetail.css';

function SurveyDetail(){
    return(
        <>
        <Header/>

        <BottomNav/>
        </>
    );
}

export default SurveyDetail;