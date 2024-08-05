import React, { useEffect, useState, useCallback } from "react";
import { useNavigate } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Header from "../components/Header";
import BottomNav from '../components/BottomNav';
import '../CSS/Survey.css';
import { v4 as uuidv4 } from 'uuid';

function Survey() {
    const navigate = useNavigate();
    const email = localStorage.getItem("email");
    const [surveys, setSurveys] = useState(() => {
        const savedSurveys = localStorage.getItem(`surveys_${email}`);
        return savedSurveys ? JSON.parse(savedSurveys) : [];
    });

    useEffect(() => {
        const handleGet = async () => {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, { 
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json(); 

            if (response.status === 200) {
                // 성공적인 응답 처리
            } else {
                console.log("로그인 실패");
                alert("로그인 실패: " + result.message);
            }
        };
        handleGet();
    }, [email]);

    const fetchReviews = async (reviewDate) => {
        const token = localStorage.getItem("token");

        try {
            const response = await fetch(`http://3.37.64.39:8000/api/meal/review/${reviewDate}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            if (response.ok) {
                const result = await response.json();
                return result.reviews;
            } else {
                console.error("리뷰 데이터를 가져오는데 실패했습니다.");
                return null;
            }
        } catch (error) {
            console.error("리뷰 데이터를 가져오는 중 오류가 발생했습니다.", error);
            return null;
        }
    };

    const handleNotification = useCallback(async (message) => {
        const notification = JSON.parse(message.body);
        console.log("수신한 DTO:", notification);

        if (!notification.reviews) {
            console.error("reviews 필드가 null입니다. 추가 데이터를 가져오는 요청을 보냅니다.");
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
                const newSurveys = [...prevSurveys, notification];
                localStorage.setItem(`surveys_${email}`, JSON.stringify(newSurveys));
                return newSurveys;
            }
            return prevSurveys;
        });
    }, [email]);

    useEffect(() => {
        if (!email) return;

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
    }, [email, handleNotification]);

    return (
        <>
            <Header />
            <div className="survey-link-container">
                {surveys.map((survey) => (
                    <div 
                        className="survey-link" 
                        key={survey.id} 
                        onClick={() => navigate(`/survey-detail`, { state: { survey } })}
                    >
                        <span>{survey.notificationContent || survey.content}</span>
                    </div>
                ))}
            </div>
            <BottomNav />
        </>
    );
}

export default Survey;
