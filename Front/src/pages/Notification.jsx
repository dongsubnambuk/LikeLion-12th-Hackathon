import React, { useEffect, useState, useCallback } from "react";
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Header from "../components/Header";
import BottomNav from "../components/BottomNav";
import '../CSS/Notification.css';
import { v4 as uuidv4 } from 'uuid';

function Notification() {
    const email = localStorage.getItem("email");
    const [messages, setMessages] = useState(() => {
        const savedMessages = localStorage.getItem(`messages_${email}`);
        return savedMessages ? JSON.parse(savedMessages) : [];
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

        setMessages(prevMessages => {
            const isDuplicate = prevMessages.some(msg => 
                (msg.notificationContent || msg.content) === (notification.notificationContent || notification.content)
            );

            if (!isDuplicate) {
                const newMessages = [...prevMessages, notification];
                localStorage.setItem(`messages_${email}`, JSON.stringify(newMessages));
                return newMessages;
            }
            return prevMessages;
        });
    }, [email]);

    useEffect(() => {
        if (!email) return;

        const socket = new SockJS('http://nutrihub.kro.kr:14000/ws');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log("Connected to WebSocket");

            const dietSubscription = client.subscribe(`/topic/notification/diet/${email}`, message => {
                handleNotification(message);
            });

            const paymentSubscription = client.subscribe(`/topic/notification/payment/${email}`, message => {
                handleNotification(message);
            });

            return () => {
                dietSubscription.unsubscribe();
                paymentSubscription.unsubscribe();
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
            <div className="notification-container">
                {messages.map((msg) => (
                    <div className="notification-link" key={msg.id}>
                        <span>{msg.notificationContent || msg.content}</span>
                    </div>
                ))}
            </div>
            <BottomNav />
        </>
    );
}

export default Notification;
