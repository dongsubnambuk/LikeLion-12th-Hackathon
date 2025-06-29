import React, { useEffect, useState, useCallback, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import '../CSS/Notification.css';

function Notification() {
    const navigate = useNavigate();
    const [messages, setMessages] = useState([]);
    const [unreadCount, setUnreadCount] = useState(0);
    const [isConnected, setIsConnected] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [userEmail, setUserEmail] = useState('test1@example.com'); // 기본값
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
                
                // 토큰 만료 체크
                if (result.message === "토큰소멸") {
                    alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                    navigate('/login');
                    return;
                }
                
                const email = result.email || 'test@example.com';
                setUserEmail(email);
                console.log('사용자 이메일 설정:', email);
            } else {
                console.log("사용자 정보 조회 실패", response.status);
                
                try {
                    const result = await response.json();
                    
                    // 토큰 만료 체크
                    if (result.message === "토큰소멸") {
                        alert("로그인이 만료되었습니다. 다시 로그인 해주세요");
                        navigate('/login');
                        return;
                    }
                } catch (e) {
                    console.log("사용자 정보 파싱 실패");
                }
                
                // 실패 시 기본값 유지 (이미 test@example.com으로 설정됨)
                console.log('기본 이메일 사용:', userEmail);
            }
        } catch (error) {
            console.error("사용자 정보 조회 오류:", error);
            // 오류 시 기본값 유지 (이미 test@example.com으로 설정됨)
            console.log('기본 이메일 사용:', userEmail);
        }
    }, [navigate, userEmail]);

    // 모든 알림 조회 API
    const getAllNotificationsAPI = useCallback(async () => {
        try {
            setIsLoading(true);
            const response = await fetch(`http://nimn.store/api/notification/all?userEmail=${userEmail}`);
            
            if (response.ok) {
                const data = await response.json();
                return data;
            } else {
                return [];
            }
        } catch (error) {
            console.error('알림 조회 실패:', error);
            return [];
        } finally {
            setIsLoading(false);
        }
    }, [userEmail]);

    // 알림 읽음 처리 API
    const markAsReadAPI = useCallback(async (notificationId) => {
        try {
            const response = await fetch(`http://nimn.store/api/notification/${notificationId}`, {
                method: 'PATCH'
            });
            return response.ok;
        } catch (error) {
            return false;
        }
    }, []);

    // 모든 알림 읽음 처리 API
    const markAllAsReadAPI = useCallback(async () => {
        try {
            const response = await fetch(`http://nimn.store/api/notification?userEmail=${userEmail}`, {
                method: 'PATCH'
            });
            return response.ok;
        } catch (error) {
            return false;
        }
    }, [userEmail]);

    const connectWebSocket = useCallback(() => {
        console.log(`🔄 WebSocket 연결 시도: ${userEmail}`);
        
        // 기존 연결이 있으면 먼저 해제
        if (stompClientRef.current) {
            console.log('🔌 기존 연결 해제');
            stompClientRef.current.deactivate();
        }

        // SockJS 연결 시 query param으로 userEmail 전달
        const socket = new SockJS(`http://nimn.store/ws/notification?userEmail=${userEmail}`);

        const client = new Client({
            webSocketFactory: () => socket,
            connectHeaders: {
                login: userEmail,        
                userEmail: userEmail     
            },
            debug: (str) => console.log("🔍 STOMP Debug:", str),
            heartbeatIncoming: 10000,
            heartbeatOutgoing: 10000,
            reconnectDelay: 5000,
            onConnect: (frame) => {
                console.log("✅ STOMP CONNECTED - userEmail:", userEmail);
                setIsConnected(true);

                // 받은 메시지를 state에 추가하는 함수
                const handleMessage = (message) => {
                    console.log("📨 원본 메시지 수신:", message.body);
                    
                    let body = message.body;
                    let parsed;
                    try {
                        parsed = JSON.parse(body);
                        console.log("📝 파싱된 메시지:", parsed);
                    } catch {
                        parsed = { content: body, type: "TEXT" };
                        console.log("📝 파싱 실패, 기본값 사용:", parsed);
                    }
                    
                    // PAYMENT와 DIET 타입만 알림에 표시
                    if (parsed.type !== 'PAYMENT' && parsed.type !== 'DIET') {
                        console.log(`⚠️ 알림에서 제외된 타입: ${parsed.type}`);
                        return;
                    }
                    
                    console.log(`✅ 알림 추가: ${parsed.type} - ${parsed.content}`);
                    
                    const notification = {
                        notificationId: parsed.notificationId || Date.now(),
                        content: parsed.content || parsed.notificationContent,
                        type: parsed.type || "TEXT",
                        sendTime: parsed.sendTime || new Date().toISOString(),
                        check: parsed.check || false
                    };
                    
                    setMessages(prev => {
                        console.log(`📊 이전 메시지 수: ${prev.length}, 새 메시지 추가 후: ${prev.length + 1}`);
                        return [notification, ...prev];
                    });
                    setUnreadCount(prev => {
                        console.log(`📢 읽지 않은 알림 수: ${prev} → ${prev + 1}`);
                        return prev + 1;
                    });
                };

                // 유저 전용 알림만 구독
                const sub = client.subscribe(
                    "/user/queue/notification",
                    (msg) => {
                        console.log("📥 /user/queue/notification 수신:", msg.body);
                        handleMessage(msg);
                    }
                );

                console.log("📡 구독 완료 - ID:", sub.id, "경로: /user/queue/notification");
            },
            onWebSocketError: (err) => {
                console.error("❌ WebSocket Error:", err);
                setIsConnected(false);
            },
            onStompError: (frame) => {
                console.error("❌ STOMP ERROR:", frame.headers?.message || frame);
                setIsConnected(false);
            },
            onDisconnect: (receipt) => {
                console.log("🔌 STOMP DISCONNECTED:", receipt);
                setIsConnected(false);
            },
        });

        client.activate();
        stompClientRef.current = client;
        
        console.log("🚀 STOMP 클라이언트 활성화 완료");
    }, [userEmail]);

    // 단일 알림 읽음 처리
    const markAsRead = useCallback(async (notificationId) => {
        // UI 먼저 업데이트
        setMessages(prev => 
            prev.map(msg => 
                msg.notificationId === notificationId 
                    ? { ...msg, check: true }
                    : msg
            )
        );
        setUnreadCount(prev => Math.max(0, prev - 1));
        
        // API 호출
        const success = await markAsReadAPI(notificationId);
        if (!success) {
            // API 실패 시 UI 롤백
            setMessages(prev => 
                prev.map(msg => 
                    msg.notificationId === notificationId 
                        ? { ...msg, check: false }
                        : msg
                )
            );
            setUnreadCount(prev => prev + 1);
        }
    }, [markAsReadAPI]);

    // 모든 알림 읽음 처리
    const markAllAsRead = useCallback(async () => {
        // UI 먼저 업데이트
        const prevMessages = messages;
        const prevUnreadCount = unreadCount;
        
        setMessages(prev => 
            prev.map(msg => ({ ...msg, check: true }))
        );
        setUnreadCount(0);
        
        // API 호출
        const success = await markAllAsReadAPI();
        if (!success) {
            // API 실패 시 UI 롤백
            setMessages(prevMessages);
            setUnreadCount(prevUnreadCount);
        }
    }, [markAllAsReadAPI, messages, unreadCount]);

    // 초기 사용자 정보 조회 useEffect
    useEffect(() => {
        getUserInfo();
    }, [getUserInfo]);

    // 알림 데이터 로드 useEffect (userEmail이 변경될 때마다)
    useEffect(() => {
        const loadInitialNotifications = async () => {
            const notifications = await getAllNotificationsAPI();
            if (notifications && notifications.length > 0) {
                // PAYMENT와 DIET 타입만 필터링
                const filteredNotifications = notifications.filter(
                    notification => notification.type === 'PAYMENT' || notification.type === 'DIET'
                );
                
                // API 응답 데이터를 컴포넌트 상태에 맞게 변환 및 최신순 정렬
                const formattedNotifications = filteredNotifications
                    .map(notification => ({
                        notificationId: notification.notificationId,
                        content: notification.content,
                        type: notification.type || "TEXT",
                        sendTime: notification.sendTime,
                        check: notification.check || false
                    }))
                    .sort((a, b) => new Date(b.sendTime) - new Date(a.sendTime)); // 최신순 정렬
                
                setMessages(formattedNotifications);
                
                // 읽지 않은 알림 개수 계산
                const unreadCount = formattedNotifications.filter(notification => !notification.check).length;
                setUnreadCount(unreadCount);
            }
        };

        loadInitialNotifications();
    }, [userEmail, getAllNotificationsAPI]);

    // WebSocket 연결 useEffect (userEmail이 변경될 때마다 항상 실행)
    useEffect(() => {
        connectWebSocket();

        return () => {
            if (stompClientRef.current) {
                stompClientRef.current.deactivate();
            }
        };
    }, [userEmail, connectWebSocket]);

    return (
        <>
            <div className="notification_container" data-count={messages.length}>

                {/* 로딩 상태 표시 */}
                {isLoading && (
                    <div style={{ padding: '10px', textAlign: 'center', color: '#666' }}>
                        알림 목록을 불러오는 중...
                    </div>
                )}

                {/* 읽지 않은 알림 개수 및 전체 읽음 처리 버튼 */}
                {unreadCount > 0 && (
                    <div className="notification_header">
                        <span className="notification_unread_count">
                            읽지 않은 알림 {unreadCount}개
                        </span>
                        <button 
                            className="notification_mark_all_button"
                            onClick={markAllAsRead}
                            disabled={isLoading}
                        >
                            모두 읽음
                        </button>
                    </div>
                )}

                {/* 알림 목록 */}
                {messages.length > 0 ? (
                    messages.map((msg) => (
                        <div 
                            className={`notification_item ${msg.check ? 'notification_read' : 'notification_unread'}`}
                            key={msg.notificationId}
                            data-type={msg.type}
                            onClick={() => markAsRead(msg.notificationId)}
                        >
                            <div className="notification_content">
                                <span>{msg.content}</span>
                            </div>
                            
                            <div className="notification_meta">
                                {msg.type && (
                                    <div className="notification_type">
                                        {msg.type === 'DIET' ? '🍎 식단' : 
                                         msg.type === 'PAYMENT' ? '💳 결제' : '📝 기타'}
                                    </div>
                                )}
                                {msg.sendTime && (
                                    <div className="notification_timestamp">
                                        {new Date(msg.sendTime).toLocaleString('ko-KR', {
                                            month: 'short',
                                            day: 'numeric',
                                            hour: '2-digit',
                                            minute: '2-digit'
                                        })}
                                    </div>
                                )}
                            </div>

                            {/* 읽지 않은 알림 인디케이터 */}
                            {!msg.check && <div className="notification_unread_indicator"></div>}
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