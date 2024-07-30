import React, { useEffect, useState } from "react";
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/Notification.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { v4 as uuidv4 } from 'uuid';

function Notification() {
    const [email, setEmail] = useState(localStorage.getItem("email"));
    const [messages, setMessages] = useState(() => {
        // 로컬 스토리지에서 알림을 불러옵니다.
        const savedMessages = localStorage.getItem(`messages_${email}`);
        return savedMessages ? JSON.parse(savedMessages) : [];
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

            if (response.status === 200) { // 응답 status가 200 OK 일 경우
                console.log(result);
                console.log("유저 이름 : " + result.name);
            } else {
                console.log("로그인 실패");
                alert("로그인 실패: " + result.message);
            }
        };
        handleGet();
    }, [email]);

    useEffect(() => {
        if (!email) return;

        console.log("Setting up WebSocket connection...");

        const socket = new SockJS('http://localhost:13306/ws');
        const client = Stomp.over(socket);

        const onConnected = frame => {
            console.log('Connected: ' + frame);

            client.subscribe(`/topic/notification/diet/${email}`, message => {
                console.log('Message received: ', message);
                const notification = JSON.parse(message.body);
                console.log('Notification parsed: ', notification);

                // 고유한 ID가 없는 경우 생성합니다.
                if (!notification.id) {
                    notification.id = uuidv4();
                }

                setMessages(prevMessages => {
                    // 중복된 알림을 방지하기 위해 고유한 ID를 사용하여 새로운 알림만 추가합니다.
                    const isDuplicate = prevMessages.some(msg => msg.id === notification.id);
                    if (!isDuplicate) {
                        const newMessages = [...prevMessages, notification];
                        // 로컬 스토리지에 알림을 저장합니다.
                        localStorage.setItem(`messages_${email}`, JSON.stringify(newMessages));
                        toast.info(`Notification: ${notification.notificationContent}`, {
                            position: "top-right",  // 직접 위치 문자열 사용
                            autoClose: 5000,
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            progress: undefined,
                        });
                        return newMessages;
                    }
                    return prevMessages;
                });
            });
        };

        client.connect({}, onConnected, error => {
            console.error('Connection error: ', error);
        });

        return () => {
            console.log("Disconnecting WebSocket...");
            if (client.connected) {
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, [email]);

    return (
        <>
            <Header />
            <div className="notification-container">
                {messages.map((msg, index) => (
                    <div className="notification-link" key={msg.id || index}>
                        <span>{msg.notificationContent || msg.content}</span>
                    </div>
                ))}
            </div>
            <BottomNav />
            <ToastContainer />
        </>
    );
}

export default Notification;
