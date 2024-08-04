import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Card, Spin } from 'antd';
import '../CSS/AllDietPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

const { Meta } = Card;

function AllDietPage() {
    const navigate = useNavigate();

    const handleItemClick = (item) => {
        navigate(`/dietinfo`, { state: { item } });
    };

    const [mealData, setMealData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const handleGet = async () => {
            try {
                const token = localStorage.getItem("token");

                const response = await fetch(`http://3.37.64.39:8000/api/meal/food`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization":  token, // Bearer 토큰 형식으로 변경
                    }
                });

                if (response.status === 403) {
                    console.error("403 Forbidden: Access denied.");
                    alert("접근이 거부되었습니다. 인증 토큰을 확인하세요.");
                    return;
                }

                const result = await response.json();

                if (response.ok) {
                    setMealData(result);
                    console.log(result);
                } else {
                    console.log("실패");
                    alert("실패: " + result.message);
                }
            } catch (error) {
                console.error("Error fetching data:", error);
                alert("데이터를 가져오는 중 오류가 발생했습니다.");
            } finally {
                setLoading(false); // 데이터 로딩이 완료되면 로딩 상태를 false로 설정
            }
        };
        handleGet();
    }, []);

    if (loading) {
        return <div style={{width: "100%", height: "100%", display: 'flex', alignItems: "center", justifyContent: 'center'}}><Spin size="large" /></div>;
    }

    return (
        <>
            <Header />

            <div className="ADPcontainer">
                <div className="ADPlistContainer">
                    {mealData.map((item, index) => (
                        <Card
                            key={index}
                            hoverable
                            className="ADPitemCard"
                            onClick={() => handleItemClick(item)}
                        >
                            <div className="ADPitemImageContainer">
                                <img src={item.image} alt={item.name} className="ADPitemImage" />
                            </div>
                            <Meta title={<div className="ADPitemTitle">{item.name}</div>} description={`가격: ${item.price}`} className="ADPitemMeta" />
                        </Card>
                    ))}
                </div>
            </div>

            <BottomNav />
        </>
    );
}

export default AllDietPage;
