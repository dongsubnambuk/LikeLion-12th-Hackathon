import React, { useState, useEffect } from "react";
import { useLocation } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/SurveyDetail.css';
import logo from '../images/logo.png';

function SurveyDetail() {
    const location = useLocation();
    const { survey } = location.state;
    const [meals, setMeals] = useState([]);
    const [responses, setResponses] = useState({});
    const [feedback, setFeedback] = useState({});

    useEffect(() => {
        if (survey) {
            setMeals(survey.reviews);
            const initialResponses = {};
            const initialFeedback = {};
            survey.reviews.forEach(review => {
                initialResponses[review.reviewId] = '';
                initialFeedback[review.reviewId] = '';
            });
            setResponses(initialResponses);
            setFeedback(initialFeedback);
        }
    }, [survey]);

    const handleResponseChange = (reviewId, value) => {
        setResponses((prevResponses) => ({ ...prevResponses, [reviewId]: value }));
    };

    const handleFeedbackChange = (reviewId, value) => {
        setFeedback((prevFeedback) => ({ ...prevFeedback, [reviewId]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log({ responses, feedback });
        alert('설문이 제출되었습니다!');
    };

    return (
        <>
            <Header />
            <div className="survey-container">
                <form onSubmit={handleSubmit}>
                    {meals.map((meal) => (
                        <div key={meal.reviewId} className="survey-section">
                            <div className="survey-left">
                                <h3>{`2024년 8월 4일 - ${meal.foodName}`}</h3>
                                <div className="food-image">
                                    <img src={meal.foodImage} className="logoImage-survey" alt="food" />
                                </div>
                                <p>[{meal.foodName}]</p>
                            </div>
                            <div className="survey-right">
                                <label className="survey-label">
                                    <input
                                        type="radio"
                                        value="좋아요"
                                        checked={responses[meal.reviewId] === '좋아요'}
                                        onChange={(e) => handleResponseChange(meal.reviewId, e.target.value)}
                                    />
                                    좋아요
                                </label>
                                <label className="survey-label">
                                    <input
                                        type="radio"
                                        value="별로예요"
                                        checked={responses[meal.reviewId] === '별로예요'}
                                        onChange={(e) => handleResponseChange(meal.reviewId, e.target.value)}
                                    />
                                    별로예요
                                </label>
                                <label className="survey-label">
                                    <input
                                        type="radio"
                                        value="기타"
                                        checked={responses[meal.reviewId] === '기타'}
                                        onChange={(e) => handleResponseChange(meal.reviewId, e.target.value)}
                                    />
                                    기타 (의견을 입력해주세요.)
                                </label>
                                {responses[meal.reviewId] === '기타' && (
                                    <input
                                        type="text"
                                        value={feedback[meal.reviewId]}
                                        onChange={(e) => handleFeedbackChange(meal.reviewId, e.target.value)}
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
            <BottomNav />
        </>
    );
}

export default SurveyDetail;
