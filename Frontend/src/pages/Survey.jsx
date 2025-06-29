import React, { useEffect, useState, useCallback, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import '../CSS/Survey.css'; 
import testfood from '../images/mainCardImg1.jpeg';

function Survey() {
    const navigate = useNavigate();
    const [userEmail, setUserEmail] = useState('user5@example.com');
    const [selectedSurvey, setSelectedSurvey] = useState(null);
    const [ratings, setRatings] = useState({});
    const [comments, setComments] = useState({});
    const [hoveredStars, setHoveredStars] = useState({});
    const [surveys, setSurveys] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const stompClientRef = useRef(null);

    // 사용자 정보 조회 API
    const getUserInfo = useCallback(async () => {
        try {
            const response = await fetch('http://nimn.store/api/users', {
                method: "GET",
                credentials: 'include',
            });
      
            if (response.status === 200) {
                const result = await response.json();
                
                if (result.message === "토큰소멸") {
                    alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                    navigate('/login');
                    return;
                }
                
                const email = result.email || 'user5@example.com';
                setUserEmail(email);
                console.log('Survey - 사용자 이메일 설정:', email);
            } else {
                console.log("Survey - 사용자 정보 조회 실패", response.status);
                const result = await response.json().catch(() => ({}));
                
                if (result.message === "토큰소멸") {
                    alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                    navigate('/login');
                    return;
                }
                
                console.log('Survey - 기본 이메일 사용:', userEmail);
            }
        } catch (error) {
            console.error("Survey - 사용자 정보 조회 오류:", error);
            console.log('Survey - 기본 이메일 사용:', userEmail);
        }
    }, [navigate, userEmail]);

    // 모든 알림 조회 API (REVIEW 타입만)
    const getAllNotificationsAPI = useCallback(async () => {
        try {
            setIsLoading(true);
            const response = await fetch(`http://nimn.store/api/notification/all?userEmail=${userEmail}`);
            
            if (response.ok) {
                const data = await response.json();
                const reviewNotifications = data.filter(notification => notification.type === 'REVIEW');
                console.log('기존 REVIEW 알림 조회:', reviewNotifications.length, '건');
                return reviewNotifications;
            } else {
                console.error('알림 조회 실패:', response.status);
                return [];
            }
        } catch (error) {
            console.error('알림 조회 실패:', error);
            return [];
        } finally {
            setIsLoading(false);
        }
    }, [userEmail]);

    // 일일 리뷰 조회 API
    const getDailyReviewAPI = useCallback(async (userEmail, date) => {
        try {
            console.log(`일일 리뷰 조회 시작 - userEmail: ${userEmail}, date: ${date}`);
            
            const response = await fetch(`http://nimn.store/api/review/daily?userEmail=${userEmail}&date=2025-07-07`, {
                method: "GET",
                credentials: 'include',
            });
            
            if (response.ok) {
                const data = await response.json();
                console.log('일일 리뷰 조회 성공:', data);
                return data;
            } else {
                console.error('일일 리뷰 조회 실패:', response.status);
                return null;
            }
        } catch (error) {
            console.error('일일 리뷰 조회 에러:', error);
            return null;
        }
    }, []);

    // 하루 식단 리뷰 수정 API
    const updateDailyReviewAPI = useCallback(async (dailyReviewId, requestBody) => {
        try {
            console.log(`하루 식단 리뷰 수정 시작 - dailyReviewId: ${dailyReviewId}`);
            console.log('요청 데이터:', JSON.stringify(requestBody, null, 2));
            
            const response = await fetch(`http://nimn.store/api/review/daily/${dailyReviewId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify(requestBody)
            });
            
            console.log(`dailyReviewId ${dailyReviewId} 응답 상태:`, response.status);
            
            if (response.ok) {
                const result = await response.json();
                console.log(`하루 식단 리뷰 수정 성공:`, result);
                return true;
            } else {
                try {
                    const errorText = await response.text();
                    console.error(`서버 에러 응답:`, errorText);
                } catch (e) {
                    console.error(`에러 응답 파싱 실패`);
                }
                console.error(`하루 식단 리뷰 수정 실패:`, response.status);
                return false;
            }
        } catch (error) {
            console.error(`하루 식단 리뷰 수정 에러:`, error);
            return false;
        }
    }, []);

    // WebSocket 연결
    const connectWebSocket = useCallback(() => {
        console.log(`🔄 Survey WebSocket 연결 시도: ${userEmail}`);
        
        if (stompClientRef.current) {
            console.log('🔌 Survey 기존 연결 해제');
            try {
                stompClientRef.current.deactivate();
            } catch (error) {
                console.log('기존 연결 해제 중 오류:', error);
            }
        }

        const socket = new SockJS(`http://nimn.store/ws/notification?userEmail=${userEmail}`);

        const client = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                userEmail: userEmail     
            },
            debug: (str) => console.log("🔍 Survey STOMP Debug:", str),
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            reconnectDelay: 3000,
            onConnect: (frame) => {
                console.log("✅ Survey STOMP CONNECTED - userEmail:", userEmail);
                console.log("Connection frame:", frame);

                const handleMessage = async (message) => {
                    console.log("📨 Survey 원본 메시지 수신:", message.body);
                    
                    let parsed;
                    try {
                        parsed = JSON.parse(message.body);
                        console.log("📝 Survey 파싱된 메시지:", parsed);
                    } catch (error) {
                        console.log("파싱 오류:", error);
                        parsed = { content: message.body, type: "TEXT" };
                        console.log("📝 Survey 파싱 실패, 기본값 사용:", parsed);
                    }
                    
                    if (parsed.type === 'REVIEW' && parsed.dailyReviewId) {
                        console.log(`✅ Survey REVIEW 타입 알림 처리 - dailyReviewId: ${parsed.dailyReviewId}`);
                        
                        const today = new Date().toISOString().split('T')[0];
                        const reviewData = await getDailyReviewAPI(userEmail, today);
                        
                        if (reviewData && reviewData.reviews) {
                            const surveyData = {
                                id: `${reviewData.id}_${Date.now()}`,
                                userEmail: userEmail,
                                reviewDate: reviewData.reviewDate || today,
                                notificationContent: parsed.content,
                                isCompleted: false,
                                reviews: reviewData.reviews,
                                dailyReviewId: reviewData.id
                            };
                            
                            console.log("📝 Survey 데이터 생성:", surveyData);
                            
                            setSurveys(prev => {
                                const isDuplicate = prev.some(survey => 
                                    survey.dailyReviewId === reviewData.id && 
                                    survey.reviewDate === surveyData.reviewDate
                                );
                                if (!isDuplicate) {
                                    console.log("📌 새 설문 추가:", surveyData);
                                    return [surveyData, ...prev];
                                }
                                console.log("⚠️ 중복 설문 제외");
                                return prev;
                            });
                        } else {
                            console.error('리뷰 데이터 조회 실패 또는 빈 데이터');
                        }
                    } else {
                        console.log(`⚠️ Survey에서 제외된 타입 또는 dailyReviewId 없음: ${parsed.type}, dailyReviewId: ${parsed.dailyReviewId}`);
                    }
                };

                const userQueueSub = client.subscribe(
                    `/user/queue/notification`,
                    (msg) => {
                        console.log("📥 Survey /user/queue/notification 수신:", msg.body);
                        handleMessage(msg);
                    }
                );

                const userTopicSub = client.subscribe(
                    `/topic/notification/${userEmail}`,
                    (msg) => {
                        console.log("📥 Survey /topic/notification 수신:", msg.body);
                        handleMessage(msg);
                    }
                );

                console.log("📡 Survey 구독 완료 - Queue ID:", userQueueSub.id, "Topic ID:", userTopicSub.id);
            },
            onWebSocketError: (err) => {
                console.error("❌ Survey WebSocket Error:", err);
                console.log("WebSocket 연결 실패 - 재시도 중...");
            },
            onStompError: (frame) => {
                console.error("❌ Survey STOMP ERROR:", frame.headers?.message || frame);
                console.error("STOMP Error details:", frame);
            },
            onDisconnect: (receipt) => {
                console.log("🔌 Survey STOMP DISCONNECTED:", receipt);
                console.log("연결 해제됨 - 자동 재연결 시도");
            },
        });

        try {
            client.activate();
            stompClientRef.current = client;
            console.log("🚀 Survey STOMP 클라이언트 활성화 완료");
        } catch (error) {
            console.error("STOMP 클라이언트 활성화 실패:", error);
        }
    }, [userEmail, getDailyReviewAPI]);

    // 기존 알림 데이터 로드
    const loadExistingReviewNotifications = useCallback(async () => {
        const notifications = await getAllNotificationsAPI();
        if (notifications && notifications.length > 0) {
            console.log('기존 알림 처리 시작:', notifications.length, '건');
            
            const formattedSurveys = await Promise.all(
                notifications.map(async (notification) => {
                    if (notification.dailyReviewId) {
                        const reviewDate = notification.sendTime ? 
                            notification.sendTime.split('T')[0] : 
                            new Date().toISOString().split('T')[0];
                            
                        const reviewData = await getDailyReviewAPI(userEmail, reviewDate);
                        
                        if (reviewData && reviewData.reviews) {
                            return {
                                id: `${reviewData.id}_${notification.notificationId || Date.now()}`,
                                userEmail: userEmail,
                                reviewDate: reviewData.reviewDate || reviewDate,
                                notificationContent: notification.content,
                                isCompleted: notification.check || false,
                                reviews: reviewData.reviews,
                                dailyReviewId: reviewData.id
                            };
                        }
                    }
                    return null;
                })
            );
            
            const validSurveys = formattedSurveys.filter(survey => survey !== null);
            const sortedSurveys = validSurveys.sort((a, b) => new Date(b.reviewDate) - new Date(a.reviewDate));
            
            console.log('유효한 설문 데이터:', sortedSurveys.length, '건');
            setSurveys(sortedSurveys);
        }
    }, [userEmail, getAllNotificationsAPI, getDailyReviewAPI]);

    // 초기화
    useEffect(() => {
        getUserInfo();
    }, [getUserInfo]);

    useEffect(() => {
        if (userEmail && userEmail !== '') {
            console.log('🔄 사용자 이메일 설정됨, 데이터 로드 시작:', userEmail);
            loadExistingReviewNotifications();
            
            const timer = setTimeout(() => {
                connectWebSocket();
            }, 1000);

            return () => {
                clearTimeout(timer);
                if (stompClientRef.current) {
                    console.log('🔌 컴포넌트 언마운트 - WebSocket 연결 해제');
                    try {
                        stompClientRef.current.deactivate();
                    } catch (error) {
                        console.log('WebSocket 해제 중 오류:', error);
                    }
                }
            };
        }
    }, [userEmail, loadExistingReviewNotifications, connectWebSocket]);

    // 모달 열기
    const openSurveyModal = (survey) => {
        console.log('설문 모달 열기:', survey);
        setSelectedSurvey(survey);
        document.body.classList.add('modal-open');
        
        const initialRatings = {};
        const initialComments = {};
        const initialHoveredStars = {};
        
        survey.reviews.forEach(review => {
            const existingRating = review.rating || 0;
            // 정수를 .0 형태로 변환
            const doubleRating = parseFloat(existingRating + '.0');
            initialRatings[review.id] = doubleRating;
            console.log(`모달 열기 - 리뷰 ${review.id} 초기 rating: ${existingRating} → ${doubleRating}`);
            
            initialComments[review.id] = review.comment || '';
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
        document.body.classList.remove('modal-open');
    };

    // 별점 변경
    const handleRatingChange = (reviewId, rating) => {
        // 정수를 .0 형태로 변환
        const doubleRating = parseFloat(rating + '.0');
        console.log(`별점 변경 - reviewId: ${reviewId}, rating: ${rating} → ${doubleRating}`);
        setRatings(prevRatings => ({ ...prevRatings, [reviewId]: doubleRating }));
    };

    const handleStarHover = (reviewId, starIndex) => {
        setHoveredStars(prevHovered => ({ ...prevHovered, [reviewId]: starIndex }));
    };

    const handleStarLeave = (reviewId) => {
        setHoveredStars(prevHovered => ({ ...prevHovered, [reviewId]: 0 }));
    };

    const handleCommentChange = (reviewId, value) => {
        setComments(prevComments => ({ ...prevComments, [reviewId]: value }));
    };

    // 별점 렌더링
    const renderStars = (reviewId) => {
        const currentRating = ratings[reviewId] || 0;
        const hoveredRating = hoveredStars[reviewId] || 0;
        const displayRating = hoveredRating > 0 ? hoveredRating : Math.floor(currentRating);

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
                    {displayRating > 0 ? `${Math.floor(currentRating)}.0` : '평가해주세요'}
                </span>
            </div>
        );
    };

    // 전체 설문 제출
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const incompleteItems = selectedSurvey.reviews.filter(review => 
            !ratings[review.id] || ratings[review.id] === 0
        );
        
        if (incompleteItems.length > 0) {
            alert('모든 메뉴에 대해 별점 평가를 해주세요.');
            return;
        }
        
        try {
            setIsLoading(true);
            console.log('전체 설문 제출 시작');
            
            // 모든 리뷰를 배열로 구성
            const reviewsArray = selectedSurvey.reviews.map(review => {
                const originalRating = ratings[review.id];
                // .0 형태로 변환
                const doubleRating = parseFloat(Math.floor(originalRating) + '.0');
                
                return {
                    foodId: review.foodId,
                    rating: doubleRating,
                    comment: comments[review.id] || ''
                };
            });
            
            // reviews 객체로 감싸서 전송
            const requestBody = {
                reviews: reviewsArray
            };
            
            console.log('최종 제출할 요청 데이터:', requestBody);
            
            const success = await updateDailyReviewAPI(selectedSurvey.dailyReviewId, requestBody);
            
            if (success) {
                setSurveys(prevSurveys => 
                    prevSurveys.map(survey => 
                        survey.id === selectedSurvey.id 
                            ? { ...survey, isCompleted: true } 
                            : survey
                    )
                );
                
                alert('설문이 제출되었습니다!');
                closeSurveyModal();
                console.log('전체 설문 제출 완료');
            } else {
                alert('설문 제출에 실패했습니다. 다시 시도해주세요.');
                console.error('전체 설문 제출 실패');
            }
        } catch (error) {
            console.error('전체 설문 제출 에러:', error);
            alert('설문 제출 중 오류가 발생했습니다.');
        } finally {
            setIsLoading(false);
        }
    };

    // ESC 키 처리
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
                {isLoading && (
                    <div style={{ padding: '10px', textAlign: 'center', color: '#666' }}>
                        설문 목록을 불러오는 중...
                    </div>
                )}

                {surveys.length > 0 ? (
                    surveys.map((survey) => (
                        <div 
                            className={`survey_link ${survey.isCompleted ? 'survey_completed' : ''}`}
                            key={survey.id} 
                            onClick={() => survey.isCompleted ? null : openSurveyModal(survey)}
                        >
                            <div className="survey_content">
                                <div className="survey_title">
                                    {survey.notificationContent}
                                </div>
                                <div className="survey_meta">
                                    <span className="survey_meta_icon">📝</span>
                                    <span className="survey_meta_text">설문조사</span>
                                    <span className="survey_meta_text">{survey.reviewDate}</span>
                                    <span className="survey_meta_text">ID: {survey.dailyReviewId}</span>
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

            {selectedSurvey && (
                <div className="survey_modal_overlay" onClick={closeSurveyModal}>
                    <div className="survey_modal_container" onClick={(e) => e.stopPropagation()}>
                        <div className="survey_modal_header">
                            <div className="survey_modal_header_content">
                                <h2 className="survey_modal_title">식단 만족도 조사</h2>
                                <p className="survey_modal_date">
                                    {selectedSurvey.reviewDate} (ID: {selectedSurvey.dailyReviewId})
                                </p>
                            </div>
                            <button className="survey_modal_close" onClick={closeSurveyModal}>✕</button>
                        </div>
                        
                        <form onSubmit={handleSubmit} className="survey_modal_form">
                            <div className="survey_modal_scroll_card">
                                <div className="survey_modal_scroll_container">
                                    {selectedSurvey.reviews.map((review) => (
                                        <div key={review.id} className="survey_modal_section">
                                            <div className="survey_modal_left">
                                                <h3 className="survey_modal_item_title">
                                                    {review.foodName}
                                                </h3>
                                                <div className="survey_modal_food_image">
                                                    <img 
                                                        src={review.foodImage ? `http://nimn.store${review.foodImage}` : testfood} 
                                                        className="survey_modal_logo_image" 
                                                        alt="food" 
                                                        onError={(e) => {
                                                            e.target.src = testfood;
                                                        }}
                                                    />
                                                </div>
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
                                                        disabled={isLoading}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                            
                            <div className="survey_modal_submit">
                                <button 
                                    type="submit" 
                                    className="survey_modal_submit_button"
                                    disabled={isLoading}
                                >
                                    {isLoading ? '제출 중...' : '📝 설문 제출하기'}
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