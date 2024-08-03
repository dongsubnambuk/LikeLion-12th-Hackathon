import React, { useEffect, useState, useCallback } from "react";
import { useNavigate } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/Survey.css';
import { v4 as uuidv4 } from 'uuid';

function Survey() {
    const navigate = useNavigate();
    const [email, setEmail] = useState(localStorage.getItem("email"));
    const [surveys, setSurveys] = useState(() => {
        const savedSurveys = localStorage.getItem(`surveys_${email}`);
        return savedSurveys ? JSON.parse(savedSurveys) : [];
    });

    useEffect(() => {
        const handleGet = async () => {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://3.37.64.39:8000/users?email=${email}`, { 
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json(); 

            if (response.status === 200) {
                // Handle successful response
            } else {
                console.log("로그인 실패");
                alert("로그인 실패: " + result.message);
            }
        };
        handleGet();
    }, [email]);

    const handleNotification = useCallback((message) => {
        const notification = JSON.parse(message.body);

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
            console.log("Connected to WebSocket");

            const surveySubscription = client.subscribe(`/topic/notification/survey/${email}`, message => {
                handleNotification(message);
            });

            return () => {
                surveySubscription.unsubscribe();
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            };
        }, error => {
            console.error('Connection error: ', error);
        });

        return () => {
            if (client && client.connected) {
                client.disconnect(() => {
                    console.log('Disconnected');
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
                        onClick={() => navigate(`/survey-detail`)}
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
