import React, { useState, useEffect } from "react";
import { useLocation } from 'react-router-dom';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/SurveyDetail.css';

function SurveyDetail() {
    const location = useLocation();
    const { survey } = location.state || {}; // state가 없을 경우를 대비한 기본값 설정
    const reviews = survey.reviews || []; // reviews 배열 사용
    const [responses, setResponses] = useState({});
    const [feedback, setFeedback] = useState({});

    useEffect(() => {
        if (reviews.length > 0) {
            const initialResponses = {};
            const initialFeedback = {};
            reviews.forEach(review => {
                initialResponses[review.reviewId] = '';
                initialFeedback[review.reviewId] = '';
            });
            setResponses(initialResponses);
            setFeedback(initialFeedback);
        }
    }, [reviews]);

    const handleResponseChange = (reviewId, value) => {
        setResponses((prevResponses) => ({ ...prevResponses, [reviewId]: value }));
    };

    const handleFeedbackChange = (reviewId, value) => {
        setFeedback((prevFeedback) => ({ ...prevFeedback, [reviewId]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const requests = reviews.map(async review => {
            const payload = {
                reviewId: review.reviewId,
                foodImage: review.foodImage,
                foodName: review.foodName,
                likes: responses[review.reviewId] === '좋아요' ? review.likes + 1 : review.likes,
                disLikes: responses[review.reviewId] === '별로예요' ? review.disLikes + 1 : review.disLikes,
                comment: feedback[review.reviewId] ? [feedback[review.reviewId]] : review.comment
            };

            const url = responses[review.reviewId] === '좋아요'
                ? `http://3.37.64.39:8000/api/meal/review/likes/${review.reviewId}`
                : `http://3.37.64.39:8000/api/meal/review/disLikes/${review.reviewId}`;

            const token = localStorage.getItem("token");
            try {
                const response = await fetch(url, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        "Authorization": token
                    },
                    body: JSON.stringify(payload)
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const result = await response.json();
                console.log(result);
            } catch (error) {
                console.error('Error:', error);
                alert('설문 제출에 실패했습니다.');
            }
        });

        await Promise.all(requests);
        alert('설문이 제출되었습니다!');
    };

    if (!survey || reviews.length === 0) {
        return <p>설문 데이터를 로드하는 중입니다...</p>;
    }

    return (
        <>
            <Header />
            <div className="survey-container">
                <form onSubmit={handleSubmit}>
                    {reviews.map((review) => (
                        <div key={review.reviewId} className="survey-section">
                            <div className="survey-left">
                                <h3>{`${survey.reviewDate} - ${review.reviewId}`}</h3>
                                <div className="food-image">
                                    <img src={review.foodImage} className="logoImage-survey" alt="food" />
                                </div>
                                <p>[{review.foodName}]</p>
                            </div>
                            <div className="survey-right">
                                <label className="survey-label">
                                    <input
                                        type="radio"
                                        value="좋아요"
                                        checked={responses[review.reviewId] === '좋아요'}
                                        onChange={(e) => handleResponseChange(review.reviewId, e.target.value)}
                                    />
                                    좋아요
                                </label>
                                <label className="survey-label">
                                    <input
                                        type="radio"
                                        value="별로예요"
                                        checked={responses[review.reviewId] === '별로예요'}
                                        onChange={(e) => handleResponseChange(review.reviewId, e.target.value)}
                                    />
                                    별로예요
                                </label>
                                <label className="survey-label">
                                    <input
                                        type="radio"
                                        value="기타"
                                        checked={responses[review.reviewId] === '기타'}
                                        onChange={(e) => handleResponseChange(review.reviewId, e.target.value)}
                                    />
                                    기타 (의견을 입력해주세요.)
                                </label>
                                {responses[review.reviewId] === '기타' && (
                                    <input
                                        type="text"
                                        value={feedback[review.reviewId]}
                                        onChange={(e) => handleFeedbackChange(review.reviewId, e.target.value)}
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
