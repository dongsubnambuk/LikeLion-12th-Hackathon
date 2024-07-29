import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/SurveyDetail.css';
import logo from '../images/logo.png';

function SurveyDetail(){
    const [meals, setMeals] = useState({ breakfast: '', lunch: '', dinner: '' });
    const [responses, setResponses] = useState({ breakfast: '', lunch: '', dinner: '' });
    const [feedback, setFeedback] = useState({ breakfast: '', lunch: '', dinner: '' });
  
    useEffect(() => {
      // 임시 데이터 설정
      const fetchMeals = async () => {
        const data = {
          breakfast: '김치찌개 정식',
          lunch: '비빔밥',
          dinner: '된장찌개 정식',
        };
        setMeals(data);
      };
  
      fetchMeals();
    }, []);
  
    const handleResponseChange = (mealType, value) => {
      setResponses((prevResponses) => ({ ...prevResponses, [mealType]: value }));
    };
  
    const handleFeedbackChange = (mealType, value) => {
      setFeedback((prevFeedback) => ({ ...prevFeedback, [mealType]: value }));
    };
  
    const handleSubmit = (e) => {
      e.preventDefault();
      console.log({ responses, feedback });
      alert('설문이 제출되었습니다!');
    };

    return(
        <>
        <Header/>
        <div className="survey-container">
      <form onSubmit={handleSubmit}>
        {['breakfast', 'lunch', 'dinner'].map((mealType) => (
          <div key={mealType} className="survey-section">
            <div className="survey-left">
              <h3>{`2024년 8월 1일 - ${mealType === 'breakfast' ? '아침' : mealType === 'lunch' ? '점심' : '저녁'}`}</h3>
              
              <div className="food-image">
                <img src={logo} className="logoImage-survey" alt="logo" />
                </div>

              <p>[{meals[mealType]}]</p>
            </div>
            <div className="survey-right">
              <label className="survey-label">
                <input
                  type="radio"
                  value="좋아요"
                  checked={responses[mealType] === '좋아요'}
                  onChange={(e) => handleResponseChange(mealType, e.target.value)}
                />
                좋아요
              </label>
              <label className="survey-label">
                <input
                  type="radio"
                  value="별로예요"
                  checked={responses[mealType] === '별로예요'}
                  onChange={(e) => handleResponseChange(mealType, e.target.value)}
                />
                별로예요
              </label>
              <label className="survey-label">
                <input
                  type="radio"
                  value="기타"
                  checked={responses[mealType] === '기타'}
                  onChange={(e) => handleResponseChange(mealType, e.target.value)}
                />
                기타 (의견을 입력해주세요.)
              </label>
              {responses[mealType] === '기타' && (
                <input
                  type="text"
                  value={feedback[mealType]}
                  onChange={(e) => handleFeedbackChange(mealType, e.target.value)}
                  placeholder="텍스트 박스"
                />
              )}
            </div>
          </div>
        ))}
        <div className="survey-submit">
            <button type="submit">설문 제출하기</button>
        </div>

      </form>
    </div>
        <BottomNav/>
        </>
    );
}

export default SurveyDetail;