import React, { useEffect, useState, useCallback } from "react";
import { useNavigate } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import '../CSS/Survey.css'; // CSS 파일 import
import testfood from '../images/mainCardImg1.jpeg'; // 예시 이미지

function Survey() {
    const navigate = useNavigate();
    const email = localStorage.getItem("email") || "test@example.com";
    const [selectedSurvey, setSelectedSurvey] = useState(null);
    const [responses, setResponses] = useState({});
    const [feedback, setFeedback] = useState({});
    
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
        // 응답 상태 초기화
        const initialResponses = {};
        const initialFeedback = {};
        survey.reviews.forEach(review => {
            initialResponses[review.id] = '';
            initialFeedback[review.id] = '';
        });
        setResponses(initialResponses);
        setFeedback(initialFeedback);
    };

    // 모달 닫기
    const closeSurveyModal = () => {
        setSelectedSurvey(null);
        setResponses({});
        setFeedback({});
        // body에서 modal-open 클래스 제거
        document.body.classList.remove('modal-open');
    };

    // 응답 변경
    const handleResponseChange = (reviewId, value) => {
        setResponses((prevResponses) => ({ ...prevResponses, [reviewId]: value }));
    };

    // 피드백 변경
    const handleFeedbackChange = (reviewId, value) => {
        setFeedback((prevFeedback) => ({ ...prevFeedback, [reviewId]: value }));
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
        
        console.log("설문 제출 시뮬레이션");
        console.log("응답:", responses);
        console.log("피드백:", feedback);
        
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
                                                <label className="survey_modal_label">
                                                    <input
                                                        type="radio"
                                                        value="좋아요"
                                                        checked={responses[review.id] === '좋아요'}
                                                        onChange={(e) => handleResponseChange(review.id, e.target.value)}
                                                        className="survey_modal_radio"
                                                    />
                                                    <span className="survey_modal_label_text">😊 좋아요</span>
                                                </label>
                                                <label className="survey_modal_label">
                                                    <input
                                                        type="radio"
                                                        value="별로예요"
                                                        checked={responses[review.id] === '별로예요'}
                                                        onChange={(e) => handleResponseChange(review.id, e.target.value)}
                                                        className="survey_modal_radio"
                                                    />
                                                    <span className="survey_modal_label_text">😐 별로예요</span>
                                                </label>
                                                <label className="survey_modal_label">
                                                    <input
                                                        type="radio"
                                                        value="기타"
                                                        checked={responses[review.id] === '기타'}
                                                        onChange={(e) => handleResponseChange(review.id, e.target.value)}
                                                        className="survey_modal_radio"
                                                    />
                                                    <span className="survey_modal_label_text">💭 기타 (의견을 입력해주세요.)</span>
                                                </label>
                                                {responses[review.id] === '기타' && (
                                                    <input
                                                        type="text"
                                                        value={feedback[review.id]}
                                                        onChange={(e) => handleFeedbackChange(review.id, e.target.value)}
                                                        placeholder="의견을 입력해주세요..."
                                                        className="survey_modal_feedback_input"
                                                    />
                                                )}
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