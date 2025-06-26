import React, { useEffect, useState, useCallback } from "react";
import { useNavigate } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import '../CSS/Survey.css'; 
import testfood from '../images/mainCardImg1.jpeg'; // 예시 이미지

function Survey() {
    const navigate = useNavigate();
    const email = localStorage.getItem("email") || "test@example.com";
    const [selectedSurvey, setSelectedSurvey] = useState(null);
    const [ratings, setRatings] = useState({}); // 별점 상태
    const [comments, setComments] = useState({}); // 코멘트 상태
    const [hoveredStars, setHoveredStars] = useState({}); // 호버된 별점 상태
    
    // JSON 응답 양식에 맞는 예시 데이터 - 많은 설문을 포함
    const getSampleSurveys = () => [
        {
            id: 1,
            userEmail: email,
            reviewDate: "2025-06-24",
            notificationContent: "오늘의 식단에 대한 설문조사가 도착했습니다!",
            isCompleted: false,
            reviews: [
                {
                    id: 1,
                    userEmail: email,
                    foodMenuId: 101,
                    foodMenuName: "김치찌개",
                    foodMenuImage: testfood,
                    rating: 0,
                    comment: "",
                    createdAt: "2025-06-24T09:00:00.000Z"
                },
                {
                    id: 2,
                    userEmail: email,
                    foodMenuId: 102,
                    foodMenuName: "불고기",
                    foodMenuImage: testfood,
                    rating: 0,
                    comment: "",
                    createdAt: "2025-06-24T09:15:00.000Z"
                }
            ]
        },
        {
            id: 2,
            userEmail: email,
            reviewDate: "2025-06-23",
            notificationContent: "어제 식단에 대한 추가 설문조사입니다.",
            isCompleted: true,
            reviews: [
                {
                    id: 3,
                    userEmail: email,
                    foodMenuId: 103,
                    foodMenuName: "된장찌개",
                    foodMenuImage: testfood,
                    rating: 0,
                    comment: "",
                    createdAt: "2025-06-23T12:30:00.000Z"
                }
            ]
        },
        {
            id: 3,
            userEmail: email,
            reviewDate: "2025-06-22",
            notificationContent: "주간 식단 만족도 조사",
            isCompleted: false,
            reviews: [
                {
                    id: 4,
                    userEmail: email,
                    foodMenuId: 104,
                    foodMenuName: "비빔밥",
                    foodMenuImage: testfood,
                    rating: 0,
                    comment: "",
                    createdAt: "2025-06-22T18:00:00.000Z"
                },
                {
                    id: 5,
                    userEmail: email,
                    foodMenuId: 105,
                    foodMenuName: "갈비탕",
                    foodMenuImage: testfood,
                    rating: 0,
                    comment: "",
                    createdAt: "2025-06-22T18:15:00.000Z"
                }
            ]
        },
        {
            id: 4,
            userEmail: email,
            reviewDate: "2025-06-21",
            notificationContent: "월요일 점심 메뉴 만족도 조사",
            isCompleted: false,
            reviews: [
                {
                    id: 6,
                    userEmail: email,
                    foodMenuId: 106,
                    foodMenuName: "삼겹살 구이",
                    foodMenuImage: testfood,
                    rating: 0,
                    comment: "",
                    createdAt: "2025-06-21T12:00:00.000Z"
                }
            ]
        }
    ];

    const [surveys, setSurveys] = useState(() => {
        console.log("Survey 초기화 중... email:", email);
        
        const savedSurveys = localStorage.getItem(`surveys_${email}`);
        if (savedSurveys) {
            try {
                const parsed = JSON.parse(savedSurveys);
                console.log("저장된 설문 로드:", parsed);
                return parsed;
            } catch (error) {
                console.error("저장된 설문 파싱 오류:", error);
            }
        }
        
        // 저장된 설문이 없으면 예시 데이터를 사용
        const sampleData = getSampleSurveys();
        console.log("예시 설문 데이터 생성:", sampleData);
        localStorage.setItem(`surveys_${email}`, JSON.stringify(sampleData));
        return sampleData;
    });

    useEffect(() => {
        // 예시 데이터로만 동작
        console.log("Survey 컴포넌트 마운트됨");
        console.log("현재 설문 목록:", surveys);
    }, [surveys]);

    // 모달 열기
    const openSurveyModal = (survey) => {
        setSelectedSurvey(survey);
        // body에 modal-open 클래스 추가 (스크롤 방지)
        document.body.classList.add('modal-open');
        // 별점과 코멘트 상태 초기화
        const initialRatings = {};
        const initialComments = {};
        const initialHoveredStars = {};
        survey.reviews.forEach(review => {
            initialRatings[review.id] = 0;
            initialComments[review.id] = '';
            initialHoveredStars[review.id] = 0;
        });
        setRatings(initialRatings);
        setComments(initialComments);
        setHoveredStars(initialHoveredStars);
    };

    // 모달 닫기
    const closeSurveyModal = () => {
        setSelectedSurvey(null);
        setRatings({});
        setComments({});
        setHoveredStars({});
        // body에서 modal-open 클래스 제거
        document.body.classList.remove('modal-open');
    };

    // 별점 변경
    const handleRatingChange = (reviewId, rating) => {
        setRatings((prevRatings) => ({ ...prevRatings, [reviewId]: rating }));
    };

    // 별점 호버
    const handleStarHover = (reviewId, starIndex) => {
        setHoveredStars((prevHovered) => ({ ...prevHovered, [reviewId]: starIndex }));
    };

    // 별점 호버 종료
    const handleStarLeave = (reviewId) => {
        setHoveredStars((prevHovered) => ({ ...prevHovered, [reviewId]: 0 }));
    };

    // 코멘트 변경
    const handleCommentChange = (reviewId, value) => {
        setComments((prevComments) => ({ ...prevComments, [reviewId]: value }));
    };

    // 별점 렌더링 함수
    const renderStars = (reviewId) => {
        const currentRating = ratings[reviewId] || 0;
        const displayRating =  currentRating;

        return (
            <div className="star_rating_container">
                <div className="star_rating">
                    {[1, 2, 3, 4, 5].map((starIndex) => (
                        <span
                            key={starIndex}
                            className={`star ${starIndex <= displayRating ? 'star_filled' : 'star_empty'}`}
                            onClick={() => handleRatingChange(reviewId, starIndex)}
                            onMouseEnter={() => handleStarHover(reviewId, starIndex)}
                            onMouseLeave={() => handleStarLeave(reviewId)}
                        >
                            ★
                        </span>
                    ))}
                </div>
                <span className="rating_text">
                    {displayRating > 0 ? `${displayRating}.0` : '평가해주세요'}
                </span>
            </div>
        );
    };

    const fetchReviews = async (reviewDate) => {
        // 예시 데이터로만 동작 (실제 API 호출 제거)
        console.log("fetchReviews 시뮬레이션:", reviewDate);
        return null;
    };

    const handleNotification = useCallback(async (message) => {
        // WebSocket 알림 처리 (예시 데이터로만 동작)
        const notification = JSON.parse(message.body);
        console.log("수신한 설문 DTO:", notification);

        if (!notification.reviews) {
            console.log("reviews 필드가 null입니다. 예시 데이터 사용");
            const reviews = await fetchReviews(notification.reviewDate);
            if (reviews) {
                notification.reviews = reviews;
            }
        }

        if (!notification.id) {
            notification.id = uuidv4();
        }

        setSurveys(prevSurveys => {
            const isDuplicate = prevSurveys.some(survey => 
                (survey.notificationContent || survey.content) === (notification.notificationContent || notification.content)
            );

            if (!isDuplicate) {
                const newSurveys = [notification, ...prevSurveys];
                localStorage.setItem(`surveys_${email}`, JSON.stringify(newSurveys));
                return newSurveys;
            }
            return prevSurveys;
        });
    }, [email]);

    useEffect(() => {
        if (!email) return;

        // WebSocket 연결도 예시 데이터로만 동작하도록 주석 처리
        console.log("WebSocket 연결 시뮬레이션 (설문 - 예시 데이터로만 동작)");
        
        /*
        const socket = new SockJS('http://nutrihub.kro.kr:14000/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log("WebSocket에 연결되었습니다.");

            const surveySubscription = client.subscribe(`/topic/notification/survey/${email}`, message => {
                handleNotification(message);
            });

            return () => {
                surveySubscription.unsubscribe();
                client.disconnect(() => {
                    console.log('연결이 끊어졌습니다.');
                });
            };
        }, error => {
            console.error('연결 오류: ', error);
        });

        return () => {
            if (client && client.connected) {
                client.disconnect(() => {
                    console.log('연결이 끊어졌습니다.');
                });
            }
        };
        */
    }, [email, handleNotification]);

    // 설문 제출
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        // 모든 항목에 별점이 있는지 확인
        const incompleteItems = selectedSurvey.reviews.filter(review => !ratings[review.id] || ratings[review.id] === 0);
        
        if (incompleteItems.length > 0) {
            alert('모든 메뉴에 대해 별점 평가를 해주세요.');
            return;
        }
        
        console.log("설문 제출 시뮬레이션");
        console.log("별점:", ratings);
        console.log("코멘트:", comments);
        
        // 설문을 완료 상태로 업데이트
        setSurveys(prevSurveys => {
            const updatedSurveys = prevSurveys.map(survey => 
                survey.id === selectedSurvey.id ? { ...survey, isCompleted: true } : survey
            );
            localStorage.setItem(`surveys_${email}`, JSON.stringify(updatedSurveys));
            return updatedSurveys;
        });
        
        alert('설문이 제출되었습니다!');
        closeSurveyModal();
    };

    // ESC 키로 모달 닫기
    useEffect(() => {
        const handleEscKey = (event) => {
            if (event.key === 'Escape' && selectedSurvey) {
                closeSurveyModal();
            }
        };

        if (selectedSurvey) {
            document.addEventListener('keydown', handleEscKey);
        }

        return () => {
            document.removeEventListener('keydown', handleEscKey);
        };
    }, [selectedSurvey]);

    return (
        <>
            <div className="survey_link_container">
                {surveys.length > 0 ? (
                    surveys.map((survey) => (
                        <div 
                            className={`survey_link ${survey.isCompleted ? 'survey_completed' : ''}`}
                            key={survey.id} 
                            onClick={() => survey.isCompleted ? null : openSurveyModal(survey)}
                        >
                            <div className="survey_content">
                                <div className="survey_title">
                                    {survey.notificationContent || survey.content}
                                </div>
                                <div className="survey_meta">
                                    <span className="survey_meta_icon">🍎</span>
                                    <span className="survey_meta_text">식단</span>
                                    <span className="survey_meta_text">{survey.reviewDate} 오후 11:58</span>
                                </div>
                                {!survey.isCompleted && <div className="survey_notification_dot"></div>}
                                
                            </div>
                        </div>
                    ))
                ) : (
                    <div className="survey_empty">
                        설문조사가 없습니다.
                    </div>
                )}
            </div>

            {/* 설문 모달 */}
            {selectedSurvey && (
                <div className="survey_modal_overlay" onClick={closeSurveyModal}>
                    <div className="survey_modal_container" onClick={(e) => e.stopPropagation()}>
                        <div className="survey_modal_header">
                            <div className="survey_modal_header_content">
                                <h2 className="survey_modal_title">식단 만족도 조사</h2>
                                <p className="survey_modal_date">{selectedSurvey.reviewDate}</p>
                            </div>
                            <button className="survey_modal_close" onClick={closeSurveyModal}>✕</button>
                        </div>
                        
                        <form onSubmit={handleSubmit} className="survey_modal_form">
                            <div className="survey_modal_scroll_card">
                                <div className="survey_modal_scroll_container">
                                    {selectedSurvey.reviews.map((review) => (
                                        <div key={review.id} className="survey_modal_section">
                                            <div className="survey_modal_left">
                                                <h3 className="survey_modal_item_title">{`${selectedSurvey.reviewDate} - ${review.id}`}</h3>
                                                <div className="survey_modal_food_image">
                                                    <img 
                                                        src={review.foodMenuImage || testfood} 
                                                        className="survey_modal_logo_image" 
                                                        alt="food" 
                                                    />
                                                </div>
                                                <p className="survey_modal_food_name">[{review.foodMenuName}]</p>
                                            </div>
                                            <div className="survey_modal_right">
                                                <div className="rating_section">
                                                    <label className="rating_label">만족도 평가 *</label>
                                                    {renderStars(review.id)}
                                                </div>
                                                
                                                <div className="comment_section">
                                                    <label className="comment_label">리뷰내용 (선택사항)</label>
                                                    <textarea
                                                        value={comments[review.id] || ''}
                                                        onChange={(e) => handleCommentChange(review.id, e.target.value)}
                                                        placeholder="이 음식에 대한 의견을 자유롭게 남겨주세요."
                                                        className="comment_textarea"
                                                        rows="4"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                            
                            <div className="survey_modal_submit">
                                <button type="submit" className="survey_modal_submit_button">
                                    📝 설문 제출하기
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </>
    );
}

export default Survey;