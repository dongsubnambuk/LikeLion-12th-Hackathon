import React, { useEffect, useState, useCallback } from "react";
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import '../CSS/Notification.css';
import { v4 as uuidv4 } from 'uuid';

function Notification() {
    const email = localStorage.getItem("email") || "test@example.com"; // 기본값 설정
    
    // API 명세에 맞는 예시 데이터
    const getSampleNotifications = () => [
        {
            notificationId: 1,
            notificationContent: "새로운 다이어트 플랜이 추천되었습니다!",
            notificationType: "diet",
            notificationTime: new Date().toISOString(),
            isRead: false,
            userId: email
        },
        {
            notificationId: 2,
            notificationContent: "결제가 성공적으로 완료되었습니다.",
            notificationType: "payment",
            notificationTime: new Date(Date.now() - 3600000).toISOString(), // 1시간 전
            isRead: false,
            userId: email
        },
        {
            notificationId: 3,
            notificationContent: "오늘의 칼로리 목표를 달성했습니다! 🎉",
            notificationType: "diet",
            notificationTime: new Date(Date.now() - 7200000).toISOString(), // 2시간 전
            isRead: true,
            userId: email
        },
        {
            notificationId: 4,
            notificationContent: "월간 구독료 자동 결제 알림",
            notificationType: "payment",
            notificationTime: new Date(Date.now() - 86400000).toISOString(), // 1일 전
            isRead: true,
            userId: email
        },
        {
            notificationId: 5,
            notificationContent: "주간 운동 목표 50% 달성!",
            notificationType: "diet",
            notificationTime: new Date(Date.now() - 172800000).toISOString(), // 2일 전
            isRead: true,
            userId: email
        }
        
    ];

    const [messages, setMessages] = useState(() => {
        console.log("초기화 중... email:", email);
        
        const savedMessages = localStorage.getItem(`messages_${email}`);
        if (savedMessages) {
            try {
                const parsed = JSON.parse(savedMessages);
                console.log("저장된 메시지 로드:", parsed);
                return parsed;
            } catch (error) {
                console.error("저장된 메시지 파싱 오류:", error);
            }
        }
        
        // 저장된 메시지가 없으면 예시 데이터를 사용
        const sampleData = getSampleNotifications();
        console.log("예시 데이터 생성:", sampleData);
        localStorage.setItem(`messages_${email}`, JSON.stringify(sampleData));
        return sampleData;
    });

    const [unreadCount, setUnreadCount] = useState(0);

    // 읽지 않은 알림 개수 업데이트
    useEffect(() => {
        const count = messages.filter(msg => !msg.isRead).length;
        console.log("읽지 않은 알림 개수:", count, "전체 메시지:", messages.length);
        setUnreadCount(count);
    }, [messages]);

    useEffect(() => {
        // 컴포넌트 마운트 시 디버깅 정보 출력
        console.log("컴포넌트 마운트됨");
        console.log("현재 email:", email);
        console.log("현재 messages:", messages);
        console.log("messages.length:", messages.length);
        
        const handleGet = async () => {
            // 예시 데이터로만 동작 (실제 API 호출 제거)
            console.log("예시 데이터로 동작 중");
        };
        
        handleGet();
    }, [email, messages]);

    const handleNotification = useCallback((message) => {
        const notification = JSON.parse(message.body);

        // API 응답 형식에 맞게 데이터 구조 통일
        if (!notification.notificationId) {
            notification.notificationId = Date.now(); // 임시 ID
        }

        // 타임스탬프 필드명 통일
        if (!notification.notificationTime) {
            notification.notificationTime = new Date().toISOString();
        }

        // 새 알림은 기본적으로 읽지 않음 상태
        if (notification.isRead === undefined) {
            notification.isRead = false;
        }

        // 타입 필드명 통일
        if (!notification.notificationType && notification.type) {
            notification.notificationType = notification.type;
        }

        setMessages(prevMessages => {
            const isDuplicate = prevMessages.some(msg => 
                (msg.notificationContent || msg.content) === (notification.notificationContent || notification.content)
            );

            if (!isDuplicate) {
                const newMessages = [notification, ...prevMessages]; // 새 알림을 맨 앞에 추가
                localStorage.setItem(`messages_${email}`, JSON.stringify(newMessages));
                return newMessages;
            }
            return prevMessages;
        });
    }, [email]);

    // 알림 읽음 처리 함수 (예시 데이터로만 동작)
    const markAsRead = useCallback((notificationId) => {
        setMessages(prevMessages => {
            const updatedMessages = prevMessages.map(msg => 
                msg.notificationId === notificationId ? { ...msg, isRead: true } : msg
            );
            localStorage.setItem(`messages_${email}`, JSON.stringify(updatedMessages));
            return updatedMessages;
        });
    }, [email]);

    // 모든 알림 읽음 처리 함수 (예시 데이터로만 동작)
    const markAllAsRead = useCallback(() => {
        setMessages(prevMessages => {
            const updatedMessages = prevMessages.map(msg => ({ ...msg, isRead: true }));
            localStorage.setItem(`messages_${email}`, JSON.stringify(updatedMessages));
            return updatedMessages;
        });
    }, [email]);

    useEffect(() => {
        if (!email) return;
        
        // WebSocket 연결도 예시 데이터로만 동작하도록 주석 처리
        console.log("WebSocket 연결 시뮬레이션 (예시 데이터로만 동작)");
        
        /*
        const socket = new SockJS(`http://chatex.p-e.kr:14000/ws?userId=${email}`);
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
        */
    }, [email]);

    return (
        <>
            <div className="notification_container" data-count={messages.length}>
                {/* 읽지 않은 알림 개수 및 전체 읽음 처리 버튼 */}
                {unreadCount > 0 && (
                    <div className="notification_header">
                        <span className="notification_unread_count">
                            읽지 않은 알림 {unreadCount}개
                        </span>
                        <button 
                            className="notification_mark_all_button"
                            onClick={markAllAsRead}
                        >
                            모두 읽음
                        </button>
                    </div>
                )}

                {/* 알림 목록 */}
                {messages.length > 0 ? (
                    messages.map((msg) => (
                        <div 
                            className={`notification_item ${msg.isRead ? 'notification_read' : 'notification_unread'}`}
                            key={msg.notificationId}
                            data-type={msg.notificationType}
                            onClick={() => markAsRead(msg.notificationId)}
                        >
                            <div className="notification_content">
                                <span>{msg.notificationContent || msg.content}</span>
                            </div>
                            
                            <div className="notification_meta">
                                {msg.notificationType && (
                                    <div className="notification_type">
                                        {msg.notificationType === 'diet' ? '🍎 식단' : '💳 결제'}
                                    </div>
                                )}
                                {msg.notificationTime && (
                                    <div className="notification_timestamp">
                                        {new Date(msg.notificationTime).toLocaleString('ko-KR', {
                                            month: 'short',
                                            day: 'numeric',
                                            hour: '2-digit',
                                            minute: '2-digit'
                                        })}
                                    </div>
                                )}
                            </div>

                            {/* 읽지 않은 알림 인디케이터 */}
                            {!msg.isRead && <div className="notification_unread_indicator"></div>}
                        </div>
                    ))
                ) : (
                    <div className="notification_empty">
                        📭 알림이 없습니다.
                    </div>
                )}
            </div>
        </>
    );
}

export default Notification;